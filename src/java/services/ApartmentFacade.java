package services;

import entities.Apartment;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
    
}
