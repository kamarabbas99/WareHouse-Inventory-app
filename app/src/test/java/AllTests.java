import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import logic.AccountAccesserTest;
import logic.InventoryManagerIntegrationTest;
import logic.ItemAccesserTest;
import objects.AccountTest;
import objects.ItemTest;
import logic.InventoryManagerTest;
import objects.InventoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        ItemTest.class,
        InventoryManagerTest.class,
        AccountAccesserTest.class,
        ItemAccesserTest.class,
        InventoryTest.class
})
public class AllTests {
}
