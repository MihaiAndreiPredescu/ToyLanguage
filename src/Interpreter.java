/*
import controller.Controller;
import repository.IRepository;
import repository.Repository;
import view.TextMenu;
import view.commands.ExitCommand;
import view.commands.GetPrgCommand;
import view.commands.RunExampleCommand;

public class Interpreter {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        IRepository repository = new Repository("src/log.out");
        Controller controller = new Controller(repository);

        TextMenu menu = new TextMenu();

        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new GetPrgCommand("1", "get predefined program(1,2,3,4)", controller));
        menu.addCommand(new RunExampleCommand("2", "run program", controller));

        menu.show();
    }
}*/
