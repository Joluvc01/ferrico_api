package rest.ferrico_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.ferrico_api.entity.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
