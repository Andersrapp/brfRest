package se.andersrapp.brf.services;

import java.util.List;
import se.andersrapp.brf.entities.Apartment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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
    public long getAreaCount() {
        TypedQuery query = em.createNamedQuery("Apartment.getAreaSum", Integer.class);
        long count = (long) query.getSingleResult();
//        Query q = em.createNativeQuery("SELECT SUM(area) from Apartment");
//        int count = (int) q.getSingleResult();
        return count;
    }

    @Override
    public List<Apartment> findApartmentsWithAddressId(int addressId) {
        System.out.println("Address Id : " + addressId);
        TypedQuery q = em.createNamedQuery("Apartment.getApartmentWithAddressId", Apartment.class);
        q.setParameter("addressId", addressId);
        List<Apartment> apartments = q.getResultList();
        return apartments;
    }

}
