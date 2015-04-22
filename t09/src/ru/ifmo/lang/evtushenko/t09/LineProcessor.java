package ru.ifmo.lang.evtushenko.t09;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LineProcessor {

    public static void main(String[] args) throws IOException {
        String str;
        PrintWriter out = new PrintWriter(args[1]);
        Path input = Paths.get(args[0]);
        List<String> listOfStrings = Files.readAllLines(input);

        Integer iterator = 2;
        Stream<String> stream = listOfStrings.stream();
        while (iterator < args.length) {
            switch (args[iterator]) {
                case "sort":
                    stream = stream.sorted();
                    iterator++;
                    break;
                case "distinct":
                    stream = stream.distinct();
                    iterator++;
                    break;
                case "skip":
                    iterator++;
                    stream = stream.skip(Integer.parseInt(args[iterator]));
                    iterator++;
                    break;
                case "limit":
                    iterator++;
                    stream = stream.limit(Integer.parseInt(args[iterator]));
                    iterator++;
                    break;
                case "shuffle":
                    listOfStrings = stream.collect(Collectors.toList());
                    Collections.shuffle(listOfStrings);
                    stream = listOfStrings.stream();
                    iterator++;
                    break;
                case "filter":
                    iterator++;
                    Pattern pattern = Pattern.compile(args[iterator]);
                    stream = stream.filter(i -> pattern.matcher(i).matches());
                    iterator++;
                    break;
            }
        }
        Files.write(Paths.get(args[1]), stream.collect(Collectors.toList()));
    }
}
