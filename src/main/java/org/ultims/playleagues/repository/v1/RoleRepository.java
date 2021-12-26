package org.ultims.playleagues.repository.v1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ultims.playleagues.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
