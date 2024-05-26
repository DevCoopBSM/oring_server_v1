package bsm.devcoop.oring.domain.vote;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

  private short vote; // 찬성 1 or 반대 0

  private String reason; // 반대 이유

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("agendaId")
  @JoinColumns({
    @JoinColumn(name = "agenda_no"),
    @JoinColumn(name = "conference_date")
  })
  @JsonBackReference
  private Agenda agenda;

  public void setAgenda(Agenda agenda) {
    this.agenda = agenda;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("studentId")
  @JoinColumn(name = "stu_number")
  @JsonBackReference
  private User user;

  public void setUser(User user) {
    this.user = user;
  }

  @Builder
  public Vote(VoteId voteId, short vote, String reason) {
    this.voteId = voteId;
    this.vote = vote;
    this.reason = reason;
  }
}

