package todolist;

import todolist.service.TaskServiceImpl;

public class ListApp {
    public static void main(String[] args) {
        ToDoList todolistMenu = new ToDoList(new TaskServiceImpl());
        todolistMenu.menu();
    }
}
