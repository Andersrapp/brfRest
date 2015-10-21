package service;

import entity.Apartment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface ApartmentFacadeLocal {

    void create(Apartment apartment);

    void edit(Apartment apartment);

    void remove(Apartment apartment);

    Apartment find(Object id);

    List<Apartment> findAll();

    List<Apartment> findRange(int[] range);

    int count();
    
}
