package rest.ferrico_api.service;

import rest.ferrico_api.entity.Role;
import java.util.List;
import java.util.Optional;

public interface RoleService {

    public List<Role> findAll();
    Optional<Role> findByName(String name);
    public Optional<Role> findById(Long id);
    public Role save(Role user);
    public void deleteById(Long id);
}
