package me.manny.ntu.logic;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProviderPool {

    private static Map<String, AtomicInteger> providerMap;
    public static final int taskInterval = 30;
    public static final int capacity = 10;
    public static final int heartBeatCount = 2;


    /**
     * The Provider pool construct.
     *
     * ProviderPool is a Map<Provider, HeartBeatCount>
     * Active : HeartBeatCount = 2
     * inActive : HeartBeatCount < 2
     */
    static {
        providerMap = new ConcurrentHashMap<>(capacity);
        providerMap.put("192.168.1.1", new AtomicInteger(2));
        providerMap.put("192.168.1.2", new AtomicInteger(2));
        providerMap.put("192.168.1.3", new AtomicInteger(2));
        providerMap.put("192.168.1.4", new AtomicInteger(2));
        providerMap.put("192.168.1.5", new AtomicInteger(2));
        providerMap.put("192.168.1.6", new AtomicInteger(2));
        providerMap.put("192.168.1.7", new AtomicInteger(2));
        providerMap.put("192.168.1.8", new AtomicInteger(2));
        providerMap.put("192.168.1.9", new AtomicInteger(2));
        providerMap.put("192.168.2.1", new AtomicInteger(2));

        HealthCheckTask task = new HealthCheckTask(heartBeatCount);
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task, 0, taskInterval * 1000);
    }

    /**
     * Get's total providerPool map.
     *
     * @return provider Map
     */
    public static Map<String, AtomicInteger> getProviderMap() {
        return providerMap;
    }

    /**
     * Get list of active providers.
     * Active provider -> heartbeat count is 2
     * InActive provider -> heartbeat count < 2
     *
     * @return List of active provider IP's
     */
    public static List<String> getProviderList() {
        return providerMap.entrySet()
                .stream()
                .filter(entry -> entry.getValue().get() == 2)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
