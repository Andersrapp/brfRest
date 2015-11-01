package resources;

import entities.Address;
import exception.DataNotFoundException;
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
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import services.AddressFacadeLocal;

/**
 *
 * @author Anders
 */
@Path("addresses")
public class AddressResource {

    @EJB
    AddressFacadeLocal addressFacade;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAddresses(
            @Context Request request
    ) {
        List<Address> allAddresses = addressFacade.findAll();
        if (allAddresses == null) {
            throw new DataNotFoundException("Addresses are not found!");
        }
        CacheControl cc = new CacheControl();
        cc.setMaxAge(86400);
        cc.setPrivate(true);
        int hashValue = 0;
        for (Address address : allAddresses) {
            hashValue += address.hashCode();
        }
        EntityTag eTag = new EntityTag(Integer.toString(hashValue));

        ResponseBuilder builder = request.evaluatePreconditions(eTag);
        if (builder == null) {
            builder = Response.ok(allAddresses);
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
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAddress(
            @FormParam("city") String city,
            @FormParam("streetName") String streetName,
            @FormParam("streetNumber") String streetNumber,
            @Context Request request
    ) {
        Address address = new Address();
        address.setCity(city);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);

        addressFacade.create(address);
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
        address.setCity(city);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        address = addressFacade.edit(address);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
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
    public void deleteAddress(@PathParam("addressId") int addressId,
            @Context Request request
    ) {
        Address address = addressFacade.find(addressId);
        if (address == null) {
            throw new DataNotFoundException("Address with id: " + addressId + " is not found!");
        }
        addressFacade.remove(address);
    }

}
