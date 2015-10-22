package services;

import entities.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Anders
 */
@Stateless
public class AddressFacade extends AbstractFacade<Address> implements AddressFacadeLocal {
    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressFacade() {
        super(Address.class);
    }
    
}
