package bsm.devcoop.oring.entity.account.student.repository;

import bsm.devcoop.oring.entity.account.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
    Student findByStuEmail(String stuEmail);

    Student findByStuCode(String stuCode);
}
