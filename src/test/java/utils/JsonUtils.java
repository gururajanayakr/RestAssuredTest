package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class JsonUtils {
    public static Map<String, String> getTestCaseData(String tcName) {
        try {
            String jsonContent = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/main/java/resources/data.json")));
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, String>> data = objectMapper.readValue(jsonContent, Map.class);
            if(data.containsKey(tcName))
            {
                return data.get(tcName);
            }
            else
            {
                throw  new IllegalArgumentException("Test Case Name is not Found"+tcName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while reading data.json file",e);
        }
    }

    public static String getJsonData(String value) {
        String[] compTCName = value.split("\\.");
        String tcName = compTCName[0].substring(1);
        String jsonKey = compTCName[1];
        Map<String, String> testCaseData = JsonUtils.getTestCaseData(tcName);
        value = testCaseData.get(jsonKey);
        return value;
    }
}
