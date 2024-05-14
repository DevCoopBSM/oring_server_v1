package bsm.devcoop.oring.domain.user;

import bsm.devcoop.oring.domain.vote.Vote;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  private char stuNumber;

  private String stuCode;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Vote> votes = new ArrayList<>();

  public void addVote(Vote vote) {
    vote.setUser(this);
    votes.add(vote);
  }

  @Builder
  public User(char stuNumber, String stuCode, List<Vote> votes) {
    this.stuNumber = stuNumber;
    this.stuCode = stuCode;
    this.votes = votes;
  }
}
