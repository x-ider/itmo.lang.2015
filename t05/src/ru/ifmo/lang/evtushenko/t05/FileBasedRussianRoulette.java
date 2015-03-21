package ru.ifmo.lang.evtushenko.t05;


public class FileBasedRussianRoulette implements RussianRoulette {

    public static void main(String[] args) {
        RussianRoulette russianRoulette = new FileBasedRussianRoulette();
        if (args[1].equals("bonus")) {
            Gun gun = new GunBonus(args[0]);
            russianRoulette.play(gun);
        } else {
            Gun gun = new GunCommon(args[0], args[1]);
            russianRoulette.play(gun);
        }
    }

    public void play(RussianRoulette.Gun gun) {
        gun.fire();
    }
}