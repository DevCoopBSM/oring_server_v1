package bsm.devcoop.oring.domain.user.repository;

import bsm.devcoop.oring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
  User findByStuNumber(String stuNumber);
  User findByStuCode(String stuCode);
}
