package rest.ferrico_api.service;

import org.springframework.stereotype.Service;
import rest.ferrico_api.dto.RegisterDTO;
import rest.ferrico_api.dto.UserDTO;
import rest.ferrico_api.entity.User;

@Service
public class MapperDTO {

    public RegisterDTO convertToRegisterDTO(User user) {
        Long role = user.getRole().getId();
        return new RegisterDTO(
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getPassword(),
                user.getActive(),
                role
        );
    }

    public UserDTO convertToUserDTO(User user) {
        Long role = user.getRole().getId();
        return new UserDTO(
                user.getName(),
                user.getLastname(),
                user.getUsername(),
                user.getActive(),
                role
        );
    }

}
