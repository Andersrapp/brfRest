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
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable ex) {
        Message errorMessage = new Message(ex.getMessage(), 500, "http://hmpg.net/");
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(errorMessage).build();

    }

}
