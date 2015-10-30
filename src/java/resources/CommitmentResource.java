package resources;

import dtos.CommitmentDTO;
import entities.Commitment;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import services.CommitmentFacadeLocal;
import services.ResidentFacadeLocal;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CommitmentResource {

    @EJB
    CommitmentFacadeLocal commitmentFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    public CommitmentResource(CommitmentFacadeLocal commitmentFacade) {
        this.commitmentFacade = commitmentFacade;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Commitment> getCommitmentsByResident(@PathParam("residentId") int residentId) {
        List<Commitment> residentCommitments = new ArrayList<>();
        residentCommitments = commitmentFacade.findResidentCommitments(residentId);
        List<CommitmentDTO> commitmentDTOs = new ArrayList<>();
        residentCommitments.stream().forEach((c) -> {
            commitmentDTOs.add(Utility.convertCommitmentToDTO(c));
        });
        return residentCommitments;
    }

    @GET
//    @Produces(MediaType.APPLICATION_JSON)
    @Path("{commitmentId: \\d+}")
    public Response getResidentCommitmentById(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        return Response.ok(commitmentDTO).build();
    }

    @POST
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
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
//        commitment.setFromDate(Utility.parseStringToDate(fromDate));
//        commitment.setToDate(Utility.parseStringToDate(toDate));
//        commitment.setFromDate(Date.from(com.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        commitment.setToDate(Date.from(com.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
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

}
