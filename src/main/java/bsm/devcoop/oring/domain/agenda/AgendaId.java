package bsm.devcoop.oring.domain.agenda;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AgendaId implements Serializable {
  private int agendaId;
  private LocalDate conferenceId;
}
