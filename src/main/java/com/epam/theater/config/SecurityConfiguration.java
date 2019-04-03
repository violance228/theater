package com.epam.theater.config;

import com.epam.theater.entity.User;
import com.epam.theater.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * created by user violence
 * created on 03.04.2019
 * class created for project theater
 */

@EnableWebSecurity
public class SecurityConfiguration {
    private static final Logger log = LogManager.getLogger(SecurityConfiguration.class.getName());
    //    @Value("${rememberMe.key}")
    private String rememberMeKey = "KEY";

    @Autowired
    DataSource dataSource;

    @Autowired(required = false)
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        SecurityConfiguration sc;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web
                    .ignoring()
                    .antMatchers("/fonts/**", "/images/**", "/icons/**", "/js/**", "/css/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();// вимкнути csrf захист

            http.authorizeRequests()
                    .antMatchers("/**").permitAll()
                    .anyRequest().authenticated() // всі інші запити потребують аутентифікації
                    .and()
                    .formLogin()
                    .loginPage("/login")  // URL логіну
                    //                .usernameParameter("username") // назва параметру з логіном на формі
                    .usernameParameter("mobile")
                    .successHandler(sc.successHandler)
                    .failureHandler(sc.failureHandler)
                    //                .failureUrl("/access") //access
                    //                .failureForwardUrl("/login?error")
                    //.failureUrl("/login?error") //login?error
                    //                .defaultSuccessUrl("/home")
                    .permitAll() // дозволити всім заходити на форму логіну
                    .and()
                    .logout()
                    //                .logoutSuccessHandler(logoutHandler)
                    .permitAll()
                    .and();
            //.rememberMe().key(rememberMeKey).rememberMeServices(rememberMeServices())
            //.rememberMe().tokenRepository(persistentTokenRepository())

            //.tokenValiditySeconds(1209600);

            http.exceptionHandling()
                    .accessDeniedPage("/access?error");
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(sc.userDetailsService)
                    .passwordEncoder(new BCryptPasswordEncoder());
        }

    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler
    savedRequestAwareAuthenticationSuccessHandler() {

        SavedRequestAwareAuthenticationSuccessHandler auth
                = new SavedRequestAwareAuthenticationSuccessHandler();
        auth.setTargetUrlParameter("targetUrl");
        return auth;
    }

    /**
     * Returns user identifier in JSON format on authentication success.
     */
    private final AuthenticationSuccessHandler successHandler = new AuthenticationSuccessHandler() {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response,
                                            Authentication auth) throws IOException, ServletException {
            log.info("***************** AuthenticationSuccessHandler *****************");
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            User u = userService.findUserByName(userDetail.getUsername());
            if(u!=null){
                request.getSession().setAttribute("userId", u.getId());
            }
//            if (userService.findUserTypeIdByUserId(u.getId()).equals(Constants.USER_TYPE_API)) {
            Gson g = new Gson();
            Map<String, String> result = new HashMap<>();
            result.put("authorities", userDetail.getAuthorities().toString());
            result.put("status", "ACCESS_GUARANTEED");
            result.put("JSESSIONID", request.getSession().getId());
            response.setHeader("JSESSIONID", request.getSession().getId());
            response.getWriter().write(g.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
    };

    /**
     * Returns authentication error in JSON format.
     */
    private final AuthenticationFailureHandler failureHandler = new AuthenticationFailureHandler() {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request,
                                            HttpServletResponse response,
                                            AuthenticationException exception) throws IOException, ServletException {
            log.info("***************** onAuthenticationFailure *****************");
            HttpSession session = request.getSession(false);
            ThreadContext.put("USER", request.getRemoteUser());
            log.info("ACCESS DENIED");

            Gson g = new Gson();
            Map<String, String> result = new HashMap<>();
            result.put("status", "ACCESS_DENIED");
            result.put("message", "Login failure");
            response.setHeader("JSESSIONID", request.getSession().getId());
            response.getWriter().write(g.toJson(result));
            response.getWriter().flush();
            response.getWriter().close();
            response.setStatus(HttpServletResponse.SC_NON_AUTHORITATIVE_INFORMATION);
//            }
        }
    };

    /**
     * Changes default redirect logout handler to empty one, 'cause no redirect is necessary.
     */
    private final LogoutSuccessHandler logoutHandler = new SimpleUrlLogoutSuccessHandler() {
        @Override
        public void onLogoutSuccess(HttpServletRequest request,
                                    HttpServletResponse response,
                                    Authentication authentication) throws IOException, ServletException {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    };
}
