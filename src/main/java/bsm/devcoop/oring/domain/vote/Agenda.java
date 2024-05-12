package bsm.devcoop.oring.domain.vote;

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
  // agendaNo 랑 conference(pk) 한 번에 묶어야 함

  @EmbeddedId
  @Column(name = "agenda_id")
  private VoteId id;

  private String agendaContent; // 안건 내용

  @OneToMany(
    mappedBy = "agenda",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Vote> vote;

  public void addVote(Vote vote) {
    vote.setAgenda(this);
    this.vote.add(vote);
  }

  @Builder
  public Agenda(VoteId id, String agendaContent, List<Vote> vote) {
    this.id = id;
    this.agendaContent = agendaContent;
    this.vote = vote;
  }
}
