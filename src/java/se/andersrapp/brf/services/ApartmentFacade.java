package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Apartment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Anders
 */
@Stateless
public class ApartmentFacade extends AbstractFacade<Apartment> implements ApartmentFacadeLocal {

    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ApartmentFacade() {
        super(Apartment.class);
    }

    @Override
    public int getAreaCount() {

        Query q = em.createNativeQuery("SELECT SUM(area) from Apartment");
        int count = (int) q.getSingleResult();
        return count;
    }

}
