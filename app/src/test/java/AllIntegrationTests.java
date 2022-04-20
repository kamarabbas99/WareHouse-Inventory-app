import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import logic.AccountIntegrationTest;
import logic.InventoryIntegrationTest;
import logic.InventoryManagerIntegrationTest;
import logic.TransactionIntegrationTest;
import objects.Transaction;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        InventoryManagerIntegrationTest.class,
        InventoryIntegrationTest.class,
        AccountIntegrationTest.class,
        TransactionIntegrationTest.class
})
public class AllIntegrationTests {
}
