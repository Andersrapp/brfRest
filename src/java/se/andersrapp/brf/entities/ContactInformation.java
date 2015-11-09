package se.andersrapp.brf.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anders
 */
@Entity
@Table(name = "contactinformation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContactInformation.findAll", query = "SELECT c FROM ContactInformation c"),
    @NamedQuery(name = "ContactInformation.findById", query = "SELECT c FROM ContactInformation c WHERE c.residentId = :id"),
    @NamedQuery(name = "ContactInformation.findByTelephone", query = "SELECT c FROM ContactInformation c WHERE c.telephone = :telephone"),
    @NamedQuery(name = "ContactInformation.findByEmail", query = "SELECT c FROM ContactInformation c WHERE c.email = :email"),
    @NamedQuery(name = "ContactInformation.findResidentContactInformation",
            query = "SELECT c FROM ContactInformation c WHERE c.residentId = :residentId")

})
public class ContactInformation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "residentId")
    private Integer residentId;
    @Size(max = 30)
    @Column(name = "telephone")
    private String telephone;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 50)
    @Column(name = "email")
    private String email;
    @JoinColumn(name = "residentId", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Resident resident;
    @Transient
    private Link link;

    public ContactInformation() {
    }

    public ContactInformation(Integer residentId) {
        this.residentId = residentId;
    }

    public Integer getResidentId() {
        return residentId;
    }

    public void setResidentId(Integer residentId) {
        this.residentId = residentId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.residentId);
        hash = 83 * hash + Objects.hashCode(this.telephone);
        hash = 83 * hash + Objects.hashCode(this.email);
        hash = 83 * hash + Objects.hashCode(this.resident);
        hash = 83 * hash + Objects.hashCode(this.link);
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
        final ContactInformation other = (ContactInformation) obj;
        if (!Objects.equals(this.residentId, other.residentId)) {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) {
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



    @Override
    public String toString() {
        return "entities.Contactinformation[ residentId=" + residentId + " ]";
    }

}
