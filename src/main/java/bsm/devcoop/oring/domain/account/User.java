package bsm.devcoop.oring.domain.account;

import bsm.devcoop.oring.domain.account.types.Role;
import bsm.devcoop.oring.domain.vote.voting.Vote;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "oring_user")
@Data
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  private String userNumber; // Primary key, 조합원 번호

  private String userCode; // 학생증 바코드

  private String userName; // 유저 이름

  private String userEmail; // 유저 이메일

  private String userPassword; // 유저 비밀번호

  private String userPin; // 유저 셀프 계산대 비밀번호

  private int userPoint; // 유저 포인트

  private String userFingerPrint; // 유저 지문 정보

  @Enumerated(EnumType.STRING)
  private Role roles;

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  ) // user 라는 컬럼명으로 Vote 내 연관 관계 맵핑 ( 1: N 중 1 )
  @JsonManagedReference // JSON 객체로 변환 시 1 : N 중 1에게 부여
  private List<Vote> voteList = new ArrayList<>();

  @Builder
  public User(
          String userNumber, String userCode, String userName,
          String userEmail, String userPassword, String userPin,
          int userPoint, String userFingerPrint, Role roles
  ) {
    this.userNumber = userNumber;
    this.userCode = userCode;
    this.userName = userName;
    this.userEmail = userEmail;
    this.userPassword = userPassword;
    this.userPin = userPin;
    this.userPoint = userPoint;
    this.userFingerPrint = userFingerPrint;
    this.roles = roles;
  }

  public void addVote(Vote vote) {
    vote.setUser(this);
    voteList.add(vote);
  }

  public void changePassword(String userPassword) {
    this.userPassword = userPassword;
  }
  public void changePin(String userPin) {
    this.userPin = userPin;
  }
}
