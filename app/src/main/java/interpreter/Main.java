package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.repository.Repository;
import interpreter.view.Command;
import interpreter.view.Examples;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

public class Main {
    public static void main(String[] args) {
        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i = 0; i < Examples.ALL.size(); i++) {
            Examples.Example example = Examples.ALL.get(i);
            String logFileName = "log" + (i + 1) + ".txt";

            PrgState state = PrgState.of(example.getProgram());
            Repository repository = new Repository(state, logFileName);
            Controller controller = new Controller(repository, true);

            Command command = new RunExampleCommand(
                    Integer.toString(i + 1),
                    example.getName(),
                    controller);
            menu.addCommand(command);
        }

        menu.show();
    }
}
