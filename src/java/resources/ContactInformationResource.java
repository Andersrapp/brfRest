package resources;

import entities.ContactInformation;
import entities.Resident;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.ContactInformationFacadeLocal;
import services.ResidentFacadeLocal;
import utilities.JNDIUtility;

/**
 *
 * @author Anders
 */
@Path("/")
public class ContactInformationResource {

    @EJB
    ContactInformationFacadeLocal contactInformationFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    public ContactInformationResource() {
        contactInformationFacade = JNDIUtility.checkJNDI(contactInformationFacade);
        residentFacade = JNDIUtility.checkJNDI(residentFacade);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResidentContactInformation(
            @PathParam("residentId") int residentId
    ) {
        ContactInformation contactInformation
                = contactInformationFacade.findResidentContactinformation(residentId);

        return Response.ok(contactInformation).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void createContactInformation(
            @PathParam("residentId") int residentId,
            @FormParam("telephone") String telephone,
            @FormParam("email") String email
    ) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setResident(residentFacade.find(residentId));
        contactInformation.setTelephone(telephone);
        contactInformation.setEmail(email);
        contactInformationFacade.create(contactInformation);
        Resident resident = residentFacade.find(residentId);
//        resident.setContactInformation(contactInformation);
        residentFacade.edit(resident);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{contactInformationId: \\d+}")
    public void updateContactInformation(
            @PathParam("residentId") int residentId,
            @PathParam("contactInformationId") int contactInformationId,
            @FormParam("telephone") String telephone,
            @FormParam("email") String email
    ) {
        ContactInformation contactInformation
                = contactInformationFacade.findResidentContactinformation(residentId);
        contactInformation.setResident(residentFacade.find(residentId));
        contactInformation.setTelephone(telephone);
        contactInformation.setEmail(email);

        contactInformationFacade.edit(contactInformation);
    }

    @DELETE
    @Path("{contactInformationId: \\d+}")
    public void deleteContactInformation(
            @PathParam("residentId") int residentId,
            @PathParam("contactInformationId") int contactInformationId) {
        ContactInformation contactInformation
                = contactInformationFacade.findResidentContactinformation(residentId);
        contactInformationFacade.remove(contactInformation);
    }

}
