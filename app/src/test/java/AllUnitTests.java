import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import objects.AccountTest;
import objects.ItemTest;
import objects.TransactionTest;
import logic.InventoryManagerTest;
import objects.InventoryTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        ItemTest.class,
        TransactionTest.class,
        InventoryManagerTest.class,
        InventoryTest.class
})
public class AllUnitTests {
}
