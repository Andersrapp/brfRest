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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anders
 */
@Entity
@Table(name = "commitment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commitment.findAll", query = "SELECT c FROM Commitment c"),
    @NamedQuery(name = "Commitment.findById", query = "SELECT c FROM Commitment c WHERE c.id = :id"),
    @NamedQuery(name = "Commitment.findByRole", query = "SELECT c FROM Commitment c WHERE c.role = :role"),
    @NamedQuery(name = "Commitment.findByFromDate", query = "SELECT c FROM Commitment c WHERE c.fromDate = :fromDate"),
    @NamedQuery(name = "Commitment.findByToDate", query = "SELECT c FROM Commitment c WHERE c.toDate = :toDate"),
    @NamedQuery(name = "Commitment.findByAuthorized", query = "SELECT c FROM Commitment c WHERE c.authorized = :authorized"),
    @NamedQuery(name = "Commitment.findResidentCommitments",
            query = "SELECT c FROM Commitment c WHERE c.resident.id = :residentId"),
    @NamedQuery(name = "Commitment.findOneResidentCommitment",
            query = "SELECT c FROM Commitment c WHERE c.id = :commitmentId AND c.resident.id = :residentId")
})

public class Commitment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "role")
    private String role;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fromDate")
    @Temporal(TemporalType.DATE)
    private Date fromDate;
    @Column(name = "toDate")
    @Temporal(TemporalType.DATE)
    private Date toDate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "authorized")
    private boolean authorized;
    @JoinColumn(name = "resident", referencedColumnName = "id")
    @ManyToOne
    private Resident resident;

    public Commitment() {
    }

    public Commitment(Integer id) {
        this.id = id;
    }

    public Commitment(Integer id, String role, Date fromDate, boolean authorized) {
        this.id = id;
        this.role = role;
        this.fromDate = fromDate;
        this.authorized = authorized;
    }

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

    public boolean getAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public Resident getResident() {
        return resident;
    }

    public void setResident(Resident resident) {
        this.resident = resident;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commitment)) {
            return false;
        }
        Commitment other = (Commitment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Commitment[ id=" + id + " ]";
    }

}
