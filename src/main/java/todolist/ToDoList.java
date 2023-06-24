package todolist;

import todolist.domain.Task;
import todolist.service.TaskService;
import todolist.service.TaskServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;



public class ToDoList {
    private final TaskService taskService;

    public ToDoList(TaskService taskService) {
        this.taskService = taskService;
    }

    public void menu() {
        System.out.println("Вас вітає електронний щоденник 'My Daily Life'" + "\n" + "Введіть будь ласка своє ім'я: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println(name);
        System.out.println("Вітаємо, " + name + "! "+"\n");
        int menu1 = 1;
        while (menu1 != 0) {
            String text = """ 
                     \n Оберіть команду:
                     1.Створити завдання
                     2.Редагувати дату
                     3.Редагувати статус завдання
                     4.Оновити завдання
                     5.Видалити завдання
                     6.Показати список завдань
                     7.Зберегти в файл
                     8.Завантажити із файла
                     9.Допомога
                     10.Пошук
                    """;
            System.out.println(text);
            String action = in.nextLine();
            switch (action) {
                case "1" -> create();
                case "2" -> updateDate();
                case "3" -> updateStatus();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> readWithSorting();
                case "7" -> save();
                case "8" -> load();
                case "9" -> help();
                case "10" -> search();
                case "0" -> menu1 = 0;
                default -> System.out.println("Такої команди не існує. Оберіть будь ласка іншу команду \n");
            }

        }
    }

    private void save() {
        try {
            taskService.save();
        } catch (IOException e) {
            System.err.println("Помилка збереження в файл:" + e.getMessage());
        }
    }

    private void load() {
        try {
            taskService.load();
        } catch (IOException e) {
            System.err.println("Помилка завантаження із файла:" + e.getMessage());
        }
    }


    private void create() {
        Scanner in = new Scanner(System.in);
        System.out.println("1.Створити завдання ");
        String description = in.nextLine();
        System.out.print("Введіть дату: ");
        String str = in.nextLine();
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate dueDate = LocalDate.parse(str, date);
            Task task = taskService.create(description, dueDate);
            System.out.println("Ви додали нове завдання." +"\n" + "Номер: " + task.getId());
        } catch (DateTimeParseException e) {
            System.err.println("Введено невірну дату!");
        }
    }


    private void update() {
        read();
        Scanner in = new Scanner(System.in);
        System.out.println("Оберіть номер завдання, яке бажаєте редагувати. ");
        int id = in.nextInt();
        System.out.println("Введіть задачу");
        String description = in.nextLine();
        taskService.update(id, description);
        System.out.println("Завдання відредаговано ");
    }

    private void updateDate() {
        read();
        Scanner in = new Scanner(System.in);
        System.out.println("Оберіть номер завдання, в якому бажаєте редагувати дату. ");
        int id = in.nextInt();
        System.out.print("Введіть дату: ");
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = in.next();
        try {
            LocalDate dueDate = LocalDate.parse(str, date);
            taskService.updateDate(id, dueDate);
        } catch (DateTimeParseException e) {
            System.err.println("Введено невірну дату!");
        }
        System.out.println("Дата відредагована ");
    }

    private void updateStatus() {
        read();
        Scanner in = new Scanner(System.in);
        System.out.println("Оберіть номер завдання, в якому бажаєте редагувати статус. ");
        int id = in.nextInt();
        System.out.println("Відредагуйте статус: ");
        String statusText = in.next();
        boolean status = statusText.equalsIgnoreCase("Виконано") || statusText.equalsIgnoreCase("done");
        taskService.updateStatus(id, status);
        System.out.println("Статус відредаговано ");
    }

    private void delete() {
        read();
        Scanner in = new Scanner(System.in);
        System.out.println("Оберіть завдання, яке бажаєте видалити. ");
        int id = in.nextInt();
        taskService.delete(id);
        System.out.println("Завдання видалено ");
    }


    private void read() {
        List<Task> taskList = taskService.getTaskList(Comparator.comparing(Task::getId));
        printTaskList(taskList);
    }

    private void readWithSorting() {
        Scanner in = new Scanner(System.in);
        String text = """
                За чим ви бажаєте сортувати завдання?
                1. За датою
                2. За статусом
                """;
        System.out.println(text);
        String action = in.nextLine();
        List<Task> taskList;

        switch (action) {
            case "1" -> taskList = taskService.getTaskList(Comparator.comparing(Task::getDueDate));
            case "2" -> taskList = taskService.getTaskList(Comparator.comparing(Task::isDone));
            default -> {
                System.out.println("Неправильно введена цифра.");
                return;
            }
        }

         printTaskList(taskList);

    }

     private static void printTaskList(List<Task> taskList) {
        for (Task task : taskList) {
            System.out.println("************");
            System.out.println("Задача: " + task.getDescription());
            System.out.println("Номер: " + task.getId());
            System.out.println("Дата: " + task.getDueDate());
            System.out.println("Статус: " + (task.isDone() ? "Виконано" : "Не виконано"));

        }
    }

    private void help() {
            System.out.println("Для отриманя інформації користуйтесь інтерактивним меню. ");

}
    private void search(){
        System.out.println("Введіть дату, за якою хочете зробити пошук:" );
        Scanner in = new Scanner(System.in);
        DateTimeFormatter date = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String str = in.nextLine();

        try {
            LocalDate dueDate = LocalDate.parse(str, date);
            List<Task> taskList = taskService.search(dueDate);
            if (taskList.isEmpty()) {
                System.out.println("Нічого не знайдено.");
            } else {
                printTaskList(taskList);
            }
        } catch (DateTimeParseException e) {
            System.err.println("Введено невірну дату!");
        }
     }
}