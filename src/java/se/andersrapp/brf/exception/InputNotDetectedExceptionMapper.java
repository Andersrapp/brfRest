package se.andersrapp.brf.exception;

import se.andersrapp.brf.entities.Message;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Anders
 */
@Provider
public class InputNotDetectedExceptionMapper implements ExceptionMapper<InputNotDetectedException> {

    @Override
    public Response toResponse(InputNotDetectedException exception) {
        String uri;
        Message errorMessage = new Message(exception.getMessage(), 400, "http://hmpg.net/");
        return Response.status(Status.BAD_REQUEST).entity(errorMessage).build();
    }
    
    

}
