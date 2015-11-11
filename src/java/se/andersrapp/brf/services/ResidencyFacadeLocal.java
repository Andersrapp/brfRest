package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Residency;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders Rapp
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

    List<Residency> findResidenciesByResident(int residentId);

    Residency findOneResidentResidency(int residencyId, int residentId);
    
    List<Residency> findResidenciesWithApartmentId(int apartmentId);

}
