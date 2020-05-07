package com.codecool.gladiator.model;

import com.codecool.gladiator.model.gladiators.Gladiator;
import com.codecool.gladiator.util.RandomUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Combat class, used for simulating fights between pairs of gladiators
 */
public class Combat {

    private final Gladiator gladiator1;
    private final Gladiator gladiator2;

    private final List<String> combatLog = new ArrayList<>();

    public Combat(Gladiator gladiator1, Gladiator gladiator2) {
        this.gladiator1 = gladiator1;
        this.gladiator2 = gladiator2;
    }

    /**
     * Simulates the combat and returns the winner.
     * If one of the opponents is null, the winner is the one that is not null
     * If both of the opponents are null, the return value is null
     *
     * @return winner of combat
     */
    public Gladiator simulate() {
        RandomUtils random = new RandomUtils();
        int getFirstPlayer = random.nextInt(2);
        boolean glad1Turn = false;
        if (getFirstPlayer == 0) {
            glad1Turn = true;
        }
        int gladiator1Hp = gladiator1.getBaseHp();
        int gladiator2Hp = gladiator2.getBaseHp();
        while (gladiator1Hp > 0 && gladiator2Hp > 0) {
            if (glad1Turn) {
                gladiator2Hp = attack(gladiator1, gladiator2, gladiator2Hp);
                glad1Turn = false;
            } else {
                gladiator1Hp = attack(gladiator2, gladiator1, gladiator1Hp);
                glad1Turn = true;
            }
            
        }
        if (gladiator1Hp <= 0) {
            return gladiator2;
        }
        return gladiator1;
    }

    public int attack(Gladiator attacker, Gladiator defender, int defHp) {
        RandomUtils random = new RandomUtils();
        int chanceToHit = attacker.getBaseDex() - defender.getBaseDex();
        if (chanceToHit > 100) {
            chanceToHit = 100;
        } else if (chanceToHit < 10) {
            chanceToHit = 10;
        }

        int randomNum = random.nextInt(100);
        if (randomNum < 100 - chanceToHit) {
            combatLog.add(attacker.getFullName() + " misses");
        } else {
            int MInt = random.nextInt(4) + 1;
            double M = MInt / 10.0;
            int damage = (int) Math.round(attacker.getBaseSp() * M);
            combatLog.add(attacker.getFullName() + " deals " + damage + " damage, " + defender.getFullName() + " has " + (defHp - damage) + " remaining health");
            return defHp - damage;
        }
        return defHp;
    }

    public Gladiator getGladiator1() {
        return gladiator1;
    }

    public Gladiator getGladiator2() {
        return gladiator2;
    }

    public String getCombatLog(String separator) {
        return String.join(separator, combatLog);
    }

}
