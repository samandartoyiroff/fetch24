package uz.tenzorsoft.fetch24.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.tenzorsoft.fetch24.domain.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
