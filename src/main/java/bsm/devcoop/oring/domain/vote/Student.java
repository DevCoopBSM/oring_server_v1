package bsm.devcoop.oring.domain.vote;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Student {
    @Id
    private char stuNumber;

    private String stuCode;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Vote> votes = new ArrayList<>();

    public void addVote(Vote vote) {
        vote.setStudent(this);
        votes.add(vote);
    }

    @Builder
    public Student(char stuNumber, String stuCode, List<Vote> votes) {
        this.stuNumber = stuNumber;
        this.stuCode = stuCode;
        this.votes = votes;
    }
}