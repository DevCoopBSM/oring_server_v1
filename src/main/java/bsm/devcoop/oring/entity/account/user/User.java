package bsm.devcoop.oring.entity.account.user;

import bsm.devcoop.oring.entity.account.user.types.Role;
import bsm.devcoop.oring.entity.vote.voting.Vote;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "common_user")
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
  @Id
  private String userNumber; // Primary key, 조합원 번호

  private String userCode; // 학생증 바코드

  private String userCiNumber; // 유저 CI 정보

  private String userName; // 유저 이름

  private String userAddress; // 유저 주소

  private String userPhone; // 유저 전화번호

  private String userEmail; // 유저 이메일

  private String userPassword; // 유저 비밀번호

  private String userPin; // 유저 셀프 계산대 비밀번호

  private int userPoint; // 유저 포인트

  private String userFingerPrint; // 유저 지문 정보

  @Enumerated(EnumType.STRING)
  private Role roles; // 사용자 인증용 Role, == userType

  // 연관 관계 맵핑

  @OneToMany(
    mappedBy = "user",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  ) // user 라는 컬럼명으로 Vote 내 연관 관계 맵핑 ( 1: N 중 1 )
  @JsonManagedReference // JSON 객체로 변환 시 1 : N 중 1에게 부여
  private List<Vote> voteList = new ArrayList<>();

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
