package resources;

import dtos.ResidencyDTO;
import entities.Residency;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import services.ResidencyFacadeLocal;
import utilities.JNDIUtility;
import utilities.Utility;

/**
 *
 * @author Anders
 */
public class ResidencyResource {

    @EJB
    ResidencyFacadeLocal residencyFacade;

    public ResidencyResource() {
        JNDIUtility.checkJNDI(residencyFacade);
    }

    @GET
    public void getAllResidencies(
            @PathParam("parentResourceId") int parentResourceId
            ,
            //            @PathParam("residencyId") int residencyId,
            @Context Request request,
            @Context UriInfo info
    ) {
        List<Residency> residencies = new ArrayList<>();
//        String uri = info.getAbsolutePathBuilder().build().toString();
            System.out.println("Before facade call: "+  parentResourceId);
            
//        if ("http://localhost:8080/brfRest/api/residents".equals(uri.substring(0, 43))) {
            residencies = residencyFacade.findResidentResidencies(1);
            System.out.println("After facade call: " + residencies.size()); 
//        } else {
//            System.out.println("Before facade call: ");
//            residencies = residencyFacade.findResidenciesByApartment(parentResourceId);
//            System.out.println("After facade call: ");
//        }
//
//        int hashValue = 0;
//        List<ResidencyDTO> residencyDTOs = new ArrayList<>();
//        for (Residency residency : residencies) {
//            ResidencyDTO residencyDTO = Utility.convertResidencyToDTO(residency);
//            hashValue += residencyDTO.hashCode();
//            residencyDTOs.add(residencyDTO);
//        }
//        CacheControl cc = new CacheControl();
//        cc.setMaxAge(86400);
//        cc.setPrivate(true);
//
//        EntityTag eTag = new EntityTag(Integer.toString(hashValue));
//        ResponseBuilder builder = request.evaluatePreconditions(eTag);
//        GenericEntity<List<ResidencyDTO>> residencyDTOWrapper = new GenericEntity<List<ResidencyDTO>>(residencyDTOs) {
//        };
//
//        if (builder == null) {
//            builder = Response.ok();
//            builder.entity(residencyDTOWrapper);
//            builder.tag(eTag);
//        }
//        builder.cacheControl(cc);
//
//        return builder.build();
    }

//    @GET
//    @Path("{residencyId}")
//    public void getOneResidency(
//            @PathParam("parentResourceId") int parentResourceId,
//            @PathParam("residencyId") int residencyId,
//            @Context Request request,
//            @Context UriInfo info
//    ) {

//        Residency residency = new Residency();
//        String uri = info.getAbsolutePathBuilder().build().toString();
//        if ("http://localhost:8080/brfRest/api/residents".equals(uri.substring(0, 43))) {
//            residency = residencyFacade.findOneResidentResidency(parentResourceId, residencyId);
//        } else {
//            residency = residencyFacade.findOneApartmentResidency(parentResourceId, residencyId);
//        }
//        ResidencyDTO residencyDTO = Utility.convertResidencyToDTO(residency);
//
//        CacheControl cc = new CacheControl();
//        cc.setMaxAge(86400);
//        cc.setPrivate(true);
//
//        EntityTag eTag = new EntityTag(Integer.toString(residencyDTO.hashCode()));
//
//        ResponseBuilder builder = request.evaluatePreconditions(eTag);
//        if (builder == null) {
//            builder = Response.ok();
//            builder.entity(residency);
//            builder.tag(eTag);
//        }
//        builder.cacheControl(cc);
//
//        return builder.build();
//    }
}
