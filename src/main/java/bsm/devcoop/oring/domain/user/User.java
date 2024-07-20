package bsm.devcoop.oring.domain.user;

import bsm.devcoop.oring.domain.user.types.Role;
import bsm.devcoop.oring.domain.vote.voting.Vote;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  private String userCode; // 사용자 바코드

  private Long userId;
  private String userName; // 사용자 이름
  private String email;
  private String password;
  private int point;
  private String pin;
  private String authCode; // 사용자 인증 코드

  @Enumerated(EnumType.STRING)
  private Role roles;

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
  public User(
          String userCode, Long userId, String userName,
          String email, String password, int point,
          String pin, String authCode, Role roles
  ) {
    this.userCode = userCode;
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.point = point;
    this.pin = pin;
    this.authCode = authCode;
    this.roles = roles;
  }
}
