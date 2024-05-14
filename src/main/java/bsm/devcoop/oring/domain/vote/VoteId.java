package bsm.devcoop.oring.domain.vote;

import bsm.devcoop.oring.domain.agenda.AgendaId;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class VoteId implements Serializable {
  private AgendaId agendaId;
  private char studentId;
}
