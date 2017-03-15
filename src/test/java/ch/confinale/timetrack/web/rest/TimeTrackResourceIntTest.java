package ch.confinale.timetrack.web.rest;

import ch.confinale.timetrack.TimetrackApp;

import ch.confinale.timetrack.domain.TimeTrack;
import ch.confinale.timetrack.repository.TimeTrackRepository;
import ch.confinale.timetrack.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ch.confinale.timetrack.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimeTrackResource REST controller.
 *
 * @see TimeTrackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TimetrackApp.class)
public class TimeTrackResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_TIME_FROM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_FROM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_TIME_TO = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_TIME_TO = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private TimeTrackRepository timeTrackRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimeTrackMockMvc;

    private TimeTrack timeTrack;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimeTrackResource timeTrackResource = new TimeTrackResource(timeTrackRepository);
        this.restTimeTrackMockMvc = MockMvcBuilders.standaloneSetup(timeTrackResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TimeTrack createEntity(EntityManager em) {
        TimeTrack timeTrack = new TimeTrack()
            .name(DEFAULT_NAME)
            .timeFrom(DEFAULT_TIME_FROM)
            .timeTo(DEFAULT_TIME_TO);
        return timeTrack;
    }

    @Before
    public void initTest() {
        timeTrack = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeTrack() throws Exception {
        int databaseSizeBeforeCreate = timeTrackRepository.findAll().size();

        // Create the TimeTrack
        restTimeTrackMockMvc.perform(post("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isCreated());

        // Validate the TimeTrack in the database
        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeCreate + 1);
        TimeTrack testTimeTrack = timeTrackList.get(timeTrackList.size() - 1);
        assertThat(testTimeTrack.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTimeTrack.getTimeFrom()).isEqualTo(DEFAULT_TIME_FROM);
        assertThat(testTimeTrack.getTimeTo()).isEqualTo(DEFAULT_TIME_TO);
    }

    @Test
    @Transactional
    public void createTimeTrackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timeTrackRepository.findAll().size();

        // Create the TimeTrack with an existing ID
        timeTrack.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimeTrackMockMvc.perform(post("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTrackRepository.findAll().size();
        // set the field null
        timeTrack.setName(null);

        // Create the TimeTrack, which fails.

        restTimeTrackMockMvc.perform(post("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isBadRequest());

        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeFromIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTrackRepository.findAll().size();
        // set the field null
        timeTrack.setTimeFrom(null);

        // Create the TimeTrack, which fails.

        restTimeTrackMockMvc.perform(post("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isBadRequest());

        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeToIsRequired() throws Exception {
        int databaseSizeBeforeTest = timeTrackRepository.findAll().size();
        // set the field null
        timeTrack.setTimeTo(null);

        // Create the TimeTrack, which fails.

        restTimeTrackMockMvc.perform(post("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isBadRequest());

        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTimeTracks() throws Exception {
        // Initialize the database
        timeTrackRepository.saveAndFlush(timeTrack);

        // Get all the timeTrackList
        restTimeTrackMockMvc.perform(get("/api/time-tracks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeTrack.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].timeFrom").value(hasItem(sameInstant(DEFAULT_TIME_FROM))))
            .andExpect(jsonPath("$.[*].timeTo").value(hasItem(sameInstant(DEFAULT_TIME_TO))));
    }

    @Test
    @Transactional
    public void getTimeTrack() throws Exception {
        // Initialize the database
        timeTrackRepository.saveAndFlush(timeTrack);

        // Get the timeTrack
        restTimeTrackMockMvc.perform(get("/api/time-tracks/{id}", timeTrack.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeTrack.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.timeFrom").value(sameInstant(DEFAULT_TIME_FROM)))
            .andExpect(jsonPath("$.timeTo").value(sameInstant(DEFAULT_TIME_TO)));
    }

    @Test
    @Transactional
    public void getNonExistingTimeTrack() throws Exception {
        // Get the timeTrack
        restTimeTrackMockMvc.perform(get("/api/time-tracks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeTrack() throws Exception {
        // Initialize the database
        timeTrackRepository.saveAndFlush(timeTrack);
        int databaseSizeBeforeUpdate = timeTrackRepository.findAll().size();

        // Update the timeTrack
        TimeTrack updatedTimeTrack = timeTrackRepository.findOne(timeTrack.getId());
        updatedTimeTrack
            .name(UPDATED_NAME)
            .timeFrom(UPDATED_TIME_FROM)
            .timeTo(UPDATED_TIME_TO);

        restTimeTrackMockMvc.perform(put("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTimeTrack)))
            .andExpect(status().isOk());

        // Validate the TimeTrack in the database
        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeUpdate);
        TimeTrack testTimeTrack = timeTrackList.get(timeTrackList.size() - 1);
        assertThat(testTimeTrack.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTimeTrack.getTimeFrom()).isEqualTo(UPDATED_TIME_FROM);
        assertThat(testTimeTrack.getTimeTo()).isEqualTo(UPDATED_TIME_TO);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeTrack() throws Exception {
        int databaseSizeBeforeUpdate = timeTrackRepository.findAll().size();

        // Create the TimeTrack

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimeTrackMockMvc.perform(put("/api/time-tracks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timeTrack)))
            .andExpect(status().isCreated());

        // Validate the TimeTrack in the database
        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeTrack() throws Exception {
        // Initialize the database
        timeTrackRepository.saveAndFlush(timeTrack);
        int databaseSizeBeforeDelete = timeTrackRepository.findAll().size();

        // Get the timeTrack
        restTimeTrackMockMvc.perform(delete("/api/time-tracks/{id}", timeTrack.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TimeTrack> timeTrackList = timeTrackRepository.findAll();
        assertThat(timeTrackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimeTrack.class);
    }
}
