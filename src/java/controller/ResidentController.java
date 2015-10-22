package controller;

import dto.CommitmentDTO;
import dto.ResidencyDTO;
import entity.Commitment;
import entity.ContactInformation;
import entity.Residency;
import entity.Resident;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AddressFacadeLocal;
import service.ApartmentFacadeLocal;
import service.CommitmentFacadeLocal;
import service.ContactInformationFacadeLocal;
import service.ResidencyFacadeLocal;
import service.ResidentFacadeLocal;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("residents")
public class ResidentController {

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
    public List<Resident> getAllResidents() {
        List<Resident> residents = residentFacade.findAll();
        return residents;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public Resident getOneResidentById(@PathParam("residentId") int residentId) {
        Resident resident = residentFacade.find(residentId);
        return resident;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createResident(
            //            @FormParam("ssn") String ssn,
            //            @FormParam("firstName") String firstName,
            //            @FormParam("lastName") String lastName
            Resident resident
    ) {
//        if (ssn == null) {
//            return Response.status(Response.Status.BAD_REQUEST)
//                    .entity("SSN is missing")
//                    .type(MediaType.TEXT_PLAIN)
//                    .build();
//        }
//        Resident resident = new Resident();
//        resident.setSsn(ssn);
//        resident.setFirstName(firstName);
//        resident.setLastName(lastName);
        residentFacade.create(resident);
        return Response.ok(resident, "application/json").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public void updateResident(
            @PathParam("residentId") int residentId,
            //            @FormParam("ssn") String ssn,
            //            @FormParam("firstName") String firstName,
            //            @FormParam("lastName") String lastName
            Resident resident
    ) {
        resident.setId(residentId);
//        Resident resident = getOneResidentById(residentId);
//        resident.setSsn(ssn);
//        resident.setFirstName(firstName);
//        resident.setLastName(lastName);
        residentFacade.edit(resident);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}")
    public void deleteResident(@PathParam("residentId") int residentId) {
        Resident resident = getOneResidentById(residentId);
        residentFacade.remove(resident);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/residencies")
    public List<ResidencyDTO> getResidenciesByResidentId(
            @PathParam("residentId") int residentId
    //            ,
    //            @QueryParam("year") int year
    ) {
//
//        if (year > 0) {
//        }
        List<Residency> residencies = residencyFacade.findResidentResidencies(residentId);
        List<ResidencyDTO> residencyDTOs = new ArrayList<>();
        for (Residency residency : residencies) {
            residencyDTOs.add(Utility.convertResidencyToDTO(residency));
        }
        return residencyDTOs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response getResidentResidencyById(
            @PathParam("residentId") int residentId,
            @PathParam("residencyId") int residencyId
    ) {
        Residency residency = residencyFacade.findOneResidentResidency(residentId, residencyId);
        return Response.ok(residency).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/commitments")
    public List<CommitmentDTO> getCommitmentsByResident(@PathParam("residentId") int residentId) {
        List<Commitment> residentCommitments = commitmentFacade.findResidentCommitments(residentId);
        List<CommitmentDTO> commitmentDTOs = new ArrayList<>();
        for (Commitment c : residentCommitments) {
            commitmentDTOs.add(Utility.convertCommitmentToDTO(c));
        }
        return commitmentDTOs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/commitments/{commitmentId: \\d+}")
    public Response getResidentCommitmentById(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        CommitmentDTO commitmentDTO = Utility.convertCommitmentToDTO(commitment);
        return Response.ok(commitmentDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/commitments")
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
        commitment.setToDate(Utility.parseStringToDate(toDate));
//        commitment.setFromDate(Date.from(com.getFromDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
//        commitment.setToDate(Date.from(com.getToDate().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        if ("true".equals(authorized)) {
            commitment.setAuthorized(true);
        }

        commitmentFacade.create(commitment);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/commitments/{commitmentId: \\d+}")
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
        commitment.setFromDate(Utility.parseStringToDate(startDate));
        commitment.setToDate(Utility.parseStringToDate(endDate));
        commitment.setAuthorized(authorized);
        commitmentFacade.edit(commitment);
    }

    @DELETE
    @Path("{residentId: \\d+}/commitments/{commitmentId: \\d+}")
    public void deleteCommitment(
            @PathParam("residentId") int residentId,
            @PathParam("commitmentId") int commitmentId
    ) {
        Commitment commitment = commitmentFacade.findOneResidentCommitment(residentId, commitmentId);
        commitmentFacade.remove(commitment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{residentId: \\d+}/contactinformation")
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
    @Path("{residentId: \\d+}/contactinformation")
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
    @Path("{residentId: \\d+}/contactinformation/{contactInformationId: \\d+}")
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
    @Path("{residentId: \\d+}/contactinformation/{contactInformationId: \\d+}")
    public void deleteContactInformation(
            @PathParam("residentId") int residentId,
            @PathParam("contactInformationId") int contactInformationId) {
        ContactInformation contactInformation
                = contactInformationFacade.findResidentContactinformation(residentId);
        contactInformationFacade.remove(contactInformation);
    }

}
