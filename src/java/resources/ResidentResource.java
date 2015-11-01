package resources;

import dtos.ResidencyDTO;
import entities.Residency;
import entities.Resident;
import exception.DataNotFoundException;
import exception.InputNotDetectedException;
import java.util.ArrayList;
import java.util.List;
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
import javax.ws.rs.core.UriInfo;
import services.AddressFacadeLocal;
import services.ApartmentFacadeLocal;
import services.CommitmentFacadeLocal;
import services.ContactInformationFacadeLocal;
import services.ResidencyFacadeLocal;
import services.ResidentFacadeLocal;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("residents")
public class ResidentResource {

    @Context
    UriInfo info;

    @EJB
    ApartmentFacadeLocal apartmentFacade;

    @EJB
    AddressFacadeLocal addressFacade;

    @EJB
    ResidencyFacadeLocal residencyFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    @EJB
    CommitmentFacadeLocal commitmentFacade;

    @EJB
    ContactInformationFacadeLocal contactInformationFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllResidents(
            @Context Request request
    ) {

        List<Resident> residents = residentFacade.findAll();
        int hashValue = 0;

        for (Resident resident : residents) {
            hashValue += resident.hashCode();
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(residents);
            builder.tag(eTag);
        }
//        Använd ETag Header i requestet.
        builder.cacheControl(cc);
        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public Response getOneResidentById(
            @PathParam("residentId") int residentId,
            @Context Request request
    ) {
        Resident resident = residentFacade.find(residentId);
        if (resident == null) {
            throw new DataNotFoundException("Resident with id: " + residentId + " not found!");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(resident.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(resident);
            builder.tag(eTag);
        }
        return builder.build();
    }

    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResident(
            @FormParam("ssn") String ssn,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @Context Request request
    ) {
        if (ssn == null) {
            throw new InputNotDetectedException("Social Security Number is missing!");
        }
        Resident resident = new Resident();
        resident.setSsn(ssn);
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident = residentFacade.create(resident);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(resident.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(resident);
            builder.tag(eTag);
        }
        return builder.build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public Response updateResident(
            @PathParam("residentId") int residentId,
            @FormParam("ssn") String ssn,
            @FormParam("firstName") String firstName,
            @FormParam("lastName") String lastName,
            @Context Request request
    ) {
        Resident resident = residentFacade.find(residentId);

        if (resident == null) {
            throw new DataNotFoundException("Resident with id: " + residentId + " not found!");
        }

        resident.setId(residentId);
        resident.setSsn(ssn);
        resident.setFirstName(firstName);
        resident.setLastName(lastName);
        resident = residentFacade.edit(resident);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(resident.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(resident);
            builder.tag(eTag);
        }
        return builder.build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public void deleteResident(
            @PathParam("residentId") int residentId,
            @Context Request request
    ) {
        Resident resident = residentFacade.find(residentId);
        residentFacade.remove(resident);

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/residencies")
    public Response getResidenciesByResidentId(
            @PathParam("residentId") int residentId,
            @Context Request request
    ) {
        int hashValue = 0;
        List<Residency> residencies = residencyFacade.findResidentResidencies(residentId);
        List<ResidencyDTO> residencyDTOs = new ArrayList<>();
        ResidencyDTO residencyDTO;
        for (Residency residency : residencies) {
            residencyDTO = Utility.convertResidencyToDTO(residency);
            residencyDTOs.add(residencyDTO);
            hashValue += residencyDTO.hashCode();
//            hashValue += residency.hashCode();
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
//            builder = Response.ok(residencyDTOs);
            builder = Response.ok(residencyDTOs);
            builder.tag(eTag);
        }
//        Använd ETag Header i requestet.
        builder.cacheControl(cc);
        return builder.build();

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId:  \\d+}/residencies/{residencyId: \\d+}")
    public Response getResidentResidencyById(
            @PathParam("residentId") int residentId,
            @PathParam("residencyId") int residencyId,
            @Context Request request
    ) {
        Residency residency = residencyFacade.findOneResidentResidency(residentId, residencyId);

        if (residency == null) {
            throw new DataNotFoundException("Residency with id: " + residentId + " not found!");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(residency.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(residency);
            builder.tag(eTag);
        }
        return builder.build();
    }

    @Path("{residentId: \\d+}/contactinformation")
    public ContactInformationResource getContactInformationResource() {
        return new ContactInformationResource();
    }

    @Path("{residentId: \\d+}/commitments")
    @Consumes(MediaType.APPLICATION_JSON)
    public CommitmentResource getCommitmentResource() {
        return new CommitmentResource();
    }
}
