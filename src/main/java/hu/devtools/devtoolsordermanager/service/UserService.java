package hu.devtools.devtoolsordermanager.service;

import hu.devtools.devtoolsordermanager.entity.Role;
import hu.devtools.devtoolsordermanager.entity.User;
import hu.devtools.devtoolsordermanager.repository.RoleRepository;
import hu.devtools.devtoolsordermanager.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByEmail("zsolt.kurti@devtools.hu").isEmpty()) {
            // Létrehozzuk a felhasználót, de szerepkör nélkül
            User admin = new User();
            admin.setEmail("zsolt.kurti@devtools.hu");
            admin.setPassword(passwordEncoder.encode("Kzsolt89"));
            userRepository.save(admin);  // Először elmentjük a felhasználót

            // Most keresünk vagy létrehozunk egy szerepkört
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            Role userRole = roleRepository.findByName("ROLE_USER");
            if (userRole == null) {
                userRole = new Role();
                userRole.setName("ROLE_USER");
                roleRepository.save(userRole);
            }

            admin.setRoles(Set.of(userRole));
            userRepository.save(admin);
        }
    }


}