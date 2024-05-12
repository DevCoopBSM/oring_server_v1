package bsm.devcoop.oring.domain.vote;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class VoteId implements Serializable {
  private int agendaNo;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "date")
  private Conference conference; // 회의 날짜 PK FK

  public void setConference(Conference conference) {
    this.conference = conference;
  }
}
