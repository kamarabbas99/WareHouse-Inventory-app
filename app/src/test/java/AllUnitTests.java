import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import logic.AccountAccessorTest;
import logic.InventoryAccessor;
import logic.InventoryAccessorTest;
import logic.TransactionAccessorTest;
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
        InventoryTest.class,
        InventoryManagerTest.class,
        AccountAccessorTest.class,
        TransactionAccessorTest.class,
        InventoryAccessorTest.class

})
public class AllUnitTests {
}
