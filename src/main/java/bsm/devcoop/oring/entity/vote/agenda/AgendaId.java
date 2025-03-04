package bsm.devcoop.oring.entity.vote.agenda;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
@Builder
@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaId implements Serializable {
  private int agendaNo;
<<<<<<< HEAD:src/main/java/bsm/devcoop/oring/entity/vote/agenda/AgendaId.java
=======

>>>>>>> 8157d18c2d3aaf724b99b3c2dc8a6dbe8cfc85a1:src/main/java/bsm/devcoop/oring/domain/agenda/AgendaId.java
  private LocalDate conferenceId;

  @Builder
  public AgendaId(int agendaNo, LocalDate conferenceId) {
    this.agendaNo = agendaNo;
    this.conferenceId = conferenceId;
  }
}
