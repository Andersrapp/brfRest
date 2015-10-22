package services;

import entities.Address;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
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
