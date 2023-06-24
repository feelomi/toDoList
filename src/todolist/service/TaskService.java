package todolist.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import todolist.domain.Task;
import todolist.gson.LocalDateAdapter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class TaskService {
    private final static String JSON_FILE = "saved-todo.json";

    private final List<Task> taskList = new ArrayList<>();
    private int idSequence = 0;

    public Task create(String description, LocalDate dueDate) {
        Task newTask = new Task( ++idSequence, description, dueDate);

        //TODO save new task
        taskList.add(newTask);
        return newTask;
    }

        public void delete(int id) {
            taskList.removeIf(task -> task.getId() == id);
        }

    public void update(int id, String description) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDescription(description);
            }
        }
    }

    public void updateStatus(int id, boolean newStatus) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDone(newStatus);
            }
        }
    }
    public void updateDate(int id, LocalDate newDate) {
        for (Task task : taskList) {
            if (task.getId()==id) {
                task.setDueDate(newDate);
            }
        }
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public List<Task> search(LocalDate localDate) {
        List<Task> result = new ArrayList<>();

        for (Task task: taskList) {
            if (task.getDueDate().isEqual(localDate)) {
                result.add(task);
            }
        }

        return result;
    }

    public void saveToJson() throws IOException {
        Gson gson = createGson();
        String json = gson.toJson(taskList);
        Files.writeString(Path.of(JSON_FILE), json);
    }

    public void loadFromJson() throws IOException {
        String content = Files.readString(Path.of(JSON_FILE));
        Gson gson = createGson();
        Type listOfTasks = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> loadedTasks = gson.fromJson(content, listOfTasks);
        taskList.clear();
        taskList.addAll(loadedTasks);
        idSequence = taskList.size();
    }

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
        return gsonBuilder.create();
    }
}