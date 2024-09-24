package bsm.devcoop.oring.domain.account.repository;

import bsm.devcoop.oring.domain.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUserCode(String userCode);

  User findByUserEmail(String userEmail);

  Boolean existsByUserEmail(String userCode);
}