package bsm.devcoop.oring.domain.user;

import bsm.devcoop.oring.domain.vote.Vote;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
  private String stuNumber;

  private String stuCode;

  private String stuName;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @JsonManagedReference
  private List<Vote> voteList = new ArrayList<>();

  public void addVote(Vote vote) {
    vote.setUser(this);
    voteList.add(vote);
  }

  @Builder
  public User(String stuNumber, String stuCode, String stuName) {
    this.stuNumber = stuNumber;
    this.stuCode = stuCode;
    this.stuName = stuName;
  }
}
