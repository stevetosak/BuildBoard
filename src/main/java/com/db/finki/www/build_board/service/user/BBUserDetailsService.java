package com.db.finki.www.build_board.service.user;

import com.db.finki.www.build_board.entity.user_type.BBUser;
import com.db.finki.www.build_board.repository.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BBUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public BBUserDetailsService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                             .orElseThrow(
                                     () -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public BBUser changeInfoForUserWithUsername(String oldUsername, String newUsername, String email, String name, String description, String password) {
        BBUser user = (BBUser) loadUserByUsername(oldUsername);

        user.setUsername(newUsername);
        user.setEmail(email);
        user.setName(name);
        user.setDescription(description);

        if (!password.isBlank() && !password.isEmpty()) {
            user.setPassword(
                    passwordEncoder.encode(password)
            );
        }

        return userRepository.save(user);
    }

    public BBUser createUser(String username, String email, String name, String password, String description, String sex) {
        password = passwordEncoder.encode(password);
        sex = sex.equals("male") ? "m" : "f";
        return userRepository.save(
                new BBUser(
                        username,
                        email,
                        name,
                        password,
                        description,
                        sex
                )
        );
    }

    public Authentication registerUser(String username, String email, String name, String password, String description, String sex) {
        BBUser user = createUser(username, email, name, password, description, sex);
        return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
    }

    public BBUser loadUserById(int id) {
        return userRepository.findById(id);
    }
}
