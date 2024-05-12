package bsm.devcoop.oring.domain.vote;

import jakarta.persistence.*;
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
    // agendaNo 랑 conference(pk) 한 번에 묶어야 함

    @Id
    private int agendaNo; // 안건 번호 PK

    @ManyToOne(optional = false)
    private Conference conference; // 회의 날짜 PK FK

    private String agendaContent; // 안건 내용

    @OneToMany(mappedBy = "agenda",
            cascade = CascadeType.ALL, orphanRemoval = true /** User가 삭제되면 Dream 객체도 삭제*/)
    private List<Vote> vote;

    @Builder
    public Agenda(int agendaNo, Conference conference,
                  String agendaContent, List<Vote> vote) {
        this.agendaNo = agendaNo;
        this.conference = conference;
        this.agendaContent = agendaContent;
        this.vote = vote;
    }
}
