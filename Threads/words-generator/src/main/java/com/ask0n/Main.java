package com.ask0n;

import java.util.Arrays;
import java.util.Comparator;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Main {
    private static final String SENTENCE = "Lorem ipsum dolor sit amet, consectetur adipiscing elit." +
            " Sed sit amet enim mauris. Nullam quis feugiat massa. Nullam at ullamcorper risus. " +
            "Donec quis semper velit, in interdum justo. " +
            "Quisque leo nunc, volutpat sed tincidunt vitae, interdum nec leo. " +
            "Phasellus neque neque, vulputate at felis a, tincidunt dapibus diam. " +
            "Mauris feugiat nec neque vitae tincidunt. Fusce ac viverra ligula, sed sagittis elit. " +
            "Nullam quam mauris, placerat a fermentum ut, ultricies in metus. Donec consequat nisl sollicitudin," +
            " euismod ex eget, efficitur massa.";

    public static void main(String[] args) {
        var words = Arrays.asList(
                SENTENCE.replaceAll("[,]|[.]|[?]|[!]", "").split(" "));

        words.stream()
                .map(String::toLowerCase)
                .collect(groupingBy(identity(), counting()))
                .keySet().stream()
                .sorted(Comparator.comparing(x -> x))
                .forEach(System.out::println);
    }
}
