package se.andersrapp.brf.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import se.andersrapp.brf.entities.Link;
import se.andersrapp.brf.entities.Resident;

/**
 *
 * @author Anders
 */
public class CommitmentDTO implements Serializable{

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

}
