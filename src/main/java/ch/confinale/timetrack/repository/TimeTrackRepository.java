package ch.confinale.timetrack.repository;

import ch.confinale.timetrack.domain.TimeTrack;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeTrack entity.
 */
@SuppressWarnings("unused")
public interface TimeTrackRepository extends JpaRepository<TimeTrack,Long> {

}
