package resources;

import dtos.CommitmentDTO;
import entities.Commitment;
import exception.DataNotFoundException;
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
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
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
    public Response getCommitmentsByResident(
            @PathParam("residentId") int residentId,
            @Context UriInfo info,
            @Context Request request) {
        List<Commitment> residentCommitments = commitmentFacade.findResidentCommitments(residentId);
        if (residentCommitments == null) {
            throw new DataNotFoundException("Resident with id: " + residentId + " has no commitments!");
        }

        int hashValue = 0;
        List<CommitmentDTO> commitmentDTOs = new ArrayList<>();

        for (Commitment commitment : residentCommitments) {
            CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
            hashValue += commitmentDTO.hashCode();
            commitmentDTO.setLink(Utility.getLinkToSelf(commitmentDTO.getId(), info));
            commitmentDTOs.add(commitmentDTO);
        }

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        GenericEntity<List<CommitmentDTO>> commitmentDTOswrapper = new GenericEntity<List<CommitmentDTO>>(commitmentDTOs) {
        };

        if (builder == null) {
            builder = Response.ok(commitmentDTOswrapper);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{commitmentId: \\d+}")
    public Response getResidentCommitmentById(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId,
            @Context Request request,
            @Context UriInfo info
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        if (commitment == null) {
            throw new DataNotFoundException("Commitment with id: " + residentId + " is not found!");
        }
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        commitmentDTO.setLink(Utility.getLinkToSelf(commitmentDTO.getId(), info));

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
    public Response createCommitment(
            @PathParam("residentId") int residentId,
            @FormParam("role") String role,
            @FormParam("fromDate") String fromDate,
            @FormParam("toDate") String toDate,
            @FormParam("authorized") String authorized,
            @Context HttpHeaders headers,
            @Context Request request,
            @Context UriInfo info
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
        commitment = commitmentFacade.create(commitment);
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        commitmentDTO.setLink(Utility.getLinkToSelf(commitmentDTO.getId(), info));
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(commitmentDTO.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.status(Status.CREATED);
            builder.entity(commitmentDTO);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("{commitmentId: \\d+}")
    public Response updateCommitment(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId,
            @FormParam("role") String role,
            @FormParam("fromDate") String fromDate,
            @FormParam("toDate") String toDate,
            @FormParam("authorized") boolean authorized,
            @Context Request request,
            @Context UriInfo info
    ) {
        Commitment commitment = commitmentFacade.find(commitmentId);
        commitment.setResident(residentFacade.find(residentId));
        commitment.setRole(role);
        commitment.setAuthorized(authorized);
        commitment.setFromDate(Utility.parseStringToDate(fromDate));
        commitment.setToDate(Utility.parseStringToDate(toDate));
        commitment = commitmentFacade.edit(commitment);
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        commitmentDTO.setLink(Utility.getLinkToSelf(commitmentDTO.getId(), info));
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(commitmentDTO.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.status(Status.OK);
            builder.entity(commitmentDTO);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    @DELETE
    @Path("{commitmentId: \\d+}")
    public Response deleteCommitment(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId,
            @Context UriInfo info
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        if (commitment == null) {
            throw new DataNotFoundException("Commitment with id: " + commitmentId + " is not found!");
        }

        commitmentFacade.remove(commitment);
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        commitmentDTO.setLink(Utility.getLinkToResource(commitment.getId(), info));
        return Response.ok(commitment).build();
    }
}
