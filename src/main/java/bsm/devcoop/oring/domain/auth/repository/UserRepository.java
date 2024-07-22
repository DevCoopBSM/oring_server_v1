package bsm.devcoop.oring.domain.auth.repository;

import bsm.devcoop.oring.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUserCode(String userCode);

  User findByEmail(String email);

  User existsByUserCode(String userCode);
}
