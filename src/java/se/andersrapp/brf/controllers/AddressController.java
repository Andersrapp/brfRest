package se.andersrapp.brf.controllers;

import se.andersrapp.brf.entities.Address;
import se.andersrapp.brf.exception.DataNotFoundException;
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
import javax.ws.rs.core.UriInfo;
import se.andersrapp.brf.entities.Apartment;
import se.andersrapp.brf.exception.WrongInputException;
import se.andersrapp.brf.services.AddressFacadeLocal;
import se.andersrapp.brf.services.ApartmentFacadeLocal;
import se.andersrapp.brf.utilities.Utility;

/**
 *
 * @author Anders
 */
@Path("addresses")
public class AddressController {

    @Context
    private UriInfo info;

    @EJB
    AddressFacadeLocal addressFacade;

    @EJB
    ApartmentFacadeLocal apartmentFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses(
            @Context Request request
    ) {
        List<Address> addresses = addressFacade.findAll();
        if (addresses == null) {
            throw new DataNotFoundException("Addresses are not found!");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        for (Address address : addresses) {
            address.setLink(Utility.getLinkToSelf(address.getId(), info));
        }
        EntityTag eTag = new EntityTag(Integer.toString(addresses.hashCode()));

        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        GenericEntity<List<Address>> addressesEntity = new GenericEntity<List<Address>>(addresses) {
        };
        if (builder == null) {
            builder = Response.ok(addressesEntity);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);

        return builder.build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public Response getOneAddressById(
            @PathParam("addressId") int addressId,
            @Context Request request
    ) {
        Address address = addressFacade.find(addressId);

        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        address.setLink(Utility.getLinkToSelf(address.getId(), info));
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(address.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(address);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);

        return builder.build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
    public Response createAddress(
            @FormParam("city") String city,
            @FormParam("streetName") String streetName,
            @FormParam("streetNumber") String streetNumber,
            @Context Request request
    ) {
        Address address = new Address();
        if (!"Gothenburg".equalsIgnoreCase(city.trim())) {
            throw new WrongInputException("Correct input is missing. City must be specified as: \"Gothenburg\"");
        }
        address.setCity(city);

        if (!"Waernsgatan".equalsIgnoreCase(streetName.trim())) {
            throw new WrongInputException("Correct input is missing. Address must be specified as: \"Waernsgatan\"");
        }
        address.setStreetName(streetName);

        if (streetNumber.trim().length() != 2 || !streetNumber.trim().matches("[135][ABC]")) {
            throw new WrongInputException(
                    "Correct input is missing. StreetNumber must be specified like: \"1A\", starting with one digit 1, 3 or 5 and ending with one letter A, B or C");
        }

        address.setStreetNumber(streetNumber);

        address = addressFacade.create(address);
        address.setLink(Utility.getLinkToSelf(address.getId(), info));
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(address.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);

        if (builder == null) {
            builder = Response.ok(address);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public Response updateAddress(
            @PathParam("addressId") int addressId,
            @FormParam("city") String city,
            @FormParam("streetName") String streetName,
            @FormParam("streetNumber") String streetNumber,
            @Context Request request
    ) {
        Address address = addressFacade.find(addressId);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        if (!"Gothenburg".equalsIgnoreCase(city.trim()) || !"Göteborg".equalsIgnoreCase(city.trim())) {
            throw new WrongInputException("Correct input is missing. City must be specified as: \"Gothenburg\"");
        }

        if (!"Waernsgatan".equals(streetName)) {
            throw new WrongInputException("Correct input is missing. Address must be specified as: \"Waernsgatan\"");
        }

        if (streetNumber.trim().length() != 2 || !streetNumber.trim().matches("[135][ABC]")) {
            throw new WrongInputException(
                    "Correct input is missing. StreetNumber must be specified like: \"1A\", starting with one digit 1, 3 or 5 and ending with one letter A, B or C");
        }

        address.setCity(city);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address = addressFacade.edit(address);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        address.setLink(Utility.getLinkToSelf(address.getId(), info));
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);

        EntityTag eTag = new EntityTag(Integer.toString(address.hashCode()));
        ResponseBuilder builder = request.evaluatePreconditions(eTag);

        if (builder == null) {
            builder = Response.ok(address);
            builder.tag(eTag);
        }
        builder.cacheControl(cc);
        return builder.build();

    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public Response deleteAddress(
            @PathParam("addressId") int addressId,
            @Context Request request
    ) {
        Address address = addressFacade.find(addressId);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }

        List<Apartment> apartmentsOnAddress = apartmentFacade.findApartmentsWithAddressId(address.getId());
        for (Apartment apartment : apartmentsOnAddress) {
            apartment.setAddress(null);
            apartmentFacade.edit(apartment);
        }

        addressFacade.remove(address);
        return Response.ok(address).build();
    }
}
