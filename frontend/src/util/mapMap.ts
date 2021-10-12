
export function mapMap<K extends keyof any,V, W>(
    map: {[key in K]: V},
    mapper: (value: V, key: K, map: {[key in K]: V}) => W
): {[key in K]: W} {
    const result: {[key in K]: W} = {} as any;
    for (var key in map) {
        result[key] = mapper(map[key], key, map);
    }
    return result;
}

export function mapMapToUnsortedArray<K extends keyof any,V, W>(
    map: {[key in K]: V},
    mapper: (value: V, key: K, map: {[key in K]: V}) => W
): W[] {
    const result: W[] = [];
    for (var key in map) {
        result.push(mapper(map[key], key, map));
    }
    return result;
}
