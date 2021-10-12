package name.martingeisse.electronics_board.backend.application.api.common;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import name.martingeisse.electronics_board.backend.application.util.ClientVisibleException;

@Provider
public class ClientVisibleExceptionMapper implements ExceptionMapper<ClientVisibleException> {

    @Override
    public Response toResponse(ClientVisibleException exception) {
        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON)
                .entity(exception).build();
    }

}
