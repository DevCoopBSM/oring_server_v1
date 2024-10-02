package bsm.devcoop.oring.entity.vote.voting;

import bsm.devcoop.oring.entity.vote.agenda.AgendaId;
import jakarta.persistence.Embeddable;
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
  private String userId;

  @Builder
  public VoteId(AgendaId agendaId, String userId) {
    this.agendaId = agendaId;
    this.userId = userId;
  }
}
