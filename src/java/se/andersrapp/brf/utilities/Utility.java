package se.andersrapp.brf.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.ws.rs.core.UriInfo;
import se.andersrapp.brf.dtos.CommitmentDTO;
import se.andersrapp.brf.dtos.ResidencyDTO;
import se.andersrapp.brf.entities.Commitment;
import se.andersrapp.brf.entities.Link;
import se.andersrapp.brf.entities.Residency;

/**
 *
 * @author Anders
 */
public class Utility {

    public static LocalDate convertDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static ResidencyDTO convertResidencyToDTO(Residency residency) {
        ResidencyDTO residencyDTO = new ResidencyDTO();
        residencyDTO.setId(residency.getId());
        residencyDTO.setApartment(residency.getApartment());
        residencyDTO.setResident(residency.getResident());
        residencyDTO.setFromDate(convertDateToLocalDate(residency.getFromDate()));
        Date toDate = residency.getToDate();
        if (toDate != null) {
            residencyDTO.setToDate(convertDateToLocalDate(toDate));
        }

        return residencyDTO;
    }

    public static Date parseStringToDate(String input) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = formatter.parse(input);
        } catch (ParseException e) {
            date = new Date();
        }
        return date;
    }

    public static CommitmentDTO convertCommitmentToDTO(Commitment commitment) {
        CommitmentDTO commitmentDTO = new CommitmentDTO();
        commitmentDTO.setId(commitment.getId());
        commitmentDTO.setRole(commitment.getRole());
        commitmentDTO.setResident(commitment.getResident());
        commitmentDTO.setFromDate(convertDateToLocalDate(commitment.getFromDate()));

        Date toDate = commitment.getToDate();
        if (toDate != null) {
            commitmentDTO.setToDate(convertDateToLocalDate(toDate));
        }

        commitmentDTO.setAuthorized(commitment.getAuthorized());
        return commitmentDTO;
    }

    public static Link getLinkToSelf(int entityId, UriInfo info) {
        String uri = info.getAbsolutePathBuilder().path(Integer.toString(entityId)).build().toString();
        return new Link(uri, "self");
    }

    public static Link getLinkToResource(UriInfo info) {
        String uri = info.getAbsolutePath().toString();
        return new Link(uri, "parent");
    }

    public static Link getLinkToSubresource(int entityId, UriInfo info) {
        String uri = info.getAbsolutePathBuilder().build().toString();
        return new Link(uri, "child");
    }
}
