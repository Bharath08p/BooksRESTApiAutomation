package helpers;
import java.util.*;
import pojo.Books;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Paths;

public class TestDataReader {

	private static final String FILE_PATH = "src/test/resources/testdata/BooksData.json";

    public static List<Books> loadTestData() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(Paths.get(FILE_PATH).toFile(), new TypeReference<List<Books>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error reading test data from JSON", e);
        }
    }
}