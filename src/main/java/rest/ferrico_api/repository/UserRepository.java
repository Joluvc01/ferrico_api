package rest.ferrico_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.ferrico_api.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
