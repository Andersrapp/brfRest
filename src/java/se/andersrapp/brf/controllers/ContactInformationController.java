package se.andersrapp.brf.controllers;

import se.andersrapp.brf.entities.ContactInformation;
import se.andersrapp.brf.exception.DataNotFoundException;
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
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import se.andersrapp.brf.services.ContactInformationFacadeLocal;
import se.andersrapp.brf.services.ResidentFacadeLocal;
import se.andersrapp.brf.utilities.JNDIUtility;

/**
 *
 * @author Anders
 */
public class ContactInformationController {

    @EJB
    ContactInformationFacadeLocal contactInformationFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    public ContactInformationController() {
        contactInformationFacade = JNDIUtility.checkJNDI(contactInformationFacade);
        residentFacade = JNDIUtility.checkJNDI(residentFacade);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getResidentContactInformation(
            @PathParam("residentId") int residentId,
            @Context Request request
    ) {
        ContactInformation contactInformation
                = contactInformationFacade.findResidentContactinformation(residentId);

        if (contactInformation == null) {
            throw new DataNotFoundException("ContactInformation for resident id: " + residentId + " is not found!");
        }

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(contactInformation.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(contactInformation);
            builder.tag(eTag);
        }

        builder.cacheControl(cc);
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createContactInformation(
            @PathParam("residentId") int residentId,
            @FormParam("telephone") String telephone,
            @FormParam("email") String email,
            @Context Request request
    ) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setResident(residentFacade.find(residentId));
        contactInformation.setTelephone(telephone);
        contactInformation.setEmail(email);
        contactInformationFacade.create(contactInformation);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(contactInformation.hashCode()));

        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(contactInformation);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
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
        if (contactInformation == null) {
            throw new DataNotFoundException("ContactInformation with id: " + contactInformationId + " is not found!");
        }
        contactInformationFacade.remove(contactInformation);
    }

}
