package exception;

import entities.Message;
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
        Message errorMessage = new Message(exception.getMessage(), 404, "http://hmpg.net/");
        return Response.status(Status.NOT_FOUND).entity(errorMessage).build();
    }

}
