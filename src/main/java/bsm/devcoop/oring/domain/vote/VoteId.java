package bsm.devcoop.oring.domain.vote;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class VoteId implements Serializable {
    private AgendaId agendaId;
    private char studentId;
}
