package rest.ferrico_api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import rest.ferrico_api.dto.RegisterDTO;
import rest.ferrico_api.dto.UserDTO;
import rest.ferrico_api.entity.Role;
import rest.ferrico_api.entity.User;
import rest.ferrico_api.service.MapperDTO;
import rest.ferrico_api.service.RoleService;
import rest.ferrico_api.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private MapperDTO convertDTO;
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public ResponseEntity<?> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> userDTOs = users.stream()
                .map(convertDTO::convertToUserDTO)
                .toList();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        return userService.findById(id)
                .map(user -> ResponseEntity.ok(convertDTO.convertToUserDTO(user)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody RegisterDTO registerDTO) {
        Optional<User> existingUser = userService.findByUsername(registerDTO.getUsername());

        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El nombre de usuario ya está en uso");
        }

        User newuser = new User();
        newuser.setUsername(registerDTO.getUsername());
        newuser.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        newuser.setName(registerDTO.getName());
        newuser.setLastname(registerDTO.getLastname());
        newuser.setActive(true);

        Optional<Role> role = roleService.findById(registerDTO.getRole());
        if (role.isPresent()) {
            newuser.setRole(role.get());
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("El rol no existe");
        }

        User savedUser = userService.save(newuser);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertDTO.convertToRegisterDTO(savedUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setUsername(userDTO.getUsername());
            existingUser.setPassword(existingUser.getPassword());
            existingUser.setName(userDTO.getName());
            existingUser.setLastname(userDTO.getLastname());

            Optional<Role> role = roleService.findById(userDTO.getRole());
            if (role.isPresent()) {
                existingUser.setRole(role.get());
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("El rol no existe");
            }

            User updatedUser = userService.save(existingUser);
            return ResponseEntity.ok(convertDTO.convertToUserDTO(updatedUser));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @PostMapping("/active/{id}")
    public ResponseEntity<?> toggleStatus(@PathVariable Long id) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            existingUser.setActive(!existingUser.getActive());
            String message;
            if (existingUser.getActive()) {
                message = "Usuario activado";
            } else {
                message = "Usuario desactivado";
            }
            userService.save(existingUser);
            return ResponseEntity.ok().body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<User> optionalUser = userService.findById(id);

        if(optionalUser.isPresent()){
            userService.deleteById(id);
            return ResponseEntity.ok("Usuario eliminado");
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrada");
        }
    }

    @PostMapping("/password/{id}")
    public ResponseEntity<?> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        Optional<User> optionalUser = userService.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (passwordEncoder.matches(newPassword, user.getPassword())) {
                return ResponseEntity.badRequest().body("La nueva contraseña no puede ser igual a la contraseña actual");
            }

            user.setPassword(passwordEncoder.encode(newPassword));
            userService.save(user);

            return ResponseEntity.ok("Contraseña cambiada exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }
    }
}
