package bsm.devcoop.oring.entity.vote.agenda;

import bsm.devcoop.oring.domain.vote.agenda.presentation.dto.ReadAgendaResponseDto;
import bsm.devcoop.oring.entity.vote.conference.Conference;
import bsm.devcoop.oring.entity.vote.voting.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Table(name = "oring_agenda")
@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agenda {
  @EmbeddedId
  private AgendaId id; // 복합 키, 엔티티와 다르게 선언한 후 클래스로 가져와야 한다

  private String agendaContent; // 안건 내용

  @NotNull
  private char isPossible; // default : 0, 가능 시 1

  public void setIsPossible(char isPossible) {
    this.isPossible = isPossible;
  }

  public void setAgendaContent(String agendaContent) {
    this.agendaContent = agendaContent;
  }

  @ManyToOne(fetch = FetchType.LAZY) // one conf -> many agenda
  @MapsId("conferenceId") // AgendaId 내 conferenceId와 연결
  @JoinColumn(name = "conference_date")
  @JsonBackReference // many 에게 붙는 어노테이션, 순환 루프 방지
  private Conference conference;

  public void setConference(Conference conference) {
    this.conference = conference;
  }

  @OneToMany(
    mappedBy = "agenda",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  ) // one agenda -> many vote
  @JsonManagedReference // one 에게 붙는 어노테이션, 순환 루프 방지
  private List<Vote> voteList = new ArrayList<>();

  // Agenda 엔티티를 ReadAgendaResponseDto로 변환하는 메소드
  public ReadAgendaResponseDto toResponseDto() {
      return new ReadAgendaResponseDto(
          this.id.getAgendaNo(),
          this.conference.getDate(),
          this.agendaContent
      );
  }
}
