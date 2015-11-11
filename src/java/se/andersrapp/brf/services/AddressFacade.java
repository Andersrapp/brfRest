package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Address;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Anders Rapp
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
