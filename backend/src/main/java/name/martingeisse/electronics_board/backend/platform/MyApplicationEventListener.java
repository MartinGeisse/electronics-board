package name.martingeisse.electronics_board.backend.platform;

import jakarta.inject.Inject;
import jakarta.ws.rs.ext.Provider;
import name.martingeisse.electronics_board.backend.application.util.PostHocInjector;
import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

@Provider
public class MyApplicationEventListener implements ApplicationEventListener {

    @Inject
    private jakarta.inject.Provider<PostHocInjector> postHocInjectorProvider;

    @Override
    public void onEvent(ApplicationEvent event) {
        if (event.getType() == ApplicationEvent.Type.INITIALIZATION_START) {
            PostHocInjector.INSTANCE_HOLDER.setValue(postHocInjectorProvider.get());
        }
    }

    @Override
    public RequestEventListener onRequest(RequestEvent requestEvent) {
        return null;
    }

}
