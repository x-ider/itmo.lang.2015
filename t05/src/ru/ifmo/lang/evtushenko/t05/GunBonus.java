package ru.ifmo.lang.evtushenko.t05;

import org.apache.commons.io.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GunBonus implements RussianRoulette.Gun {

    final String pathToDir;
    List<Path> pathList = new ArrayList<Path>();

    public GunBonus(String pathToDir) {
        this.pathToDir = pathToDir;
    }

    public boolean fire() {

        final String url = "http://api.vk.com/method/users.get?user_id=39592&v=5.29&fields=online";
        String text = null;
        try {
            text = IOUtils.toString(new URL(url));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (text.contains("online\":1")) {
            System.out.println("Охотник почуял свежее мясо...");
            try {
                FileVisitor visitor = new MyVisitor();
                Files.walkFileTree(Paths.get(pathToDir), visitor);
                if (pathList.size() == 0) {
                    System.out.println("Чутьё впервые подвело, здесь уже некого отчислять!");
                } else {
                    Random random = new Random();
                    int amountOfStudents = random.nextInt(pathList.size()) + 1;
                    for (int i = 0; i < amountOfStudents; i++) {
                        int chosenStudent = random.nextInt(pathList.size());
                        System.out.println("- В профайле студента " + pathList.get(chosenStudent).getFileName() + " " + ReasonsToExclude.getReason());
                        Files.delete(pathList.get(chosenStudent));
                        pathList.remove(pathList.get(chosenStudent));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Сегодня студенты могут спать спокойно...");
        }
        return true;
    }

    public class MyVisitor extends SimpleFileVisitor<Path> {
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (!Files.exists(Paths.get(file.getParent().toString() + File.separator + "Student " + pathList.size()))) {
                Files.move(Paths.get(file.toString()), Paths.get(file.getParent().toString() + File.separator + "Student " + pathList.size()));
            }
            pathList.add(Paths.get(file.getParent().toString() + File.separator + "Student " + pathList.size()));
            return FileVisitResult.CONTINUE;
        }
    }


}
