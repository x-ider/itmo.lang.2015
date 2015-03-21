package ru.ifmo.lang.evtushenko.t05;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GunCommon implements RussianRoulette.Gun {

    String bullets;
    String pathToDir;
    List<Path> pathList = new ArrayList<Path>();

    public GunCommon(String pathToDir, String bullets) {
        this.bullets = bullets;
        this.pathToDir = pathToDir;
    }

    public boolean fire() {
        System.out.println("Пуль вставлено в барабан подряд: " + bullets);
        Random random = new Random();
        int numberOfBullets = Integer.parseInt(bullets);
        int bullet = random.nextInt(6) + 1;
        System.out.println("Вращаем барабан... камора №" + bullet + " напротив ствола!");
        FileVisitor visitor = new MyVisitor();
        try {
            Files.walkFileTree(Paths.get(pathToDir), visitor);
            if (pathList.size() == 0) {
                System.out.println("...\nДанные на кон! Здесь на интерес не играют");
            } else {
                int chosenFile = random.nextInt(pathList.size());
                if (bullet <= numberOfBullets) {
                    System.out.println("Файл \"" + pathList.get(chosenFile) + "\" удалён! Такова воля Рандома!!!");
                    Files.delete(pathList.get(chosenFile));
                } else {
                    System.out.println("Файл \"" + pathList.get(chosenFile) + "\" остался нетронутым по воле Рандома!!!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public class MyVisitor extends SimpleFileVisitor<Path> {
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            pathList.add(file);
            return FileVisitResult.CONTINUE;
        }
    }
}
