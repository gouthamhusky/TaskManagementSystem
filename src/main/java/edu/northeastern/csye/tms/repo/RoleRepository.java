package edu.northeastern.csye.tms.repo;

import edu.northeastern.csye.tms.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Integer> {
}
