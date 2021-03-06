package ru.ifmo.lang.evtushenko.t06;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SimpleGrepUtility implements Grep {
    private BufferedReader bf;

    private SimpleGrepUtility(InputStream stream) {
         bf = new BufferedReader(new InputStreamReader(stream));
    }

    public static void main(final String[] args) {
        try {
            if (args.length == 2) {
                Grep grep = new SimpleGrepUtility(new BufferedInputStream(new FileInputStream(args[1])));
                System.out.println(grep.findLines(args[0]));
            } else {
                Grep grep = new SimpleGrepUtility(new BufferedInputStream(new FileInputStream(args[2])));
                if (args[0].equals("-o")) {
                    System.out.println(grep.findParts(args[1]));
                } else if (args[0].equals("-v")) {
                    System.out.println(grep.findInvertMatch(args[1]));
                } else {
                    System.out.println("Неизвестная команда");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> findLines(String regex) {
        String str;
        List<String> lines = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        try {
            while ((str = bf.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                if (matcher.find()) {
                    lines.add(str);
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public List<String> findParts(String regex) {
        String str;
        List<String> lines = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        try {
            while ((str = bf.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                while (matcher.find()) {
                    lines.add(matcher.group());
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public List<String> findInvertMatch(String regex) {
        String str;
        List<String> lines = new ArrayList<String>();
        Pattern pattern = Pattern.compile(regex);
        try {
            while ((str = bf.readLine()) != null) {
                Matcher matcher = pattern.matcher(str);
                if (!matcher.find()) {
                    lines.add(str);
                }
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
