package bsm.devcoop.oring.domain.agenda.repository;

import bsm.devcoop.oring.domain.agenda.Agenda;
import bsm.devcoop.oring.domain.agenda.AgendaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, AgendaId> {
    Agenda findByConferenceDate(LocalDate conferenceDate);

    Agenda findByConferenceDateAndAgendaNo(LocalDate conferenceDate, int AgendaNo);

    List<Agenda> findAllByConferenceDate(LocalDate conferenceDate);

    Agenda updateAgendaContentByConferenceDateAndAgendaNo(LocalDate conferenceDate, int AgendaNo, String agendaContent);

    Boolean deleteByConferenceDateAndAgendaNo(LocalDate conferenceDate, int agendaNo);
}