package todolist.presentation;

import todolist.domain.service.TaskServiceImpl;

public class ListApp {
    public static void main(String[] args) {
        ToDoList todolistMenu = new ToDoList(new TaskServiceImpl());
        todolistMenu.menu();
    }
}
