package bsm.devcoop.oring.entity.account.student;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "common_student")
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    private String stuCode; // 학생 바코드

    private String stuName; // 학생 이름

    private String stuNumber; // 학생 학번

    private LocalDate stuBirth; // 학생 생일

    private String stuEmail; // @bssm.hs.kr

    // 생성자
    @Builder
    public Student(String stuCode, String stuName, String stuNumber,
                   LocalDate stuBirth, String stuEmail) {
        this.stuCode = stuCode;
        this.stuName = stuName;
        this.stuNumber = stuNumber;
        this.stuBirth = stuBirth;
        this.stuEmail = stuEmail;
    }
}
