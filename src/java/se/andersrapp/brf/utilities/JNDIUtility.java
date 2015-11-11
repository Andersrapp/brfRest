package se.andersrapp.brf.utilities;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import se.andersrapp.brf.services.AddressFacadeLocal;
import se.andersrapp.brf.services.ApartmentFacadeLocal;
import se.andersrapp.brf.services.CommitmentFacadeLocal;
import se.andersrapp.brf.services.ResidencyFacadeLocal;
import se.andersrapp.brf.services.ResidentFacadeLocal;

/**
 *
 * @author Anders Rapp
 */
public class JNDIUtility {

    public static AddressFacadeLocal checkJNDI(AddressFacadeLocal addressFacade) {
        try {
            String lookupName = "java:global/BrfREST/AddressFacade!se.andersrapp.brf.services.AddressFacadeLocal";

            addressFacade = (AddressFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return addressFacade;
    }

    public static ApartmentFacadeLocal checkJNDI(ApartmentFacadeLocal apartmentFacade) {
        try {
            String lookupName = "java:global/BrfREST/ApartmentFacade!se.andersrapp.brf.services.ApartmentFacadeLocal";
            apartmentFacade = (ApartmentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return apartmentFacade;
    }

    public static CommitmentFacadeLocal checkJNDI(CommitmentFacadeLocal commitmentFacade) {
        try {
            String lookupName = "java:global/BrfREST/CommitmentFacade!se.andersrapp.brf.services.CommitmentFacadeLocal";
            commitmentFacade = (CommitmentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return commitmentFacade;
    }

    public static ResidencyFacadeLocal checkJNDI(ResidencyFacadeLocal residencyFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidencyFacade!se.andersrapp.brf.services.ResidencyFacadeLocal";
            residencyFacade = (ResidencyFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residencyFacade;
    }

    public static ResidentFacadeLocal checkJNDI(ResidentFacadeLocal residentFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidentFacade!se.andersrapp.brf.services.ResidentFacadeLocal";
            residentFacade = (ResidentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residentFacade;
    }
}
