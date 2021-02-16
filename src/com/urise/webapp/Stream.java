package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Stream {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 5, 9, 7, 5, 3};
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 7, 9);
        System.out.println(minValue(values));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (x, y) -> 10 * x + y);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        int remainder = integers.stream().reduce(0, Integer::sum) % 2;
        return integers.stream().filter(s -> s % 2 != remainder).collect(Collectors.toList());
    }
}