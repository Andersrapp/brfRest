package utilities;

import dto.CommitmentDTO;
import dto.ResidencyDTO;
import entity.Commitment;
import entity.Residency;
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
        residencyDTO.setToDate(convertDateToLocalDate(residency.getToDate()));
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
        commitmentDTO.setStartDate(convertDateToLocalDate(commitment.getEntryDate()));
        commitmentDTO.setEndDate(convertDateToLocalDate(commitment.getExitDate()));
        commitmentDTO.setAuthorized(commitment.getAuthorized());
        return commitmentDTO;
    }
}
