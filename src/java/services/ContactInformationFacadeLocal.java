package services;

import entities.ContactInformation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Anders
 */
@Local
public interface ContactInformationFacadeLocal {

    ContactInformation create(ContactInformation contactinformation);

    ContactInformation edit(ContactInformation contactinformation);

    void remove(ContactInformation contactinformation);

    ContactInformation find(Object id);

    List<ContactInformation> findAll();

    List<ContactInformation> findRange(int[] range);

    int count();

    ContactInformation findResidentContactinformation(int residentId);

}
