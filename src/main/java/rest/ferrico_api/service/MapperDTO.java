package rest.ferrico_api.service;

import org.springframework.stereotype.Service;
import rest.ferrico_api.dto.UserDTO;
import rest.ferrico_api.entity.User;

@Service
public class MapperDTO {

    public UserDTO convertToUserDTO(User user) {
        String role = user.getRole().getName();
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getLastname(),
                user.getActive(),
                role
        );
    }

}
