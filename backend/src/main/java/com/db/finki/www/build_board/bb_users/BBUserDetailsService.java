package com.db.finki.www.build_board.bb_users;

import com.db.finki.www.build_board.bb_users.types.repos.UserRepository;
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
        oldUsername=oldUsername.strip();
        newUsername=newUsername.strip();
        email=email.strip();
        password=password.strip();

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

    public BBUser registerUser(String username, String email, String name, String password, String description, String sex) {
        return createUser(username, email, name, password, description, sex);
    }

    public BBUser loadUserById(int id) {
        return userRepository.findById(id);
    }
}
