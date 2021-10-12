import {mapMap, mapMapToUnsortedArray} from "./mapMap";

it("mapMap", () => {
    const inputMap = {foo: "bar", baz: 5};
    const collector: any = {};
    const result = mapMap(inputMap, (value, key, map) => {
        collector[key] = value;
        expect(map).toBe(inputMap);
        return "*" + value + "*";
    });
    expect(collector).toStrictEqual(inputMap);
    expect(result).toStrictEqual({
        foo: "*bar*", baz: "*5*"
    });
});


it("mapMapToUnsortedArray", () => {
    const inputMap = {foo: "bar", baz: 5};
    const collector: any = {};
    const result = mapMapToUnsortedArray(inputMap, (value, key, map) => {
        collector[key] = value;
        expect(map).toBe(inputMap);
        return "*" + value + "*";
    });
    expect(collector).toStrictEqual(inputMap);
    result.sort();
    expect(result).toStrictEqual(["*5*", "*bar*"]);
});
