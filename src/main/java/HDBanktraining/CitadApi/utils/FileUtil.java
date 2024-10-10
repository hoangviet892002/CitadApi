package HDBanktraining.CitadApi.utils;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileWriter;


public class FileUtil {
    public static void writeFile(File file,Object data) {

        ObjectMapper objectMapper = new ObjectMapper();

        // Register the JavaTimeModule to handle Java 8 date/time types
        objectMapper.registerModule(new JavaTimeModule());
        // write message to file
        try {
            // write message to file, track the message all fields, but write at file txt
            String jsonData = objectMapper.writeValueAsString(data);

            // Write the JSON string to the file
            FileWriter myWriter = new FileWriter(file);
            myWriter.write(jsonData);
            myWriter.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
