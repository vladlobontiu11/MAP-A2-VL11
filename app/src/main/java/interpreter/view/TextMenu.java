package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            int selectedProgram = -1;
            boolean running = true;
            while (running && scanner.hasNextLine()) {
                printMenu(selectedProgram);
                String choice = scanner.nextLine().trim();
                switch (choice) {
                    case "1" -> selectedProgram = selectProgram(scanner, selectedProgram);
                    case "2" -> runProgram(selectedProgram);
                    case "0" -> running = false;
                    default -> System.out.println("Invalid option. Choose 0, 1, or 2.");
                }
            }
        }
    }

    private void printMenu(int selectedProgram) {
        System.out.println("\nToy-language interpreter");
        System.out.println("1. Select a program");
        System.out.println("2. Run selected program");
        System.out.println("0. Exit");
        if (selectedProgram >= 0) {
            System.out.println("Selected: " + Examples.ALL.get(selectedProgram).name());
        }
        System.out.print("> ");
    }

    private int selectProgram(Scanner scanner, int selectedProgram) {
        for (int i = 0; i < Examples.ALL.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, Examples.ALL.get(i).name());
        }
        System.out.print("Program number (0 to cancel): ");
        if (!scanner.hasNextLine()) {
            return selectedProgram;
        }
        String choice = scanner.nextLine().trim();
        try {
            int programNumber = Integer.parseInt(choice);
            if (programNumber == 0) {
                return selectedProgram;
            }
            if (programNumber < 1 || programNumber > Examples.ALL.size()) {
                System.out.println("Invalid program number.");
                return selectedProgram;
            }
            return programNumber - 1;
        } catch (NumberFormatException exception) {
            System.out.println("Please enter a number.");
            return selectedProgram;
        }
    }

    private void runProgram(int selectedProgram) {
        if (selectedProgram < 0) {
            System.out.println("Select a program first.");
            return;
        }

        PrgState state = new PrgState(new MyStack<>(), new MyDictionary<>(),
                new MyList<>(), Examples.ALL.get(selectedProgram).program());
        try {
            new Controller(new Repository(state), true).allStep();
        } catch (MyException exception) {
            System.out.println("Interpreter error: " + exception.getMessage());
        }
    }
}
