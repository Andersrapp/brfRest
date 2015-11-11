package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Apartment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders Rapp
 */
@Local
public interface ApartmentFacadeLocal {

    Apartment create(Apartment apartment);

    Apartment edit(Apartment apartment);

    void remove(Apartment apartment);

    Apartment find(Object id);

    List<Apartment> findAll();

    List<Apartment> findRange(int[] range);

    int count();

    long getAreaCount();
    
    List<Apartment> findApartmentsWithAddressId(int addressId);

}
