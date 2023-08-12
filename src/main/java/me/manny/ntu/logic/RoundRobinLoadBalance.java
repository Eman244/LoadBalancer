package me.manny.ntu.logic;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Round robin load balance.
 */
public class RoundRobinLoadBalance extends LoadBalance {

    private static Integer position = 0;
    private static ReentrantLock lock;

    /**
     * Get the provider based on round robin load balance strategy.
     * @return provider.
     */
    @Override
    public String get() {
        lock = new ReentrantLock();
        List<String> providerList = ProviderPool.getProviderList();
        String provider;
        lock.lock();
        try {
            int index = (position) % providerList.size();
            provider = providerList.get(index);
            ++position;
        } finally {
            lock.unlock();
        }
        return provider;
    }
}
