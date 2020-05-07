package com.codecool.gladiator.view;

import java.util.Scanner;

/**
 * Basic console view implementation
 */
public class ConsoleView implements Viewable {

    @Override
    public void display(String text) {

        System.out.println(text);

    }

    @Override
    public int getNumberBetween(int min, int max) {
        int number;
        do {
            Scanner input = new Scanner(System.in);

            number = input.nextInt();
        } while (number < min || number > max);

        return number;
    }

}
