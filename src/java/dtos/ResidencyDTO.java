package dtos;

import entities.Apartment;
import entities.Resident;
import java.time.LocalDate;

/**
 *
 * @author Anders
 */
public class ResidencyDTO {

    private Integer id;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Apartment apartment;
    private Resident resident;

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

}
