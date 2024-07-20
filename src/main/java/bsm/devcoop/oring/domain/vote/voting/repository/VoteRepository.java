package bsm.devcoop.oring.domain.vote.voting.repository;

import bsm.devcoop.oring.domain.vote.agenda.Agenda;
import bsm.devcoop.oring.domain.vote.voting.Vote;
import bsm.devcoop.oring.domain.vote.voting.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    List<Vote> findByAgenda(Agenda agenda);
}
