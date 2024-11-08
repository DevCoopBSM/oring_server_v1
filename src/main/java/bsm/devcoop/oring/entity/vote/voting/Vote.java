package bsm.devcoop.oring.entity.vote.voting;

import bsm.devcoop.oring.entity.vote.agenda.Agenda;
import bsm.devcoop.oring.entity.account.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "oring_vote")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {
  @EmbeddedId
  private VoteId voteId; // 복합 키, 엔티티와 다르게 선언한 후 클래스로 가져와야 한다

  private char vote; // 찬성 Y or 반대 N / 안건 번호 1, 2, 3 ... , n

  private String reason; // 반대 이유 ( vote == 'N' 이라면 반드시 존재해야 한다 )

  @ManyToOne(fetch = FetchType.LAZY) // one agenda -> many vote
  @MapsId("agendaId") // VoteId 내 agendaId와 연결
  @JoinColumns({
    @JoinColumn(name = "agenda_no"),
    @JoinColumn(name = "conference_date")
  })
  @JsonBackReference // many 에게 붙는 어노테이션, 순환 루프 방지
  private Agenda agenda;

  public void setAgenda(Agenda agenda) {
    this.agenda = agenda;
  }

  @ManyToOne(fetch = FetchType.LAZY) // one user -> many vote
  @MapsId("userId") // VoteId 내 studentId와 연결
  @JoinColumn(name = "user_code")
  @JsonBackReference // many 에게 붙는 어노테이션, 순환 루프 방지
  private User user;

  public void setUser(User user) {
    this.user = user;
  }
}

