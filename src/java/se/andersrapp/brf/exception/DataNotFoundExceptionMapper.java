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
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException exception) {
        Message message = new Message(exception.getMessage(), 404, "http://hmpg.net/");
        return Response.status(Status.NOT_FOUND).entity(message).build();

    }

}
