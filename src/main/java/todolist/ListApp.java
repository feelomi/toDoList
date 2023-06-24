package todolist;

import todolist.service.TaskService;

public class ListApp {
    public static void main(String[] args) {
        ToDoList todolistMenu = new ToDoList(new TaskService());
        todolistMenu.menu();
    }
}
