package todolist.service;

import todolist.domain.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;


public class TaskServiceImpl implements TaskService {
    private final static String JSON_FILE = "saved-todo.json";

    private final FileService fileService = new FileService(JSON_FILE);
    private final JsonSerializer serializer = new JsonSerializer();
    private final List<Task> taskList = new ArrayList<>();
    private int idSequence = 0;

    @Override
    public Task create(String description, LocalDate dueDate) {
        Task newTask = new Task( ++idSequence, description, dueDate);

        //TODO save new task
        taskList.add(newTask);
        return newTask;
    }

    @Override
    public void delete(int id){
        taskList.removeIf(task -> task.getId() == id);
    }


    @Override
    public void update(int id, String description) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDescription(description);
            }
        }
    }

    @Override
    public void updateStatus(int id, boolean newStatus) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDone(newStatus);
            }
        }
    }

    @Override
    public void updateDate(int id, LocalDate newDate) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDueDate(newDate);
            }
        }
    }

    @Override
    public List<Task> getTaskList(Comparator<Task> sorting) {
        List<Task> sortedTaskList = new ArrayList<>(taskList);
        sortedTaskList.sort(sorting);
        return sortedTaskList;
    }

    @Override
    public List<Task> search(LocalDate localDate) {
        List<Task> result = new ArrayList<>();

        for (Task task: taskList) {
            if (task.getDueDate().isEqual(localDate)) {
                result.add(task);
            }
        }

        return result;
    }

    @Override
    public void save() throws IOException {
        String json = serializer.toJson(taskList);
        fileService.writeToFile(json);
    }

    @Override
    public void load() throws IOException {
        String content = fileService.readFromFile();
        taskList.clear();
        Collection<Task> loadedTasks = serializer.fromJson(content);
        taskList.addAll(loadedTasks);
        idSequence = taskList.size();
    }
}
