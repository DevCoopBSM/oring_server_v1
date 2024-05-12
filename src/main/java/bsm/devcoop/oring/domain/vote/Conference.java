package bsm.devcoop.oring.domain.vote;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conference {
  @Id
  @Column(name = "date")
  private int date; // 회의 날짜 PK

  private String pdfLink; // PDF 링크

  @OneToMany(
    mappedBy = "id",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Agenda> agenda;

  public void addAgenda(Agenda agenda) {
    agenda.getId().setConference(this);
    this.agenda.add(agenda);
  }

  @OneToMany(
    mappedBy = "conference",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  private List<Vote> vote;

  public void addVote(Vote vote) {
    vote.setConference(this);
    this.vote.add(vote);
  }

  @Builder
  public Conference(int date, String pdfLink, List<Agenda> agenda, List<Vote> vote) {
    this.date = date;
    this.pdfLink = pdfLink;
    this.agenda = agenda;
    this.vote = vote;
  }
}
