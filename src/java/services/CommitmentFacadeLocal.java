package services;

import entities.Commitment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface CommitmentFacadeLocal {

    Commitment create(Commitment commitment);

    Commitment edit(Commitment commitment);

    void remove(Commitment commitment);

    Commitment find(Object id);

    List<Commitment> findAll();

    List<Commitment> findRange(int[] range);

    int count();
    
    List<Commitment> findResidentCommitments(Object residentId);
    
    Commitment findOneResidentCommitment(int residentId, int commitmendId);
}
