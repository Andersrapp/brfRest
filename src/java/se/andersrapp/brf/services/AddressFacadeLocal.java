package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Address;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders Rapp
 */
@Local
public interface AddressFacadeLocal {

    Address create(Address address);

    Address edit(Address address);

    void remove(Address address);

    Address find(Object id);

    List<Address> findAll();

    List<Address> findRange(int[] range);

    int count();
    
}
