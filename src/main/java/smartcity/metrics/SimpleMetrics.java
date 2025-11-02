package smartcity.metrics;

import java.util.HashMap;
import java.util.Map;

public class SimpleMetrics implements Metrics {

    private final Map<String, Long> counters = new HashMap<>();
    private final Map<String, Long> timers = new HashMap<>();

    @Override
    public void inc(String key) {
        counters.put(key, counters.getOrDefault(key, 0L) + 1);
    }

    @Override
    public long get(String key) {
        return counters.getOrDefault(key, 0L);
    }

    @Override
    public void start(String key) {
        timers.put(key, System.nanoTime());
    }

    @Override
    public void stop(String key) {
        Long st = timers.get(key);
        if (st != null) {
            long dur = System.nanoTime() - st;
            counters.put(key, dur);
        }
    }
}
