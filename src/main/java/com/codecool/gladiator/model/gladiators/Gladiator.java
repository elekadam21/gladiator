package com.codecool.gladiator.model.gladiators;

public abstract class Gladiator {

    private final String name;
    private int baseHp;
    private int baseSp;
    private int baseDex;
    private int level;

    /**
     * Constructor for Gladiators
     *
     * @param name the gladiator's name
     * @param baseHp the gladiator's base Health Points
     * @param baseSp the gladiator's base Strength Points
     * @param baseDex the gladiator's base Dexterity Points
     * @param level the gladiator's starting Level
     */
    public Gladiator(String name, int defaultHp, int defaultSp, int defaultDex, int level) {
        this.name = name;
        this.level = level;
        this.baseHp = (int) (defaultHp * getHpMultiplier().getValue() * level);
        this.baseSp = (int) (defaultSp * getSpMultiplier().getValue() * level);
        this.baseDex = (int) (defaultDex * getDexMultiplier().getValue() * level);
    }

    /**
     * @return HP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getHpMultiplier();

    /**
     * @return SP multiplier of the gladiator subclass
     */
    protected abstract Multiplier getSpMultiplier();

    /**
     * @return DEX multiplier of the gladiator subclass
     */
    protected abstract Multiplier getDexMultiplier();


    public int getBaseHp() {
        return baseHp;
    }


    public int getBaseSp() {
        return baseSp;
    }


    public int getBaseDex() {
        return baseDex;
    }

    public int getLevel() {
        return level;
    }

    public void changeLevel(int num) {
        this.level += num;
    }

    public void changeHp() {
        this.baseHp = this.baseHp / (this.level - 1) * level;
    }

    public void changeSp() {
        this.baseSp = this.baseSp / (this.level - 1) * level;
    }

    public void changeDex() {
        this.baseDex = this.baseDex / (this.level - 1) * level;
    }

    public void levelUp() {
        changeLevel(1);
        changeHp();
        changeSp();
        changeDex();
    }

    protected abstract String getType();

    /**
     * @return Gladiator's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the full name of the gladiator
     * assembled by the subtype and the name
     * (e.g. "Brutal Brutus" or "Archer Leo")
     *
     * @return the full name
     */
    public String getFullName() {
        return getType() + " " + name;
    }

    public enum Multiplier {
        Low(0.75),
        Medium(1.0),
        High(1.25);

        private final double value;

        Multiplier(double value) {
            this.value = value;
        }

        public double getValue() {
            return value;
        }
    }

}
