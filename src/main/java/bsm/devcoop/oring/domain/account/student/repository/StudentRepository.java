package bsm.devcoop.oring.domain.account.student.repository;

import bsm.devcoop.oring.domain.account.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByStuEmail(String stuEmail);

    Student findByStuCode(String stuCode);
}
