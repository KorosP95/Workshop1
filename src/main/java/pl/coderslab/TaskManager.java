package pl.coderslab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class TaskManager {
    static String[] options = {"add", "remove", "list", "exit"};
    static String fileName = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        readFromFile();
        selectedOptions();
        addTask();
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
        if (!Files.exists(sourcePath)) {
            System.out.println("File not exist!");
            System.exit(0);
        }
        if (Files.exists(sourcePath)) {
            try {
                List<String> file = Files.readAllLines(sourcePath);
                tasks = new String[file.size()][3];
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
    public static void selectedOptions() {
        Scanner scan = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            printOptions();
            input = scan.nextLine();
            switch (input) {
                case "add" -> addTask();
                case "remove" -> {
                    removeTask(tasks, isANumber());
                }
                case "list" -> listTasks(tasks);
                case "exit" -> exitProgram();
                default -> System.out.println("Please select a correct option.");
            }
        }
    }
    public static void addTask() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description");
        String taskDescription = scan.nextLine();
        System.out.println("Please add task due date");
        String taskDueDate = scan.nextLine();
        System.out.println("Is your task is important: true/false");
        String taskIsImportant = scan.next();

        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = taskDescription;
        tasks[tasks.length - 1][1] = taskDueDate;
        tasks[tasks.length - 1][2] = taskIsImportant;
    }

    public static void listTasks(String[][] tab) {
        for (int i = 0; i < tab.length; i++) {
                System.out.print(i + " : ");
            for (int j = 0; j < tab[i].length; j++) {
                System.out.print(tab[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void removeTask(String[][] tab, int index) {
        try {
            if (index < tab.length) {
                tasks = ArrayUtils.remove(tab, index);
                System.out.println("Value was successfully deleted.");
            } else {
                System.out.println("Table has no such element");
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Table has no such element");
        }
    }
    public static int isANumber() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select number to remove.");
        String nb = scan.nextLine();
        while (!nbIsGraterOrEqual0(nb)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
            nb = scan.nextLine();
        }
        return Integer.parseInt(nb);
    }
    public static boolean nbIsGraterOrEqual0(String input) {

        if(NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
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