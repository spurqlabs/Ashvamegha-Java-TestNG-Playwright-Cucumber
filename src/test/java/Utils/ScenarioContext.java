package Utils;


import io.cucumber.java.Scenario;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    // ✅ One context per thread (scenario)
    private static final ThreadLocal<Map<String, Object>> context =
            ThreadLocal.withInitial(HashMap::new);

    private static final ThreadLocal<Scenario> scenario =
            new ThreadLocal<>();

    // ================= DATA =================
    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    public static void put(String key, Object value) {
        context.get().put(key, value);
    }

    public static String get(String key) {
        Object value = context.get().get(key);
        return value != null ? value.toString() : null;
    }

    public static Object getObject(String key) {
        return context.get().get(key);
    }

    // ================= SCENARIO =================
    public static void setScenario(Scenario sc) {
        scenario.set(sc);
    }

    public static Scenario getScenario() {
        return scenario.get();
    }

    // ================= CLEANUP =================
    public static void clear() {
        context.remove();
        scenario.remove();
    }
}
