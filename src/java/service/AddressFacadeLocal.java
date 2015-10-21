package service;

import entity.Address;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface AddressFacadeLocal {

    void create(Address address);

    void edit(Address address);

    void remove(Address address);

    Address find(Object id);

    List<Address> findAll();

    List<Address> findRange(int[] range);

    int count();
    
}
