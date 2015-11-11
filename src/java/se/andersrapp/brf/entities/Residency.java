package se.andersrapp.brf.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anders Rapp
 */
@Entity
@Table(name = "residency")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Residency.findAll", query = "SELECT r FROM Residency r"),
    @NamedQuery(name = "Residency.findById", query = "SELECT r FROM Residency r WHERE r.id = :id"),
    @NamedQuery(name = "Residency.findByFromDate", query = "SELECT r FROM Residency r WHERE r.fromDate = :fromDate"),
    @NamedQuery(name = "Residency.findByToDate", query = "SELECT r FROM Residency r WHERE r.toDate = :toDate"),
    @NamedQuery(name = "Residency.findResidenciesByApartment",
            query = "SELECT r FROM Residency r WHERE r.apartment.id = :apartmentId"),
    @NamedQuery(name = "Residency.findOneApartmentResidency",
            query = "SELECT r FROM Residency r WHERE r.id = :residencyId AND r.apartment.id = :apartmentId"),
    @NamedQuery(name = "Residency.findResidenciesByResident",
            query = "SELECT r FROM Residency r WHERE r.resident.id = :residentId"),
    @NamedQuery(name = "Residency.findOneResidentResidency",
            query = "SELECT r FROM Residency r WHERE r.id = :residencyId AND r.resident.id = :residentId"),
    @NamedQuery(name = "Residency.getResidenciesWithApartmentId",
            query = "SELECT r FROM Residency r WHERE r.apartment.id= :apartmentId")

})
public class Residency implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromDate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @JoinColumn(name = "apartment", referencedColumnName = "id")
    @ManyToOne
    private Apartment apartment;
    @JoinColumn(name = "resident", referencedColumnName = "id")
    @ManyToOne
    private Resident resident;

    public Residency() {
    }

    public Residency(Integer id) {
        this.id = id;
    }

    public Residency(Integer id, Date fromDate) {
        this.id = id;
        this.fromDate = fromDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
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

    @Override
    public String toString() {
        return "entities.Residency[ id=" + id + " ]";
    }

}
