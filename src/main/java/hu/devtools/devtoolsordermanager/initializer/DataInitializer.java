//package hu.devtools.devtoolsordermanager.initializer;
//
//import hu.devtools.devtoolsordermanager.entity.Role;
//import hu.devtools.devtoolsordermanager.entity.User;
//import hu.devtools.devtoolsordermanager.repository.RoleRepository;
//import hu.devtools.devtoolsordermanager.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.Set;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//
//    private final UserRepository userRepository;
//    private final RoleRepository roleRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.roleRepository = roleRepository;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
//        if (adminRole == null) {
//            adminRole = new Role();
//            adminRole.setName("ROLE_ADMIN");
//            roleRepository.save(adminRole);
//        }
//
//        if (userRepository.findByEmail("zsolt.kurti@devtools.hu").isEmpty()) {
//            User admin = new User();
//            admin.setEmail("zsolt.kurti@devtools.hu");
//            admin.setPassword(passwordEncoder.encode("Kzsolt89"));
//            admin.setRoles(Set.of(adminRole));
//            userRepository.save(admin);
//        }
//    }
//}