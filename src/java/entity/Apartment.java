package entity;

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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

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
    @NamedQuery(name = "Apartment.findByShare", query = "SELECT a FROM Apartment a WHERE a.share = :share")})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "apartment")
    private List<Residency> residencyList;
    @JoinColumn(name = "address", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Address address;

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

    @XmlTransient
    public List<Residency> getResidencyList() {
        return residencyList;
    }

    public void setResidencyList(List<Residency> residencyList) {
        this.residencyList = residencyList;
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
