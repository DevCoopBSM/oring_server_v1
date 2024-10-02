package bsm.devcoop.oring.entity.vote.voting.repository;

import bsm.devcoop.oring.entity.vote.agenda.Agenda;
import bsm.devcoop.oring.entity.vote.voting.Vote;
import bsm.devcoop.oring.entity.vote.voting.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    List<Vote> findByAgenda(Agenda agenda);
}
