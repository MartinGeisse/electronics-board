package name.martingeisse.electronics_board.backend.application.util;

import name.martingeisse.electronics_board.backend.util.Holder;
import org.glassfish.hk2.api.ServiceLocator;

/**
 * This can be used for field injection into existing objects.
 *
 * The typical use case is command objects passed by the client that get deserialized from JSON. To avoid a big class
 * switch in the API class itself, the command classes contain their own implementation, and they need injected
 * services such as entity repositories for that. Jackson does not inject any services during deserialization, so this
 * post-hoc injector can be used by the API class to inject services before executing the command.
 *
 * Callers could equivalently use the {@link ServiceLocator} directly for injection, but this implies a stronger
 * dependency than the post-hoc injector (and might be less readable for the same reason).
 */
public interface PostHocInjector {

    Holder<PostHocInjector> INSTANCE_HOLDER = new Holder<>();

    /**
     * Injects dependencies into the specified target. Returns the target object to allow chaining.
     */
    <T> T inject(T target);

}
