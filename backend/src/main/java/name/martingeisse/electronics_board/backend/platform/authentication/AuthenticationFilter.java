package name.martingeisse.electronics_board.backend.platform.authentication;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.inject.Provider;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.HttpHeaders;

import java.io.IOException;

@jakarta.ws.rs.ext.Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Inject
    private Provider<TokenHolder> tokenHolderProvider;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null) {
            if (authorizationHeader.toLowerCase().startsWith("bearer ")) {
                tokenHolderProvider.get().token = authorizationHeader.substring(7).trim();
                return;
            }
        }
        // TODO authoriztation disabled for testing
        tokenHolderProvider.get().token = null;
//        requestContext.abortWith(
//                Response.status(Response.Status.UNAUTHORIZED)
//                        .header(HttpHeaders.WWW_AUTHENTICATE, "Bearer")
//                        .build());
    }

}
