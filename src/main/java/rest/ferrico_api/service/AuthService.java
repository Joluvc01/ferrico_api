package rest.ferrico_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import rest.ferrico_api.dto.ReqRes;
import rest.ferrico_api.entity.User;
import rest.ferrico_api.repository.UserRepository;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signIn(ReqRes singInRequest){
        ReqRes response = new ReqRes();
        try {
            authenticationManager.authenticate((new UsernamePasswordAuthenticationToken(singInRequest.getUsername(), singInRequest.getPassword())));
            var user = userRepository.findByUsername(singInRequest.getUsername()).orElseThrow();
            var jwt = jwtUtils.generateToken(user, user);
            response.setStatusCode(200);
            response.setToken(jwt);
            response.setMessage("Exito al ingresar");
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                response.setError("Credenciales Inválidas");
            } else {
                Optional<User> optionalUser = userRepository.findByUsername(singInRequest.getUsername());
                if (optionalUser.isPresent()) {
                    Boolean status = optionalUser.get().getActive();
                    if (!status) {
                        response.setError("Usuario Desactivado");
                    }
                } else {
                    response.setError("Credenciales Inválidas");
                }
            }
            response.setStatusCode(401);
        }
        return response;
    }
}

