package bsm.devcoop.oring.entity.account.user.repository;

import bsm.devcoop.oring.entity.account.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByUserCode(String userCode);

  User findByUserEmail(String userEmail);

  Boolean existsByUserEmail(String userCode);
}