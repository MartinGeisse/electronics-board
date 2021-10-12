package name.martingeisse.electronics_board.backend.platform;

import jakarta.inject.Singleton;
import jakarta.ws.rs.ext.Provider;
import name.martingeisse.electronics_board.backend.application.ApplicationBindings;
import name.martingeisse.electronics_board.backend.application.util.PostHocInjector;
import name.martingeisse.electronics_board.backend.platform.authentication.TokenHolder;
import org.glassfish.hk2.api.PerLookup;
import org.glassfish.hk2.api.PerThread;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.hk2.utilities.binding.ScopedBindingBuilder;
import org.glassfish.hk2.utilities.binding.ServiceBindingBuilder;
import org.glassfish.jersey.process.internal.RequestScoped;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@Provider
public class BinderAdapter extends AbstractBinder {

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {

        ApplicationBindings.Context bindingsContext = new ApplicationBindings.Context() {

            @Override
            public <T> void singleton(Class<T> theClass, Class<? super T>... contracts) {
                scoped(Singleton.class, theClass, contracts);
            }

            @Override
            public <T> void singletonInstance(T object, Class<? super T>... contracts) {
                // The @Singleton annotation gets ignored by Jersey, and .bind without.to doesn't do anything either (despite
                // IntelliJ saying that binding is "redundant". Only bind().to().in(Singleton.class) works.
                ScopedBindingBuilder<T> builder = bind(object);
                for (Class<? super T> contract : contracts) {
                    builder.to(contract);
                }
            }

            @Override
            public <T> void perLookup(Class<T> theClass, Class<? super T>... contracts) {
                scoped(PerLookup.class, theClass, contracts);
            }

            @Override
            public <T> void perThread(Class<T> theClass, Class<? super T>... contracts) {
                scoped(PerThread.class, theClass, contracts);
            }

            @Override
            public <T> void perRequest(Class<T> theClass, Class<? super T>... contracts) {
                scoped(RequestScoped.class, theClass, contracts);
            }

            @Override
            public <T> void scoped(Class<? extends Annotation> scope, Class<T> theClass, Class<? super T>... contracts) {
                scopedWithContractTypes(scope, theClass, null, contracts);
            }

            @Override
            public <T> void scopedWithContractTypes(Class<? extends Annotation> scope,
                                   Class<T> theClass,
                                   Type[] contractTypes,
                                   Class<? super T>... contractClasses) {
                ServiceBindingBuilder<T> builder = bind(theClass);
                builder.to(theClass);
                if (contractTypes != null) {
                    for (Type contract : contractTypes) {
                        builder.to(contract);
                    }
                }
                for (Class<? super T> contract : contractClasses) {
                    builder.to(contract);
                }
                builder.in(scope);
            }

        };

        bindingsContext.perRequest(TokenHolder.class);
        bindingsContext.singleton(PostHocInjectorImpl.class, PostHocInjector.class);

        // The @Singleton annotation gets ignored by Jersey, and .bind without.to doesn't do anything either (despite
        // IntelliJ saying that binding is "redundant". Only bind().to().in(Singleton.class) works.
        new ApplicationBindings().configure(bindingsContext);
    }

}
