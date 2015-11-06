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
        builder.cacheControl(cc);

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

    @POST
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    public Response createApartment(
            @FormParam("apartmentNumber") int apartmentNumber,
            @FormParam("addressId") int addressId,
            @FormParam("area") int area,
            @FormParam("floorCode") int floorCode,
            @FormParam("roomCount") int roomCount
    ) {
        Apartment apartment = new Apartment();
        apartment.setApartmentNumber(apartmentNumber);
        Address address = addressFacade.find(addressId);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }

        apartment.setAddress(address);
        if (area <= 25 || area >= 180) {
            return Response.notModified("Given area is out of bounds").build();
        }

        if (area != apartment.getArea()) {
            apartment.setArea(area);
            apartment.setShare(updateShareNumbers(area));
        }
        apartment.setFloorCode(floorCode);
        apartment.setRoomCount(roomCount);
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
            @FormParam("area") int area,
            @FormParam("floorCode") int floorCode
    ) {
        Apartment apartment = apartmentFacade.find(apartmentId);
        apartment.setApartmentNumber(apartmentNumber);
        Address address = addressFacade.find(addressId);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        if (area <= 25 || area >= 180) {
            return Response.notModified("Given area is out of bounds").build();
        }

        if (area != apartment.getArea()) {
            apartment.setArea(area);
            apartment.setShare(updateShareNumbers(area));
        }
        apartment.setAddress(address);
        apartment.setRoomCount(roomCount);
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
        apartmentFacade.remove(apartment);
    }

    @Path("{parentResourceId: \\d+}/residencies")
    @Produces(MediaType.APPLICATION_JSON)
    public ResidencyResource getResidencyResource() {
        return new ResidencyResource();
    }

    public float updateShareNumbers(int apartmentArea) {
        int areaCount = apartmentFacade.getAreaCount();
        List<Apartment> apartments = apartmentFacade.findAll();
        float share;
        for (Apartment apartment : apartments) {
            share = (float) apartment.getArea() / areaCount;
            apartment.setShare(share);
        }
        return 0;
    }
}
