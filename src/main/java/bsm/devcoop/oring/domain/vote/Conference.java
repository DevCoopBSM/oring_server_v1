package bsm.devcoop.oring.domain.vote;

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

  private String pdfLink; // PDF 링크

  @OneToMany(
          mappedBy = "conference",
          cascade = CascadeType.ALL,
          orphanRemoval = true
  )
  private List<Agenda> agendas = new ArrayList<>(); // 연관관계 맵핑 - 안건

  // add()
  public void addAgenda(Agenda agenda) {
    agenda.setConference(this);
    agendas.add(agenda);
  }

  @Builder
  public Conference(LocalDate date, String pdfLink) {
    this.date = date;
    this.pdfLink = pdfLink;
  }
}
