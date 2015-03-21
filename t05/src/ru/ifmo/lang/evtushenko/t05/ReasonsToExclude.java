package ru.ifmo.lang.evtushenko.t05;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ReasonsToExclude {
    static List<String> reasonsToExclude = new ArrayList<String>();

    static {
        reasonsToExclude.add("написано, что он сидит на Windows. Такие люди должны страдать, отчисление!");
        reasonsToExclude.add("имеется пункт об опыте работы в Git. С графическим интерфейсом. Отчислен.");
        reasonsToExclude.add("имеется пометка об удалении ценного файла на компьютере преподавателя вне указанной папки — немедленное отчисление");
        reasonsToExclude.add("в графе желаемого будущего места работы указано Mail.ru, ему уже нечем помочь - отчислен!");
        reasonsToExclude.add("совсем ничего нет. Это ли не причина к отчислению?");
        reasonsToExclude.add("уже первый пункт не соответствует стандартам оформления. Нет смысла читать дальше. Отчислен!");
        reasonsToExclude.add("указан опыт работы в McDonald's... Спасаю человека от ноши промышленной разработки, отчислен с благими намерениями!");
        reasonsToExclude.add("есть ссылка на репозиторий с собственным проектом. Все коммиты в главной ветке и не прокомментированы. Без вопросов, отчислен!");
        reasonsToExclude.add("не убедителен ответ на вопрос \"Почему меня не должны отчислить\". Отчислен");
    }

    public static String getReason() {
        Random random = new Random();
        int chosenReason = random.nextInt(reasonsToExclude.size());
        return reasonsToExclude.get(chosenReason);
    }
}
