package bsm.devcoop.oring.domain.agenda;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaId implements Serializable {
  private int agendaNo;
  private LocalDate conferenceId;

  @Builder
  public AgendaId(int agendaNo, LocalDate conferenceId) {
    this.agendaNo = agendaNo;
    this.conferenceId = conferenceId;
  }
}
