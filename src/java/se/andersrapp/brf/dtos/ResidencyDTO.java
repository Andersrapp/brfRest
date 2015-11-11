package se.andersrapp.brf.dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import se.andersrapp.brf.entities.Apartment;
import se.andersrapp.brf.entities.Link;
import se.andersrapp.brf.entities.Resident;

/**
 *
 * @author Anders Rapp
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

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.fromDate);
        hash = 97 * hash + Objects.hashCode(this.toDate);
        hash = 97 * hash + Objects.hashCode(this.apartment);
        hash = 97 * hash + Objects.hashCode(this.resident);
        hash = 97 * hash + Objects.hashCode(this.link);
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
        final ResidencyDTO other = (ResidencyDTO) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fromDate, other.fromDate)) {
            return false;
        }
        if (!Objects.equals(this.toDate, other.toDate)) {
            return false;
        }
        if (!Objects.equals(this.apartment, other.apartment)) {
            return false;
        }
        if (!Objects.equals(this.resident, other.resident)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
}
