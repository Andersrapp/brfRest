package se.andersrapp.brf.entities;

import java.io.Serializable;
import java.util.Objects;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Apartment.findByArea", query = "SELECT a FROM Apartment a WHERE a.area = :area"),
    @NamedQuery(name = "Apartment.findByFloorCode", query = "SELECT a FROM Apartment a WHERE a.floorCode = :floorCode"),
    @NamedQuery(name = "Apartment.findByShare", query = "SELECT a FROM Apartment a WHERE a.share = :share"),
    @NamedQuery(name = "Apartment.getAreaSum", query = "SELECT count(a.area) FROM Apartment a")

})
public class Apartment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "apartmentNumber")
    private int apartmentNumber;
    @Column(name = "roomCount")
    private Integer roomCount;
    @Basic(optional = false)
    @NotNull
    @Column(name = "area")
    private int area;
    @Column(name = "floorCode")
    private Integer floorCode;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "share")
    private Float share;
    @JoinColumn(name = "address", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address address;
    @Transient
    private Link link;

    public Apartment() {
    }

    public Apartment(Integer id) {
        this.id = id;
    }

    public Apartment(Integer id, int apartmentNumber, int area) {
        this.id = id;
        this.apartmentNumber = apartmentNumber;
        this.area = area;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(int apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public Integer getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount) {
        this.roomCount = roomCount;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public Integer getFloorCode() {
        return floorCode;
    }

    public void setFloorCode(Integer floorCode) {
        this.floorCode = floorCode;
    }

    public Float getShare() {
        return share;
    }

    public void setShare(Float share) {
        this.share = share;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + this.apartmentNumber;
        hash = 97 * hash + Objects.hashCode(this.roomCount);
        hash = 97 * hash + this.area;
        hash = 97 * hash + Objects.hashCode(this.floorCode);
        hash = 97 * hash + Objects.hashCode(this.share);
        hash = 97 * hash + Objects.hashCode(this.address);
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
        final Apartment other = (Apartment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (this.apartmentNumber != other.apartmentNumber) {
            return false;
        }
        if (!Objects.equals(this.roomCount, other.roomCount)) {
            return false;
        }
        if (this.area != other.area) {
            return false;
        }
        if (!Objects.equals(this.floorCode, other.floorCode)) {
            return false;
        }
        if (!Objects.equals(this.share, other.share)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "entities.Apartment[ id=" + id + " ]";
    }

}
