package bsm.devcoop.oring.domain.vote;

import bsm.devcoop.oring.domain.agenda.AgendaId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class VoteId implements Serializable {
  private AgendaId agendaId;
  private String studentId;

  @Builder
  public VoteId(AgendaId agendaId, String studentId) {
    this.agendaId = agendaId;
    this.studentId = studentId;
  }
}
