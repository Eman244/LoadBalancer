import me.manny.ntu.logic.LoadBalance;
import me.manny.ntu.logic.ProviderPool;
import me.manny.ntu.logic.RandomLoadBalance;
import me.manny.ntu.logic.RoundRobinLoadBalance;
import me.manny.ntu.exception.ProviderCapacityException;
import me.manny.ntu.exception.RemoveProviderException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoadBalanceTest {

    @Test
    public void get() {
        testLoadBalancer(new RoundRobinLoadBalance());
        testLoadBalancer(new RandomLoadBalance());
    }

    private void testLoadBalancer(LoadBalance loadBalance) {
        assertNotNull(loadBalance.get());
    }

    @Test
    public void addRemoveTest() {
        LoadBalance lb = new RoundRobinLoadBalance();
        lb.remove("192.168.2.1");
        assertEquals(9, ProviderPool.getProviderMap().size());
        lb.add("192.168.2.2");
        assertEquals(10, ProviderPool.getProviderMap().size());
    }

    @Test(expected = ProviderCapacityException.class)
    public void add() {
        LoadBalance lb = new RoundRobinLoadBalance();
        lb.add("192.168.2.1");
    }

    @Test(expected = RemoveProviderException.class)
    public void remove() {
        LoadBalance lb = new RoundRobinLoadBalance();
        lb.remove("192.168.2.3");
    }
}