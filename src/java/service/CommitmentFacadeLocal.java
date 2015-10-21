package service;

import entity.Commitment;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface CommitmentFacadeLocal {

    void create(Commitment commitment);

    void edit(Commitment commitment);

    void remove(Commitment commitment);

    Commitment find(Object id);

    List<Commitment> findAll();

    List<Commitment> findRange(int[] range);

    int count();
    
    List<Commitment> findResidentCommitments(int residentId);
    
    Commitment findOneResidentCommitment(int residentId, int commitmendId);
}
