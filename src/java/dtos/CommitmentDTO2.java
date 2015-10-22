package dtos;

import java.time.LocalDate;

/**
 *
 * @author Anders
 */
public class CommitmentDTO2 {

    private Integer id;
    private String role;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int residentId;
    private boolean authorized;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate startDate) {
        this.fromDate = startDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate endDate) {
        this.toDate = endDate;
    }

    public int getResidentId() {
        return residentId;
    }

    public void setResidentId(int residentId) {
        this.residentId = residentId;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }
}
