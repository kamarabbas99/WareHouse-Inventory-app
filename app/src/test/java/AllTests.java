import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import logic.AccountAccesserTest;
import logic.InventoryManagerIntegrationTest;
import logic.ItemAccesserTest;
import objects.AccountTest;
import objects.ItemTest;
import logic.InventoryManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        ItemTest.class,
        InventoryManagerTest.class,
        AccountAccesserTest.class,
        ItemAccesserTest.class,
        InventoryManagerIntegrationTest.class
})
public class AllTests {
}
