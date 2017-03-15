package ch.confinale.timetrack.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TimeTrack.
 */
@Entity
@Table(name = "time_track")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TimeTrack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "time_from", nullable = false)
    private ZonedDateTime timeFrom;

    @NotNull
    @Column(name = "time_to", nullable = false)
    private ZonedDateTime timeTo;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Employee employee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public TimeTrack name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ZonedDateTime getTimeFrom() {
        return timeFrom;
    }

    public TimeTrack timeFrom(ZonedDateTime timeFrom) {
        this.timeFrom = timeFrom;
        return this;
    }

    public void setTimeFrom(ZonedDateTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public ZonedDateTime getTimeTo() {
        return timeTo;
    }

    public TimeTrack timeTo(ZonedDateTime timeTo) {
        this.timeTo = timeTo;
        return this;
    }

    public void setTimeTo(ZonedDateTime timeTo) {
        this.timeTo = timeTo;
    }

    public Project getProject() {
        return project;
    }

    public TimeTrack project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Employee getEmployee() {
        return employee;
    }

    public TimeTrack employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeTrack timeTrack = (TimeTrack) o;
        if (timeTrack.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, timeTrack.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TimeTrack{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", timeFrom='" + timeFrom + "'" +
            ", timeTo='" + timeTo + "'" +
            '}';
    }
}
