package utilities;

import dtos.CommitmentDTO;
import dtos.ResidencyDTO;
import entities.Commitment;
import entities.Residency;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

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
        commitmentDTO.setToDate(convertDateToLocalDate(commitment.getToDate()));
        commitmentDTO.setAuthorized(commitment.getAuthorized());
        return commitmentDTO;
    }
}
