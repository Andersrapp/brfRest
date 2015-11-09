package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Residency;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Anders
 */
@Stateless
public class ResidencyFacade extends AbstractFacade<Residency> implements ResidencyFacadeLocal {

    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResidencyFacade() {
        super(Residency.class);
    }

    public List<Residency> findResidenciesByApartment(int apartmentId) {
        TypedQuery query = em.createNamedQuery("Residency.findResidenciesByApartment", Residency.class);
        query.setParameter("apartmentId", apartmentId);
        List<Residency> apartmentResidencies = query.getResultList();
        return apartmentResidencies;
    }

    public Residency findOneApartmentResidency(int apartmentId, int residencyId) {
        TypedQuery query = em.createNamedQuery("Residency.findOneApartmentResidency", Residency.class);
        query.setParameter("apartmentId", apartmentId).setParameter("residencyId", residencyId);
        Residency residency = (Residency) query.getSingleResult();
        return residency;
    }

    @Override
    public List<Residency> findResidentResidencies(int residentId) {
        TypedQuery query = em.createNamedQuery("Residency.findResidenciesByResident", Residency.class);
        query.setParameter("residentId", residentId);
        List<Residency> residencies = query.getResultList();
        return residencies;
    }

    @Override
    public Residency findOneResidentResidency(int residencyId, int residentId) {
        TypedQuery query = em.createNamedQuery("Residency.findOneResidentResidency", Residency.class);
        query.setParameter("residentId", residentId).setParameter("residencyId", residencyId);
        Residency residency = (Residency) query.getSingleResult();
        return residency;
    }

}
