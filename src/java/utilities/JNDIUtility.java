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

    public static ResidentFacadeLocal checkJNDI(ResidentFacadeLocal residentFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidentFacade!services.ResidentFacadeLocal";
            residentFacade = (ResidentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residentFacade;
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

    public static ResidencyFacadeLocal checkJNDI(ResidencyFacadeLocal residencyFacade) {
        try {
            String lookupName = "java:global/BrfREST/ResidencyFacade!services.ResidencyFacadeLocal";
            residencyFacade = (ResidencyFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return residencyFacade;
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

    public static AddressFacadeLocal checkJNDI(AddressFacadeLocal addressFacade) {
        try {
            String lookupName = "java:global/BrfREST/AddressFacade!services.AddressFacadeLocal";

            addressFacade = (AddressFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
        return addressFacade;
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
    
//    public static Class<?> checkJNDI(Class<?> t) {
//
//        String lookupName = "";
//
//        switch (t.getClass().toString()) {
//            case ("AddressFacadeLocal"):
//                lookupName = "java:global/BrfREST/AddressFacade!services.AddressFacadeLocal";
//                break;
//            case ("ApartmentFacadeLocal"):
//                lookupName = "java:global/BrfREST/AddressFacade!services.AddressFacadeLocal";
//                break;
//            case ("CommitmentFacadeLocal"):
//                lookupName = "java:global/BrfREST/CommitmentFacade!services.CommitmentFacadeLocal";
//                break;
//            case ("ContactInformationFacadeLocal"):
//                lookupName = "java:global/BrfREST/ContactInformationFacade!services.ContactInformationFacadeLocal";
//                break;
//            case ("ResidencyFacadeLocal"):
//                lookupName = "java:global/BrfREST/ResidencyFacade!services.ResidencyFacadeLocal";
//                break;
//            case ("ResidentFacadeLocal"):
//                lookupName = "java:global/BrfREST/ResidencyFacade!services.ResidencyFacadeLocal";
//                break;
//        }
//
//        try {
//            t = InitialContext.doLookup(lookupName);
//        } catch (Exception e) {
////            e.printStackTrace();
//        }
//        return t;
//    }
//
//}
//
//class Facade<T> {
//
//    T t;
//
//    public Facade(T t) {
//        this.t = t;
//    }
//
//    public T getFacade() {
//        return t;
//    }
//
}
