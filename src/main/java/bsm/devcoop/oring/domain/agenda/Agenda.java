package bsm.devcoop.oring.domain.agenda;

import bsm.devcoop.oring.domain.agenda.presentation.dto.ReadAgendaResponseDto;
import bsm.devcoop.oring.domain.conference.Conference;
import bsm.devcoop.oring.domain.vote.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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

  @Builder
  public Agenda(AgendaId id, String agendaContent, char isPossible, Conference conference) {
    this.id = id;
    this.agendaContent = agendaContent;
    this.isPossible = isPossible;
    this.conference = conference; // 맵핑 중 one 으로 연결되는 필드 또한 빌드
  }


  // Agenda 엔티티를 ReadAgendaResponseDto로 변환하는 메소드
  public ReadAgendaResponseDto toResponseDto() {
      return new ReadAgendaResponseDto(
          this.id.getAgendaNo(),
          this.conference.getDate(),
          this.agendaContent
      );
  }
}
