package bsm.devcoop.oring.domain.vote.repository;

import bsm.devcoop.oring.domain.vote.Vote;
import bsm.devcoop.oring.domain.vote.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {

}

