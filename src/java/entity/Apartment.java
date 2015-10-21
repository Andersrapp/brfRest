package entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Anders
 */
@Entity
@Table(name = "apartment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Apartment.findAll", query = "SELECT a FROM Apartment a"),
    @NamedQuery(name = "Apartment.findById", query = "SELECT a FROM Apartment a WHERE a.id = :id"),
    @NamedQuery(name = "Apartment.findByApartmentNumber", query = "SELECT a FROM Apartment a WHERE a.apartmentNumber = :apartmentNumber"),
    @NamedQuery(name = "Apartment.findByRoomCount", query = "SELECT a FROM Apartment a WHERE a.roomCount = :roomCount"),
    @NamedQuery(name = "Apartment.findByShare", query = "SELECT a FROM Apartment a WHERE a.share = :share"),
    @NamedQuery(name = "Apartment.findByAreaSqm", query = "SELECT a FROM Apartment a WHERE a.areaSqm = :areaSqm"),
    @NamedQuery(name = "Apartment.findByFloorCode", query = "SELECT a FROM Apartment a WHERE a.floorCode = :floorCode")})
public class Apartment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "apartmentNumber")
    private Integer apartmentNumber;
    @Column(name = "roomCount")
    private Integer roomCount;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "share")
    private Float share;
    @Column(name = "area_sqm")
    private Integer areaSqm;
    @Column(name = "floorCode")
    private Integer floorCode;
    @JoinColumn(name = "address", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address address;

    public Apartment() {
    }

    public Apartment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(Integer apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public Float getShare() {
        return share;
    }

    public void setShare(Float share) {
        this.share = share;
    }

    public Integer getAreaSqm() {
        return areaSqm;
    }

    public void setAreaSqm(Integer areaSqm) {
        this.areaSqm = areaSqm;
    }

    public Integer getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(Integer floorCode) {
        this.floorCode = floorCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        if (!(object instanceof Apartment)) {
            return false;
        }
        Apartment other = (Apartment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Apartment[ id=" + id + " ]";
    }
    
}
