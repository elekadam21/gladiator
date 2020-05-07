package com.codecool.gladiator.model.gladiators;

import com.codecool.gladiator.util.RandomUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Random;

public class GladiatorFactory {

    private List<String> names;
    private int archerChance = 1;
    private int assassinChance = 1;
    private int brutalChance = 1;
    private int swordsmanChance = 2;

    public GladiatorFactory(String fileOfNames) {
        try {
            File file = new File(getClass().getClassLoader().getResource(fileOfNames).getFile());
            names = Files.readAllLines(file.toPath());
        } catch (IOException|NullPointerException e) {
            System.out.println("Names file not found or corrupted!");
            System.exit(1);
        }
    }

    /**
     * Picks a random name from the file given in the constructor
     *
     * @return gladiator name
     */
    private String getRandomName() {
        Random random = new Random();
        return names.get(random.nextInt(names.size()));
    }

    /**
     * Instantiates a new gladiator with random name and type.
     * Creating an Archer, an Assassin, or a Brutal has the same chance,
     * while the chance of creating a Swordsman is the double of the chance of creating an Archer.
     * @return new Gladiator
     */
    public Gladiator generateRandomGladiator() {
        int MIN_STAT = 25;
        int MAX_STAT = 100;
        int MIN_LEVEL = 1;
        int MAX_LEVEL = 5;

        RandomUtils random = new RandomUtils();

        int randomHp = random.nextInt(MAX_STAT - MIN_STAT) + MIN_STAT;
        int randomSp = random.nextInt(MAX_STAT - MIN_STAT) + MIN_STAT;
        int randomDex = random.nextInt(MAX_STAT - MIN_STAT) + MIN_STAT;
        int randomLevel = random.nextInt(MAX_LEVEL - MIN_LEVEL) + MIN_LEVEL;


        int randomNumber = random.nextInt(archerChance + assassinChance + brutalChance + swordsmanChance) + 1;
        if (randomNumber <= archerChance) {
            return new Archer(getRandomName(), randomHp, randomSp, randomDex, randomLevel);

        } else if (randomNumber <= archerChance + assassinChance) {
            return new Assassin(getRandomName(), randomHp, randomSp, randomDex, randomLevel);

        } else if (randomNumber <= archerChance + assassinChance + brutalChance) {
            return new Brutal(getRandomName(), randomHp, randomSp, randomDex, randomLevel);
        } else {
            return new Swordsman(getRandomName(), randomHp, randomSp, randomDex, randomLevel);
        }

    }
}
