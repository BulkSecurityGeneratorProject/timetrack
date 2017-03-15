package ch.confinale.timetrack.web.rest;

import com.codahale.metrics.annotation.Timed;
import ch.confinale.timetrack.domain.TimeTrack;

import ch.confinale.timetrack.repository.TimeTrackRepository;
import ch.confinale.timetrack.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TimeTrack.
 */
@RestController
@RequestMapping("/api")
public class TimeTrackResource {

    private final Logger log = LoggerFactory.getLogger(TimeTrackResource.class);

    private static final String ENTITY_NAME = "timeTrack";
        
    private final TimeTrackRepository timeTrackRepository;

    public TimeTrackResource(TimeTrackRepository timeTrackRepository) {
        this.timeTrackRepository = timeTrackRepository;
    }

    /**
     * POST  /time-tracks : Create a new timeTrack.
     *
     * @param timeTrack the timeTrack to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timeTrack, or with status 400 (Bad Request) if the timeTrack has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/time-tracks")
    @Timed
    public ResponseEntity<TimeTrack> createTimeTrack(@Valid @RequestBody TimeTrack timeTrack) throws URISyntaxException {
        log.debug("REST request to save TimeTrack : {}", timeTrack);
        if (timeTrack.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new timeTrack cannot already have an ID")).body(null);
        }
        TimeTrack result = timeTrackRepository.save(timeTrack);
        return ResponseEntity.created(new URI("/api/time-tracks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /time-tracks : Updates an existing timeTrack.
     *
     * @param timeTrack the timeTrack to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timeTrack,
     * or with status 400 (Bad Request) if the timeTrack is not valid,
     * or with status 500 (Internal Server Error) if the timeTrack couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/time-tracks")
    @Timed
    public ResponseEntity<TimeTrack> updateTimeTrack(@Valid @RequestBody TimeTrack timeTrack) throws URISyntaxException {
        log.debug("REST request to update TimeTrack : {}", timeTrack);
        if (timeTrack.getId() == null) {
            return createTimeTrack(timeTrack);
        }
        TimeTrack result = timeTrackRepository.save(timeTrack);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timeTrack.getId().toString()))
            .body(result);
    }

    /**
     * GET  /time-tracks : get all the timeTracks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of timeTracks in body
     */
    @GetMapping("/time-tracks")
    @Timed
    public List<TimeTrack> getAllTimeTracks() {
        log.debug("REST request to get all TimeTracks");
        List<TimeTrack> timeTracks = timeTrackRepository.findAll();
        return timeTracks;
    }

    /**
     * GET  /time-tracks/:id : get the "id" timeTrack.
     *
     * @param id the id of the timeTrack to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timeTrack, or with status 404 (Not Found)
     */
    @GetMapping("/time-tracks/{id}")
    @Timed
    public ResponseEntity<TimeTrack> getTimeTrack(@PathVariable Long id) {
        log.debug("REST request to get TimeTrack : {}", id);
        TimeTrack timeTrack = timeTrackRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timeTrack));
    }

    /**
     * DELETE  /time-tracks/:id : delete the "id" timeTrack.
     *
     * @param id the id of the timeTrack to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/time-tracks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeTrack(@PathVariable Long id) {
        log.debug("REST request to delete TimeTrack : {}", id);
        timeTrackRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
