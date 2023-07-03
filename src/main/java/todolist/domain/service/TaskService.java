package todolist.domain.service;

import todolist.domain.data.Task;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public interface TaskService {
    Task create(String description, LocalDate dueDate);
    void delete(int id);
    void update(int id, String description);

    void updateStatus(int id, boolean newStatus);

    void updateDate(int id, LocalDate newDate);

    List<Task> getTaskList(Comparator<Task> sorting);

    List<Task> search(LocalDate localDate);

    void save() throws IOException;

    void load() throws IOException;
}
