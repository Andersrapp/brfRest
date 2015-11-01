package utilities;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import services.AddressFacadeLocal;
import services.ApartmentFacadeLocal;
import services.CommitmentFacadeLocal;
import services.ContactInformationFacadeLocal;
import services.ResidencyFacadeLocal;
import services.ResidentFacadeLocal;

/**
 *
 * @author Anders
 */
public class JNDIUtility {

    public static AddressFacadeLocal checkJNDI(AddressFacadeLocal addressFacade) {
        try {
            String lookupName = "java:global/BrfREST/AddressFacade!services.AddressFacadeLocal";

            addressFacade = (AddressFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return addressFacade;
    }

    public static ApartmentFacadeLocal checkJNDI(ApartmentFacadeLocal apartmentFacade) {
        try {
            String lookupName = "java:global/BrfREST/ApartmentFacade!services.ApartmentFacadeLocal";
            apartmentFacade = (ApartmentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return apartmentFacade;
    }

    public static CommitmentFacadeLocal checkJNDI(CommitmentFacadeLocal commitmentFacade) {
        try {
            String lookupName = "java:global/BrfREST/CommitmentFacade!services.CommitmentFacadeLocal";
            commitmentFacade = (CommitmentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return commitmentFacade;
    }

    public static ContactInformationFacadeLocal checkJNDI(ContactInformationFacadeLocal contactInformationFacade) {
        try {
            String lookupName = "java:global/BrfREST/ContactInformationFacade!services.ContactInformationFacadeLocal";
            contactInformationFacade = (ContactInformationFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return contactInformationFacade;
    }

    public static ResidencyFacadeLocal checkJNDI(ResidencyFacadeLocal residencyFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidencyFacade!services.ResidencyFacadeLocal";
            residencyFacade = (ResidencyFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residencyFacade;
    }

    public static ResidentFacadeLocal checkJNDI(ResidentFacadeLocal residentFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidentFacade!services.ResidentFacadeLocal";
            residentFacade = (ResidentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residentFacade;
    }
}
