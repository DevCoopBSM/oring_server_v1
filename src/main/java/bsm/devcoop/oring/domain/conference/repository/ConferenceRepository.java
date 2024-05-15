package bsm.devcoop.oring.domain.conference.repository;

import bsm.devcoop.oring.domain.conference.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {
    Conference findByDate(LocalDate date);
}
