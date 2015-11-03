package resources;

import dtos.ResidencyDTO;
import entities.Address;
import entities.Apartment;
import entities.Residency;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import services.AddressFacadeLocal;
import services.ApartmentFacadeLocal;
import services.ResidencyFacadeLocal;
import services.ResidentFacadeLocal;
import utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("apartments")
public class ApartmentResource {

    @Context
    UriInfo info;

    @Context
    Request request;

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
    public Response getApartmentById(
            @PathParam("apartmentId") int apartmentId,
            @Context Request request) {

        Apartment apartment;

//        apartmentFacade = JNDIUtility.checkJNDI(apartmentFacade);
        apartment = apartmentFacade.find(apartmentId);
        if (apartment == null) {
            throw new DataNotFoundException("Apartment with id: " + apartmentId + " is not found!");
        }
        apartment.setLink(Utility.getLinkToSelf(apartmentId, info));

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        EntityTag eTag = new EntityTag(Integer.toString(apartment.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(apartment);
            builder.tag(eTag);
        }
//        builder.cacheControl(cc);

        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllApartments(@Context Request request) {
        List<Apartment> apartments = apartmentFacade.findAll();
        if (apartments == null) {
            throw new DataNotFoundException("Apartments not found!");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        GenericEntity<List<Apartment>> apartmentsEntity = new GenericEntity<List<Apartment>>(apartments) {
        };
        int hashValue = 0;
        for (Apartment apartment : apartments) {
            hashValue += apartment.hashCode();
            apartment.setLink(Utility.getLinkToSelf(apartment.getId(), info));
        }

        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(apartmentsEntity);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }
//    @GET
//    @Path("/")
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response getAllApartments(
//            @Context Request request
//    ) {
//        System.out.println("före");
//        List<Apartment> apartments = apartmentFacade.findAll();
//        System.out.println("efter");
// 
//        if (apartments == null) {
//            throw new DataNotFoundException("Apartments not found!");
//        }
//        CacheControl cc = new CacheControl();
//        cc.setMaxAge(86400);
//        cc.setPrivate(true);
//
//        int hashValue = 0;
//        GenericEntity<List<Apartment>> apartmentsEntity = new GenericEntity<List<Apartment>>(apartments) {
//        };
//
//        hashValue = apartments.stream().map((apartment) -> apartment.hashCode()).reduce(hashValue, Integer::sum);
//        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
//        ResponseBuilder builder = request.evaluatePreconditions(eTag);
//        if (builder == null) {
//            builder = Response.ok(apartmentsEntity);
//            builder.tag(eTag);
//        }
//        builder.cacheControl(cc);
//        return builder.build();
//    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createApartment(
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
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }

        apartment.setAddress(address);
        apartment.setArea(area);
        apartment.setFloorCode(floorCode);
        apartment.setRoomCount(roomCount);
        apartment.setShare(share);
        apartment = apartmentFacade.create(apartment);

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        return Response.status(Status.CREATED).entity(apartment).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
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
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        apartment.setAddress(address);
        apartment.setRoomCount(roomCount);
        apartment.setShare(share);
        apartment.setArea(area);
        apartment.setFloorCode(floorCode);
        apartment = apartmentFacade.edit(apartment);
        if (apartment == null) {
            throw new DataNotFoundException("Apartment with id: " + apartmentId + " is not found!");
        }

        return Response.ok().entity(apartment).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}")
    public void deleteApartment(
            @PathParam("apartmentId") int apartmentId
    ) {
        Apartment apartment = apartmentFacade.find(apartmentId);
        if (apartment == null) {
            throw new DataNotFoundException("Apartment with id: " + apartmentId + " is not found!");
        }
//        apartment.setAddress(null);
        apartmentFacade.remove(apartment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies")
    public Response getApartmentResidencies(
            @PathParam("apartmentId") int apartmentId,
            @Context Request request
    ) {
        List<Residency> apartmentResidencies = residencyFacade.findResidenciesByApartment(apartmentId);
        List<ResidencyDTO> residencyDTOs = new ArrayList<>();
        int hashValue = 0;
        for (Residency residency : apartmentResidencies) {
            ResidencyDTO residencyDTO = Utility.convertResidencyToDTO(residency);
            hashValue += residencyDTO.hashCode();
            residencyDTOs.add(residencyDTO);
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        GenericEntity<List<ResidencyDTO>> residencyDTOsEntity = new GenericEntity<List<ResidencyDTO>>(residencyDTOs) {
        };

        if (builder == null) {
            builder = Response.ok(residencyDTOsEntity);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response getApartmentResidencyById(
            @PathParam("apartmentId") int apartmentId,
            @PathParam("residencyId") int residencyId,
            @Context Request request
    ) {
        Residency residency = residencyFacade.findOneApartmentResidency(apartmentId, residencyId);
        if (residency == null) {
            throw new DataNotFoundException(
                    "Residency with id: " + residencyId + " under apartment with id: " + apartmentId + " is not found!");
        }

        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        ResidencyDTO residencyDTO = Utility.convertResidencyToDTO(residency);
        EntityTag eTag = new EntityTag(Integer.toString(residencyDTO.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);

        if (builder == null) {
            builder = Response.ok(residency);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);

        return builder.build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies")
    public void createResidency(
            @PathParam("apartmentId") int apartmentId,
            @FormParam("residentId") int residentId,
            @FormParam("fromDate") String inDate,
            @FormParam("toDate") String outDate
    ) {
        Residency residency = new Residency();
        residency.setApartment(apartmentFacade.find(apartmentId));
        residency.setResident(residentFacade.find(residentId));
        residency.setFromDate(Utility.parseStringToDate(inDate));
        if (outDate != null) {
            residency.setToDate(Utility.parseStringToDate(outDate));
        } else {
            residency.setToDate(null);
        }
        residencyFacade.create(residency);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{apartmentId: \\d+}/residencies/{residencyId: \\d+}")
    public Response updateResidency(
            @PathParam("apartmentId") int apartmentId,
            @PathParam("residencyId") int residencyId,
            @FormParam("residentId") int residentId,
            @FormParam("fromDate") String inDate,
            @FormParam("toDate") String outDate
    ) {
        Residency residency = residencyFacade.find(residencyId);
        residency.setApartment(apartmentFacade.find(apartmentId));
        residency.setResident(residentFacade.find(residentId));
        residency.setFromDate(Utility.parseStringToDate(inDate));
        if (outDate != null) {
            residency.setToDate(Utility.parseStringToDate(outDate));
        } else {
            residency.setToDate(null);
        }

        residencyFacade.edit(residency);
        return Response.ok(Utility.convertResidencyToDTO(residency)).build();
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
}
