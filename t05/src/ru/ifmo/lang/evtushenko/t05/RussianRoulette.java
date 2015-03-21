package ru.ifmo.lang.evtushenko.t05;

/**
 * Игра в русскую рулетку
 */
public interface RussianRoulette {

    /**
     * Начать игру (зарядить пистолет, выбрать цель, нажать на крючок)
     *
     * @param gun Пистолет, которым будем играть
     */
    void play(Gun gun);

    /**
     * Пистолет для игры в русскую рулетку
     */
    interface Gun {
        /**
         * Выстрелить из пистолета
         *
         * @return {@code true} - если пистолет выстрелил, иначе {@code false}
         */
        boolean fire();
    }

}