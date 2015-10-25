package services;

import entities.Residency;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface ResidencyFacadeLocal {

    Residency create(Residency residency);

    Residency edit(Residency residency);

    void remove(Residency residency);

    Residency find(Object id);

    List<Residency> findAll();

    List<Residency> findRange(int[] range);

    int count();

    List<Residency> findResidenciesByApartment(int apartmentId);

    Residency findOneApartmentResidency(int apartmentId, int residencyId);

    List<Residency> findResidentResidencies(int residentId);

    Residency findOneResidentResidency(int residencyId, int residentId);

}
