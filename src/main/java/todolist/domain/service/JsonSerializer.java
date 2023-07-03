package todolist.domain.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import todolist.domain.data.Task;
import todolist.domain.gson.LocalDateAdapter;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JsonSerializer {

    public List<Task> fromJson(String json){
        Gson gson = createGson();
        Type listOfTasks = new TypeToken<ArrayList<Task>>() {}.getType();
        return gson.fromJson(json, listOfTasks);
    }

    public String toJson(List<Task> taskList) {
        Gson gson = createGson();
        return gson.toJson(taskList);
    }

    private static Gson createGson() {
        GsonBuilder gsonBuilder = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe());
        return gsonBuilder.create();
    }
}
