package bsm.devcoop.oring.domain.vote;

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
  @Id
  private String stuNumber; // 학번

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "agenda_no")
  private Agenda agenda;

  public void setAgenda(Agenda agenda) {
    this.agenda = agenda;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "date")
  private Conference conference;

  public void setConference(Conference conference) {
    this.conference = conference;
  }

  @Enumerated(EnumType.STRING)
  private VoteCode vote; // 찬성 or 반대

  private String reason; // 반대 이유

  @Builder
  public Vote(String stuNumber, Agenda agenda, Conference conference, VoteCode vote, String reason) {
    this.stuNumber = stuNumber;
    this.agenda = agenda;
    this.conference = conference;
    this.vote = vote;
    this.reason = reason;
  }
}
