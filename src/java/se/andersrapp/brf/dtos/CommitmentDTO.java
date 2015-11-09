package se.andersrapp.brf.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import se.andersrapp.brf.entities.Link;
import se.andersrapp.brf.entities.Resident;

/**
 *
 * @author Anders
 */
public class CommitmentDTO implements Serializable {

    private Integer id;
    private String role;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Resident resident;
    private Boolean authorized;
    private Link link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate startDate) {
        this.fromDate = startDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate endDate) {
        this.toDate = endDate;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(Boolean authorized) {
        this.authorized = authorized;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.role);
        hash = 29 * hash + Objects.hashCode(this.fromDate);
        hash = 29 * hash + Objects.hashCode(this.toDate);
        hash = 29 * hash + Objects.hashCode(this.resident);
        hash = 29 * hash + Objects.hashCode(this.authorized);
        hash = 29 * hash + Objects.hashCode(this.link);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommitmentDTO other = (CommitmentDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.role, other.role)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        if (!Objects.equals(this.toDate, other.toDate)) {
            return false;
        }
        if (!Objects.equals(this.resident, other.resident)) {
            return false;
        }
        if (!Objects.equals(this.authorized, other.authorized)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }

}
