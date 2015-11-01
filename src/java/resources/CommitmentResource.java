package resources;

import dtos.CommitmentDTO;
import entities.Commitment;
import exception.DataNotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import services.CommitmentFacadeLocal;
import services.ResidentFacadeLocal;
import utilities.JNDIUtility;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Produces(MediaType.APPLICATION_JSON)
public class CommitmentResource {

    @EJB
    CommitmentFacadeLocal commitmentFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    public CommitmentResource() {
        commitmentFacade = JNDIUtility.checkJNDI(commitmentFacade);
        residentFacade = JNDIUtility.checkJNDI(residentFacade);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Commitment> getCommitmentsByResident(
            @PathParam("residentId") int residentId
    ) {
        List<Commitment> residentCommitments = commitmentFacade.findResidentCommitments(residentId);
        if (residentCommitments == null) {
            throw new DataNotFoundException("Resident with id: " + residentId + " has no commitments!");
        }

        int hashValue = 0;
        List<CommitmentDTO> commitmentDTOs = new ArrayList<>();
        for (Commitment commitment : residentCommitments) {
            CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
            hashValue += commitmentDTO.hashCode();
            hashValue += commitment.hashCode();

            commitmentDTOs.add(commitmentDTO);
        }
        return residentCommitments;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{commitmentId: \\d+}")
    public Response getResidentCommitmentById(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId,
            @Context Request request
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        if (commitment == null) {
            throw new DataNotFoundException("Commitment with id: " + residentId + " is not found!");
        }
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(commitmentDTO.hashCode()));
        Response.ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(commitmentDTO);
            builder.tag(eTag);
        }
        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public void createCommitment(
            @PathParam("residentId") int residentId,
            //            CommitmentDTO2 com
            @FormParam("role") String role,
            @FormParam("fromDate") String fromDate,
            @FormParam("toDate") String toDate,
            @FormParam("authorized") String authorized,
            @Context HttpHeaders headers
    ) {
        Commitment commitment = new Commitment();

        commitment.setResident(residentFacade.find(residentId));
        commitment.setRole(role);
        commitment.setFromDate(Utility.parseStringToDate(fromDate));
        if (toDate != null) {
            commitment.setToDate(Utility.parseStringToDate(toDate));
        }
        if ("true".equals(authorized)) {
            commitment.setAuthorized(true);
        }
        commitmentFacade.create(commitment);
    }

    @PUT
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("{commitmentId: \\d+}")
    public void updateCommitment(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId,
            @FormParam("role") String role,
            @FormParam("startDate") String startDate,
            @FormParam("endDate") String endDate,
            @FormParam("authorized") boolean authorized
    ) {
        Commitment commitment = commitmentFacade.find(commitmentId);
        commitment.setResident(residentFacade.find(residentId));
        commitment.setRole(role);
//        commitment.setFromDate(Utility.parseStringToDate(startDate));
//        commitment.setToDate(Utility.parseStringToDate(endDate));
        commitment.setAuthorized(authorized);
        commitmentFacade.edit(commitment);
    }

    @DELETE
    @Path("{commitmentId: \\d+}")
    public void deleteCommitment(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        commitmentFacade.remove(commitment);
    }
//
//    private void checkJDNIForCommitmentFacade() {
//        try {
//            String lookupName = "java:global/BrfREST/CommitmentFacade!services.CommitmentFacadeLocal";
//            commitmentFacade = (CommitmentFacadeLocal) InitialContext.doLookup(lookupName);
//        } catch (NamingException e) {
//            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
//        }
//    }
//

    private void checkJDNIforResidentFacade() {
        try {
            String lookupName = "java:global/BrfREST/ResidentFacade!services.ResidentFacadeLocal";
            residentFacade = (ResidentFacadeLocal) InitialContext.doLookup(lookupName);
        } catch (NamingException e) {
            System.out.println("EXCEPTION MESSAGE:::" + e.getMessage());
        }
    }
}
