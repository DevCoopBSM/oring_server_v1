package bsm.devcoop.oring.domain.vote.conference;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Conference {
  @Id
  private LocalDate date; // 회의 날짜 PK

  private String fileLink; // 회의 첨부 링크

  @OneToMany(
    mappedBy = "conference",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @JsonManagedReference
  private List<Agenda> agendaList = new ArrayList<>(); // 연관관계 맵핑 - 안건 리스트

  // add()
  public void addAgenda(Agenda agenda) {
    agenda.setConference(this);
    agendaList.add(agenda);
  }

  @Builder
  public Conference(LocalDate date, String fileLink) {
    this.date = date;
    this.fileLink = fileLink;
  }
}
