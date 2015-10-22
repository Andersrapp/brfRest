package services;

import entities.Resident;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Anders
 */
@Stateless
public class ResidentFacade extends AbstractFacade<Resident> implements ResidentFacadeLocal {
    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ResidentFacade() {
        super(Resident.class);
    }
    
}
