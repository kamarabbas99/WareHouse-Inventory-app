import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import objects.AccountTest;
import objects.ItemTest;
import logic.InventoryManagerTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        ItemTest.class,
        InventoryManagerTest.class
})
public class AllTests {
}
