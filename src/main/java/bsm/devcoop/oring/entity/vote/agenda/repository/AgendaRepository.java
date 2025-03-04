package bsm.devcoop.oring.entity.vote.agenda.repository;

import bsm.devcoop.oring.entity.vote.agenda.Agenda;
import bsm.devcoop.oring.entity.vote.agenda.AgendaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, AgendaId> {
<<<<<<< HEAD:src/main/java/bsm/devcoop/oring/entity/vote/agenda/repository/AgendaRepository.java
}
=======
    Agenda findByConferenceDate(LocalDate conferenceDate);

    Agenda findByConferenceDateAndAgendaNo(LocalDate conferenceDate, int AgendaNo);

    List<Agenda> findAllByConferenceDate(LocalDate conferenceDate);

    Agenda updateAgendaContentByConferenceDateAndAgendaNo(LocalDate conferenceDate, int AgendaNo, String agendaContent);

    Boolean deleteByConferenceDateAndAgendaNo(LocalDate conferenceDate, int agendaNo);
}
>>>>>>> 8157d18c2d3aaf724b99b3c2dc8a6dbe8cfc85a1:src/main/java/bsm/devcoop/oring/domain/agenda/repository/AgendaRepository.java
