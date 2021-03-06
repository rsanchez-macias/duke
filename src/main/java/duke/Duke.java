package duke;

import duke.command.Command;
import duke.command.CommandResult;
import duke.task.TaskManager;
import duke.ui.Ui;
import duke.parser.Parser;

/**
 * Entry point to Duke application. Initializes the state of the
 * program and starts running the application.
 */
public class Duke {

    private static Ui printer;

    private static Command command;

    private static TaskManager taskManager;

    private static Parser parser;

    /** Debug mode for text-ui-test */
    public static boolean inDebugMode = false;

    /**
     * Sets the debug mode for the text-ui-test.
     *
     * @param args Flag for debug mode.
     */
    private static void setDebugMode (String[] args) {
        if (args.length >= 1 && args[0].equals("1")) {
            inDebugMode = true;
            return;
        }
    }

    /**
     * Disables warning given by Gson in terminal window
     * From: https://stackoverflow.com/questions/46454995/how-to-hide-warning-illegal-reflective-access-in-java-9-without-jvm-argument
     */
    public static void disableWarning() {
        System.err.close();
        System.setErr(System.out);
    }

    /**
     * Initializes objects to handle the list, to display output, and for
     * command information
     */
    public static void init (String[] args) {

        disableWarning();

        setDebugMode(args);

        taskManager = new TaskManager();
        printer = new Ui();

        parser = new Parser(taskManager);

        taskManager.loadTasks();
    }

    public static void run () {

        printer.greetUser();

        String userResponse;

        while (true) {
            userResponse = parser.getUserInput();

            command = parser.getCommandFromInput(userResponse);
            CommandResult userFeedback = command.execute();

            printer.displayFeedback(userFeedback);
        }

    }

    public static void main (String[] args) {

        init(args);
        run();

    }

}
