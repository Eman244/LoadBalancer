package me.manny.ntu.logic;

import me.manny.ntu.exception.ProviderCapacityException;
import me.manny.ntu.exception.RemoveProviderException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Abstract Load balance class.
 */
public abstract class LoadBalance {
    /**
     * Abstract method to get provider based on Load balance strategy.
     * @return provider ip.
     */
    public abstract String get();

    /**
     * Remove provider from provider map.
     * May return {@link RemoveProviderException}
     *
     * @param provider ip.
     */
    public void remove(String provider) {
        AtomicInteger val = ProviderPool.getProviderMap().remove(provider);
        if (val == null) {
            throw new RemoveProviderException(provider);
        }
    }

    /**
     * Add provider to the provider pool.
     * May return {@link ProviderCapacityException} if max capacity is reached.
     *
     * @param provider ip.
     */
    public void add(String provider) {
        if (ProviderPool.getProviderMap().size() < ProviderPool.capacity) {
            ProviderPool.getProviderMap()
                    .putIfAbsent(provider, new AtomicInteger(ProviderPool.heartBeatCount));
        } else {
            throw new ProviderCapacityException(provider);
        }
    }
}
