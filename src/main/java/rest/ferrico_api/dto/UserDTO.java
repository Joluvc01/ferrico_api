package rest.ferrico_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO implements Serializable {

    public String name;
    public String lastname;
    public String username;
    public Boolean active;
    public Long role;
}
