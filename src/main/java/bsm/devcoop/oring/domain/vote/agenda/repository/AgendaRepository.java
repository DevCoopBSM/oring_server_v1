package bsm.devcoop.oring.domain.vote.agenda.repository;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import bsm.devcoop.oring.domain.vote.agenda.AgendaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, AgendaId> {
}
