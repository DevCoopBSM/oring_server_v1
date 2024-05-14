package bsm.devcoop.oring.domain.vote.repository;

import bsm.devcoop.oring.domain.vote.Student;
import bsm.devcoop.oring.domain.vote.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Character> {
    Student findByStuNumber(char stuNumber);
}
