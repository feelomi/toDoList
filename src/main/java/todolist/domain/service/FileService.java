package todolist.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileService {

    private final String jsonFile;

    public FileService(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public void writeToFile(String content) throws IOException {
        Files.writeString(Path.of(jsonFile), content);
    }

    public String readFromFile() throws IOException {
        return Files.readString(Path.of(jsonFile));
    }
}


