package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Stream {
    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 3, 3, 9};
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(minValue(values));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        int[] newValues = Arrays.stream(values).sorted().distinct().toArray();
        return IntStream.range(0, newValues.length)
                .map(i -> (int) (Math.pow(10, newValues.length - 1 - i) * newValues[i])).sum();
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(integers.stream().mapToInt(Integer::intValue)
                .sum() % 2 == 0 ? s -> s % 2 != 0 : s -> s % 2 == 0)
                .collect(Collectors.toList());
    }
}