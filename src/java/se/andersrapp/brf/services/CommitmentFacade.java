package se.andersrapp.brf.services;

import se.andersrapp.brf.entities.Commitment;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Anders Rapp
 */
@Stateless
public class CommitmentFacade extends AbstractFacade<Commitment> implements CommitmentFacadeLocal {

    @PersistenceContext(unitName = "BrfRESTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommitmentFacade() {
        super(Commitment.class);
    }

    @Override
    public List<Commitment> findResidentCommitments(Object residentId) {
        TypedQuery query = em.createNamedQuery("Commitment.findResidentCommitments", Commitment.class);
        query.setParameter("residentId", residentId);
        List<Commitment> residentCommitments = query.getResultList();
        return residentCommitments;
    }

    @Override
    public Commitment findOneResidentCommitment(int residentId, int commitmentId) {
        TypedQuery query = em.createNamedQuery("Commitment.findOneResidentCommitment", Commitment.class);
        query.setParameter("residentId", residentId).setParameter("commitmentId", commitmentId);
        Commitment commitment = (Commitment) query.getSingleResult();
        return commitment;
    }

}
