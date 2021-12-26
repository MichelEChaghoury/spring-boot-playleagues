package org.ultims.playleagues.repository.v1;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.ultims.playleagues.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
