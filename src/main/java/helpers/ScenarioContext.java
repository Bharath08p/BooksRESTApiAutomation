package helpers;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> scenarioContext = ThreadLocal.withInitial(HashMap::new);

    public static void set(String key, Object value) {
        scenarioContext.get().put(key, value);
        
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T get(String key, Class<T> type) {
        Object value = scenarioContext.get().get(key);
        if (value == null) {
            throw new RuntimeException("No value found in ScenarioContext for key: " + key);
        }
        return (T) value;
    }

    public static void clear() {
        scenarioContext.get().clear();
    }
    
}