package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class TaskManager {
    static String[] options = {"add", "remove", "list", "exit"};
    static String fileName = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        readFromFile();
        printOptions();
        exitProgram();
    }

    public static void printOptions() {
        System.out.println(ConsoleColors.BLUE + "Please select an option:");
        for (String option : options) {
            System.out.println(ConsoleColors.RESET + option);
        }

    }

    public static void readFromFile() {
        Path sourcePath = Paths.get(fileName);
        if (Files.exists(sourcePath)) {
            try {
                List<String> file = Files.readAllLines(sourcePath);
                tasks = new String[3][file.size()];
                for (int i = 0; i < file.size(); i++) {
                    String[] line = file.get(i).split(", ");
                    for (int j = 0; j < line.length; j++) {
                        tasks[i][j] = line[j];
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void exitProgram() {
        Path tasksPath = Paths.get(fileName);
        String[] tasksToSave = new String[tasks.length];
        for (int i = 0; i < tasks.length; i++) {
            tasksToSave[i] = "";
            for (int j = 0; j < tasks[i].length; j++) {
                tasksToSave[i] = tasksToSave[i] + tasks[i][j];
                if (j != tasks[i].length - 1) {
                    tasksToSave[i] = tasksToSave[i] + ", ";
                }
            }
        }
        try {
            Files.write(tasksPath, List.of(tasksToSave));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        System.exit(0);
    }
}