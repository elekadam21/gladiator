package com.codecool.gladiator.controller;

import com.codecool.gladiator.model.Combat;
import com.codecool.gladiator.model.gladiators.*;
import com.codecool.gladiator.util.BinaryTree;
import com.codecool.gladiator.view.Viewable;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    public static final int MIN_TOURNAMENT_STAGES = 1;
    public static final int MAX_TOURNAMENT_STAGES = 10;

    private final Viewable view;
    private final GladiatorFactory gladiatorFactory;
    private int stages = 2;

    public Tournament(Viewable view, GladiatorFactory gladiatorFactory) {
        this.view = view;
        this.gladiatorFactory = gladiatorFactory;
    }

    /**
     * Runs the Tournament simulation
     */
    public void runSimulation() {
        var numberOfGladiators = (int) Math.pow(2, stages);
        var gladiators = generateGladiators(numberOfGladiators);
        var tournamentTree = new BinaryTree<>(gladiators);
        var champion = getChampion(tournamentTree);
        announceChampion(champion);

        // The following line chains the above lines:
        // announceChampion(getChampion(new BinaryTree<>(generateGladiators((int) Math.pow(2, stages)))));
    }

    private List<Gladiator> generateGladiators(int numberOfGladiators) {
        List<Gladiator> gladiators = new ArrayList<>();
        for (int i = 0; i < numberOfGladiators; i++) {
            gladiators.add(gladiatorFactory.generateRandomGladiator());
        }
        introduceGladiators(gladiators);
        return gladiators;
    }

    private Gladiator getChampion(BinaryTree<Gladiator> tournamentTree) {
        int sizeOfBranch = 2;
        int exponent = 2;
        while (tournamentTree.getValue() == null) {
            view.display("\nROUND " + (exponent - 1));
            getGladiators(tournamentTree, sizeOfBranch);
            sizeOfBranch = (int) Math.pow(2, exponent);
            exponent += 1;
        }

        return tournamentTree.getValue();
    }

    private void getGladiators(BinaryTree<Gladiator> branch, int sizeOfBranch) {
        if (branch.getSize() == sizeOfBranch) {
            Combat combat = new Combat(branch.getLeftBranch().getValue(), branch.getRightBranch().getValue());
            Gladiator winner = simulateCombat(combat);
            winner.levelUp();
            branch.add(winner);
        } else {
            getGladiators(branch.getLeftBranch(), sizeOfBranch);
            getGladiators(branch.getRightBranch(), sizeOfBranch);
        }
    }

    private Gladiator simulateCombat(Combat combat) {
        Gladiator gladiator1 = combat.getGladiator1();
        Gladiator gladiator2 = combat.getGladiator2();

        announceCombat(gladiator1, gladiator2);

        Gladiator winner = combat.simulate();

        displayCombatLog(combat);
        if (winner == gladiator1) {
            announceWinnerAndLoser(gladiator1, gladiator2);
        } else {
            announceWinnerAndLoser(gladiator2, gladiator1);
        }
        return winner;
    }

    public void welcome() {
        view.display("Ave Caesar, and welcome to the Colosseum!");
    }

    public void welcomeAndAskForStages() {
        welcome();
        view.display("How many stages of the Tournament do you wish to watch? (1-10)");
        stages = view.getNumberBetween(MIN_TOURNAMENT_STAGES, MAX_TOURNAMENT_STAGES);
    }

    private void introduceGladiators(List<Gladiator> gladiators) {
        view.display(String.format("\nWe have selected Rome's %d finest warriors for today's Tournament!", gladiators.size()));
        for (Gladiator gladiator: gladiators) {
            view.display(String.format(" - %s", gladiator.getFullName()));
        }
        view.display("\n\"Ave Imperator, morituri te salutant!\"");
    }

    private void announceCombat(Gladiator gladiator1, Gladiator gladiator2) {
        view.display(String.format("\nDuel %s versus %s:", gladiator1.getName(), gladiator2.getName()));
        view.display(String.format(" - %s, HP - %s, SP - %s, Dex - %s, Level - %s",
                gladiator1.getFullName(), gladiator1.getBaseHp(), gladiator1.getBaseSp(), gladiator1.getBaseDex(), gladiator1.getLevel()));
        view.display(String.format(" - %s, HP - %s, SP - %s, Dex - %s, Level - %s",
                gladiator2.getFullName(), gladiator2.getBaseHp(), gladiator2.getBaseSp(), gladiator2.getBaseDex(), gladiator2.getLevel()));
    }

    private void displayCombatLog(Combat combat) {
        view.display(String.format(" - %s", combat.getCombatLog(", ")));
    }

    private void announceWinnerAndLoser(Gladiator winner, Gladiator loser) {
        view.display(String.format("%s has died, %s wins!", loser.getFullName(), winner.getFullName()));
    }

    private void announceChampion(Gladiator champion) {
        if (champion != null) {
            view.display(String.format("\nThe Champion of the Tournament is %s!", champion.getFullName()));
        } else {
            view.display("\nHave mercy, Caesar, the Tournament will start soon!");
        }
    }

}
