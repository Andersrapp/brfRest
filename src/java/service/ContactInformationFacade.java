package service;

import entity.ContactInformation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Anders
 */
@Stateless
public class ContactInformationFacade extends AbstractFacade<ContactInformation> implements ContactInformationFacadeLocal {

    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ContactInformationFacade() {
        super(ContactInformation.class);
    }

    @Override
    public ContactInformation findResidentContactinformation(int residentId) {
        TypedQuery query
                = em.createNamedQuery("ContactInformaion.findResidentContactInformation",
                        ContactInformation.class);
        query.setParameter("residentId", residentId);
        ContactInformation contactinformation = (ContactInformation) query.getSingleResult();
        return contactinformation;
    }

}
