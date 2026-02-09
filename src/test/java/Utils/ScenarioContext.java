package Utils;

import io.cucumber.java.Scenario;

/**
 * Scenario context holder for use across step definitions
 */
public class ScenarioContext {

    private static ThreadLocal<Scenario> scenarioThreadLocal = new ThreadLocal<>();

    /**
     * Set the scenario for current thread
     */
    public static void setScenario(Scenario scenario) {
        scenarioThreadLocal.set(scenario);
    }

    /**
     * Get the scenario for current thread
     */
    public static Scenario getScenario() {
        return scenarioThreadLocal.get();
    }

    /**
     * Clear scenario from thread local
     */
    public static void clear() {
        scenarioThreadLocal.remove();
    }
}
