package rest.ferrico_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class RegisterDTO implements Serializable {

    public String name;
    public String lastname;
    public String username;
    public String password;
    public Boolean active;
    public Long role;
}
