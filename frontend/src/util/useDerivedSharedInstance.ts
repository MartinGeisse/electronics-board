import {useSharedInstance} from "./useSharedInstance";

type State<K, T> = {
    key: K;
    instance: T;
}

export function useDerivedSharedInstance<K, T>(key: K, factory: () => T) {
    const state = useSharedInstance<State<K, T>>(() => ({key, instance: factory()}));
    if (state.key !== key) {
        state.key = key;
        state.instance = factory();
    }
    return state.instance;
}
