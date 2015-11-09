package se.andersrapp.brf.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import se.andersrapp.brf.entities.Apartment;
import se.andersrapp.brf.entities.Link;
import se.andersrapp.brf.entities.Resident;

/**
 *
 * @author Anders
 */
public class ResidencyDTO implements Serializable{

    private Integer id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Apartment apartment;
    private Resident resident;
    private Link link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }
}
