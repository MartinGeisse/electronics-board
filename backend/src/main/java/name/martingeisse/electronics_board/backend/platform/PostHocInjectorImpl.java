package name.martingeisse.electronics_board.backend.platform;

import jakarta.inject.Inject;
import name.martingeisse.electronics_board.backend.application.util.PostHocInjector;
import org.glassfish.hk2.api.ServiceLocator;

public class PostHocInjectorImpl implements PostHocInjector {

    @Inject
    ServiceLocator serviceLocator;

    public <T> T inject(T target) {
        serviceLocator.inject(target);
        return target;
    }

}
