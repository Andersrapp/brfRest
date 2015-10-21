package controller;

import dto.ResidencyDTO;
import entity.Address;
import entity.Apartment;
import entity.Residency;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import service.AddressFacadeLocal;
import service.ApartmentFacadeLocal;
import service.ResidencyFacadeLocal;
import service.ResidentFacadeLocal;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("apartments")
public class ApartmentController {

    @EJB
    ApartmentFacadeLocal apartmentFacade;

    @EJB
    AddressFacadeLocal addressFacade;

    @EJB
    ResidencyFacadeLocal residencyFacade;

    @EJB
    ResidentFacadeLocal residentFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}")
    public Apartment getApartmentById(@PathParam("apartmentId") int apartmentId) {
        Apartment apartment = apartmentFacade.find(apartmentId);
        return apartment;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Apartment> getAllApartments() {
        List<Apartment> apartments = apartmentFacade.findAll();
        return apartments;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createApartment(
            @FormParam("apartmentNumber") int apartmentNumber,
            @FormParam("addressId") int addressId,
            @FormParam("area") int area,
            @FormParam("floorCode") int floorCode,
            @FormParam("roomCount") int roomCount,
            @FormParam("share") float share
    ) {
        Apartment apartment = new Apartment();
        apartment.setApartmentNumber(apartmentNumber);
        Address address = addressFacade.find(addressId);
        apartment.setAddress(address);
        apartment.setArea(area);
        apartment.setFloorCode(floorCode);
        apartment.setRoomCount(roomCount);
        apartment.setShare(share);
        apartmentFacade.create(apartment);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}")
    public Response updateApartment(
            @PathParam("apartmentId") int apartmentId,
            @FormParam("apartmentNumber") int apartmentNumber,
            @FormParam("addressId") int addressId,
            @FormParam("roomCount") int roomCount,
            @FormParam("share") float share,
            @FormParam("area") int area,
            @FormParam("floorCode") int floorCode
    ) {
        Apartment apartment = apartmentFacade.find(apartmentId);
        apartment.setApartmentNumber(apartmentNumber);
        Address address = addressFacade.find(addressId);
        apartment.setAddress(address);
        apartment.setRoomCount(roomCount);
        apartment.setShare(share);
        apartment.setArea(area);
        apartment.setFloorCode(floorCode);
        apartmentFacade.edit(apartment);
        return Response.ok(apartment).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}")
    public void deleteApartment(@PathParam("apartmentId") int apartmentId) {
        Apartment apartment = apartmentFacade.find(apartmentId);
        apartmentFacade.remove(apartment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies")
    public List<ResidencyDTO> getApartmentResidencies(
            @PathParam("apartmentId") int apartmentId) {
        List<Residency> apartmentResidencies = residencyFacade.findResidenciesByApartment(apartmentId);
        List<ResidencyDTO> residencyDTOs = new ArrayList<>();
        for (Residency r : apartmentResidencies) {
            residencyDTOs.add(Utility.convertResidencyToDTO(r));
        }
        return residencyDTOs;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response getApartmentResidencyById(
            @PathParam("apartmentId") int apartmentId,
            @PathParam("residencyId") int residencyId
    ) {
        Residency residency = residencyFacade.findOneApartmentResidency(apartmentId, residencyId);
        ResidencyDTO residencyDTO = Utility.convertResidencyToDTO(residency);
        return Response.ok(residencyDTO).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies")
    public void createResidency(
            @PathParam("apartmentId") int apartmentId,
            @FormParam("residentId") int residentId,
            @FormParam("movedInDate") String inDate,
            @FormParam("movedOutDate") String outDate
    ) {
        Residency residency = new Residency();
        residency.setApartment(apartmentFacade.find(apartmentId));
        residency.setResident(residentFacade.find(residentId));
        residency.setFromDate(Utility.parseStringToDate(inDate));
        residency.setToDate(Utility.parseStringToDate(outDate));
        residencyFacade.create(residency);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response updateResidency(
            @PathParam("apartmentId") int apartmentId,
            @PathParam("residencyId") int residencyId,
            @FormParam("residentId") int residentId,
            @FormParam("movedInDate") String inDate,
            @FormParam("movedOutDate") String outDate
    ) {
        Residency residency = residencyFacade.find(residencyId);
        residency.setApartment(apartmentFacade.find(apartmentId));
        residency.setResident(residentFacade.find(residentId));
        residency.setFromDate(Utility.parseStringToDate(inDate));
        residency.setToDate(Utility.parseStringToDate(outDate));

        residencyFacade.edit(residency);
        return Response.ok(residency).build();
    }

    @DELETE
    @Path("{apartmentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response deleteResidency(
            @PathParam("apartmentId") int apartmentId,
            @PathParam("residencyId") int residencyId
    ) {
        Residency residency = residencyFacade.findOneApartmentResidency(apartmentId, residencyId);
        residencyFacade.remove(residency);
        return Response.ok().build();
    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Path("{apartmendId: \\d+}/address")
//    public Response createApartmentAddress() {
//    }

}
