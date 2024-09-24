package rest.ferrico_api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rest.ferrico_api.entity.Role;
import rest.ferrico_api.entity.User;
import rest.ferrico_api.service.RoleService;
import rest.ferrico_api.service.UserService;

import java.util.Optional;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public AdminUserInitializer(UserService userService, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void run(String... args) {
        Optional<Role> adminRoleOpt = roleService.findByName("Administrador");

        if (adminRoleOpt.isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("Administrador");
            adminRole.setActive(true);
            roleService.save(adminRole);
            adminRoleOpt = Optional.of(adminRole);
        }

        if (userService.findByUsername("admin").isEmpty()) {
            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            adminUser.setName("Admin");
            adminUser.setLastname("Admin");
            adminUser.setActive(true);

            Role adminRole = adminRoleOpt.get();
            adminUser.setRole(adminRole);

            userService.save(adminUser);
            System.out.println("Admin user created successfully.");
        }
    }
}