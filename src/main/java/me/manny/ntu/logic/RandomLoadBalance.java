package me.manny.ntu.logic;

import java.util.List;
import java.util.Random;

/**
 * Random load balance implementation.
 */
public class RandomLoadBalance extends LoadBalance {

    /**
     * Get the provider based on random load balance strategy.
     * @return provider.
     */
    @Override
    public String get() {
        List<String> providerList = ProviderPool.getProviderList();
        int randomIndex = new Random().nextInt(providerList.size());
        return providerList.get(randomIndex);
    }

}
