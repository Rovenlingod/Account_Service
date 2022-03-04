package account.repositories;

import account.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataLoader {

    private RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
        createRoles();
    }

    private void createRoles() {
        try {
            roleRepository.save(new Role("ROLE_ADMINISTRATOR", true));
            roleRepository.save(new Role("ROLE_USER", false));
            roleRepository.save(new Role("ROLE_ACCOUNTANT", false));
            roleRepository.save(new Role("ROLE_AUDITOR", false));
        } catch (Exception e) {

        }
    }
}
