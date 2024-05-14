package bsm.devcoop.oring.domain.user.presentation;

import bsm.devcoop.oring.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Character> {
  User findByStuNumber(char stuNumber);
}
