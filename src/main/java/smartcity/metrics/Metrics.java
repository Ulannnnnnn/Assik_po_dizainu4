package smartcity.metrics;

public interface Metrics {
    void inc(String key);
    long get(String key);
    void start(String key);
    void stop(String key);
}
