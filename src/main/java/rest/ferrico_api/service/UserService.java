package rest.ferrico_api.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import rest.ferrico_api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    public List<User> findAll();
    Optional<User> findByUsername(String username);
    public Optional<User> findById(Long id);
    public User save(User user);
    public void deleteById(Long id);
}
