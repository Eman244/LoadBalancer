package me.manny.ntu.logic;

import java.util.Random;
import java.util.TimerTask;

/**
 * HealthCheck TimerTask for health monitoring and cluster management of provider pool.
 * similar to zookeeper behaviour.
 */
public class HealthCheckTask extends TimerTask {

    private int heartBeatCount;

    public HealthCheckTask(final int count) {
        this.heartBeatCount = count;
    }

    /**
     * run() background checks.
     * if check fails i.e inActive - heartbeat count is set = 0
     * And if passes i.e active and heartbeat count is less than 2
     */
    @Override
    public void run() {
        ProviderPool.getProviderMap()
                .entrySet().stream()
                .forEach(entry -> {
                    if (!check(entry.getKey())) {
                        entry.getValue().set(0);
                    } else if (entry.getValue().get() < this.heartBeatCount) {
                        entry.getValue().incrementAndGet();
                    }
                });
    }

    /**
     * Method to perform health check of provider.
     * Basically some native call is needed to check if provider is active.
     * HERE we are simulating with RandomBoolean.
     */
    private boolean check(String provider) {
        return randomBoolean(provider.hashCode());
    }

    /**
     * For simulation we would use randomBoolean().
     */
    private boolean randomBoolean(int seed) {
        Random rd = new Random(seed);
        return rd.nextBoolean();
    }
}
