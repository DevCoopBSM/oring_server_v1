package bsm.devcoop.oring.domain.vote;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.user.User;
import bsm.devcoop.oring.domain.vote.types.VoteCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote {

  // agendaId(agendaId) & stuNumber(student) 묶음
  @EmbeddedId
  private VoteId voteId;

  @Enumerated(EnumType.STRING)
  private VoteCode vote; // 찬성 or 반대

  private String reason; // 반대 이유

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("agendaId")
  @JoinColumns({
    @JoinColumn(name = "agenda_no"),
    @JoinColumn(name = "conference_date")
  })

  private Agenda agenda;

  public void setAgenda(Agenda agenda) {
    this.agenda = agenda;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("studentId")
  @JoinColumn(name = "stu_number")
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  @Builder
  public Vote(VoteId voteId, VoteCode vote, String reason) {
    this.voteId = voteId;
    this.vote = vote;
    this.reason = reason;
  }
}

