package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Anders
 */
@Entity
@Table(name = "resident")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Resident.findAll", query = "SELECT r FROM Resident r"),
    @NamedQuery(name = "Resident.findById", query = "SELECT r FROM Resident r WHERE r.id = :id"),
    @NamedQuery(name = "Resident.findBySsn", query = "SELECT r FROM Resident r WHERE r.ssn = :ssn"),
    @NamedQuery(name = "Resident.findByFirstName", query = "SELECT r FROM Resident r WHERE r.firstName = :firstName"),
    @NamedQuery(name = "Resident.findByLastName", query = "SELECT r FROM Resident r WHERE r.lastName = :lastName")})
public class Resident implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 12)
    @Column(name = "ssn")
    private String ssn;
    @Size(max = 20)
    @Column(name = "firstName")
    private String firstName;
    @Size(max = 20)
    @Column(name = "lastName")
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resident")
    private List<Residency> residencyList;
    @OneToMany(mappedBy = "resident")
    private List<Commitment> commitmentList;
    @JoinColumn(name = "contactInformation", referencedColumnName = "id")
    @OneToOne 
    private ContactInformation contactInformation;

    public Resident() {
    }

    public Resident(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @XmlTransient
    public List<Residency> getResidencyList() {
        return residencyList;
    }

    public void setResidencyList(List<Residency> residencyList) {
        this.residencyList = residencyList;
    }

    @XmlTransient
    public List<Commitment> getCommitmentList() {
        return commitmentList;
    }

    public void setCommitmentList(List<Commitment> commitmentList) {
        this.commitmentList = commitmentList;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
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
        if (!(object instanceof Resident)) {
            return false;
        }
        Resident other = (Resident) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Resident[ id=" + id + " ]";
    }
    
}
