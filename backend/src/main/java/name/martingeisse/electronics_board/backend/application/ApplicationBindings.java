package name.martingeisse.electronics_board.backend.application;

import name.martingeisse.electronics_board.backend.application.logic.simulator.EditorSimulatorBridge;
import name.martingeisse.electronics_board.backend.application.logic.simulator.Simulator;
import name.martingeisse.electronics_board.backend.application.technical.event_store.EventStore;
import name.martingeisse.electronics_board.backend.application.technical.event_store.MemoryEventStore;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class ApplicationBindings {

    public interface Context {
        <T> void singleton(Class<T> theClass, Class<? super T>... contracts);
        <T> void singletonInstance(T object, Class<? super T>... contracts);
        <T> void perLookup(Class<T> theClass, Class<? super T>... contracts);
        <T> void perThread(Class<T> theClass, Class<? super T>... contracts);
        <T> void perRequest(Class<T> theClass, Class<? super T>... contracts);
        <T> void scoped(Class<? extends Annotation> scope, Class<T> theClass, Class<? super T>... contracts);

        // Has a special name to avoid passing a non-compatible type to this method when intending to call scoped()
        // with classes only but passing an incompatible one, selecting the wrong method.
        <T> void scopedWithContractTypes(Class<? extends Annotation> scope, Class<T> theClass,
                        Type[] contractTypes, Class<? super T>... contractClasses);
    }

    public void configure(Context context) {
        context.singleton(MemoryEventStore.class, EventStore.class);

        context.singleton(EditorSimulatorBridge.class);
        context.singleton(Simulator.class);
    }

}
