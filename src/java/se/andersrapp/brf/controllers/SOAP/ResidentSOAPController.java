package se.andersrapp.brf.controllers.SOAP;

import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import se.andersrapp.brf.entities.Resident;
import se.andersrapp.brf.services.ResidentFacadeLocal;

/**
 *
 * @author Anders
 */
@WebService(name = "ResidentControll", serviceName = "ResidentService", portName = "ResidentPort")
public class ResidentSOAPController {

    @EJB
    private ResidentFacadeLocal residentFacade;

    @WebMethod(operationName = "GetAllResidents", action = "get_all_residents")
    public List<Resident> getResidents() {
        List<Resident> residents = residentFacade.findAll();
        return residents;
    }

    @WebMethod(operationName = "getOneResidentById", action = "get_one_resident_by_id")
    public Resident getOneResidentById(
            @WebParam(partName = "residentId") int residentId
    ) {
        Resident resident = residentFacade.find(residentId);
        return resident;
    }

}
