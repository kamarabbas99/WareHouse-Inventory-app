import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import presentation.AccountCreationSystemTest;
import presentation.AccountPermissionSystemTest;
import presentation.AddItemSystemTest;
import presentation.CreateInventorySystemTest;
import presentation.CreateItemSystemTest;
import presentation.ReportSystemTest;
import presentation.StockInformationSystemTest;
import presentation.ViewInventorySystemTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountCreationSystemTest.class,
        AccountPermissionSystemTest.class,
        AddItemSystemTest.class,
        CreateInventorySystemTest.class,
        StockInformationSystemTest.class,
        ViewInventorySystemTest.class,
        CreateItemSystemTest.class,
        ReportSystemTest.class
})
public class AllSystemTests {
}
