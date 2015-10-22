package controller;

import entity.Address;
import entity.Apartment;
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
import service.AddressFacadeLocal;

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
    public List<Address> getAddresses() {
        List<Address> allAddresses = addressFacade.findAll();
        return allAddresses;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public Address getOneAddressById(
            @PathParam("addressId") int addressId
    ) {
        Address address = addressFacade.find(addressId);
        return address;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public void createAddress(
            @FormParam("country") String country,
            @FormParam("city") String city,
            @FormParam("streetName") String streetName,
            @FormParam("streetNumber") String streetNumber
    ) {
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        List<Apartment> addressApartments = new ArrayList<>();
        address.setApartmentList(addressApartments);
        addressFacade.create(address);
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public void updateAddress(
            @PathParam("addressId") int addressId,
            @FormParam("country") String country,
            @FormParam("city") String city,
            @FormParam("streetName") String streetName,
            @FormParam("streetNumber") String streetNumber
    ) {
        Address address = addressFacade.find(addressId);
        address.setCountry(country);
        address.setCity(city);
        address.setStreetName(streetName);
        address.setStreetNumber(streetNumber);
        addressFacade.edit(address);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{addressId: \\d+}")
    public void deleteAddress(@PathParam("addressId") int addressId
    ) {
        Address address = addressFacade.find(addressId);
        addressFacade.remove(address);
    }

}
