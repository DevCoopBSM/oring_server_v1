package bsm.devcoop.oring.domain.agenda;

import bsm.devcoop.oring.domain.conference.Conference;
import bsm.devcoop.oring.domain.vote.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agenda {
  // agendaNo & date(conference)(pk) 한 번에 묶어야 함
  @EmbeddedId
  private AgendaId id;

  private String agendaContent; // 안건 내용

  @ManyToOne
  @MapsId("conferenceId")
  @JoinColumn(name = "conference_date")
  @JsonBackReference
  private Conference conference;

  public void setConference(Conference conference) {
    this.conference = conference;
  }

  @OneToMany(
    mappedBy = "agenda",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Vote> votes = new ArrayList<>();

  public void addVote(Vote vote) {
    vote.setAgenda(this);
    votes.add(vote);
  }

  @Builder
  public Agenda(AgendaId id, String agendaContent) {
    this.id = id;
    this.agendaContent = agendaContent;
  }
}
