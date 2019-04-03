package com.epam.theater.auth;

import com.epam.theater.entity.User;
import com.epam.theater.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by kneimad on 28.09.2016.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userDao;

//    @Override
//    @Transactional(readOnly = true)
//    public UserDetails loadUserByUsername(String username) {
//        try {
//            User user = userDao.findByUsername(username);
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//
//        for (Role role : user.getRoles()) {
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }
//        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
////                return new CurrentUser(user);
//        }catch (Exception e){
//            System.err.println("ERROR: User with username " + username + " was not found in DB by cause: " + e);
//            throw new UsernameNotFoundException("User with username " + username + " was not found in DB.");
//        }
//    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String mobile) {
        try {
            List<User> user = userDao.findByUsernameIgnoreCaseOrMobile(mobile, mobile);
            System.out.println("\nLOGIN / MOBILE : " + mobile);
            User chosenUser = null;
            if(user.size() > 1){
                System.err.println("================================================================");
                System.err.println("= ATTENTION ! MULTIPLE MOBILE-USER. CHOOSE YOUR ROLE, PLEASE   =");
                System.err.println("================================================================\n");
                for (User u: user) {
                    if(u.getMobile().equals(mobile)){
                        chosenUser = u;
                    }
                }
            } else {
                System.out.println("THE ONLY ONE USER");
                chosenUser = user.get(0);
            }
            System.out.println("\nchosenUser: " + chosenUser + "\n");
            Set<GrantedAuthority> grantedAuthorities = chosenUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

            //                System.out.println(role);
//            User(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities)
            return new org.springframework.security.core.userdetails.User(chosenUser.getUsername(), chosenUser.getPassword(), true, true, true, true, grantedAuthorities);
//                return new CurrentUser(user);
        }catch (Exception e){
            System.err.println("ERROR: User with login / mobile " + mobile + " was not found in DB by cause: " + e);
            throw new UsernameNotFoundException("User with login / mobile " + mobile + " was not found in DB.");
        }
    }
}