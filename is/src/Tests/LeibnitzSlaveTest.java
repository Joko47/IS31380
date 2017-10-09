package Tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import commexercise.TestPiSyncMasterTest;

@RunWith(Suite.class)
@SuiteClasses({ commexercise.TestPiSyncMasterTest.class, commexercise.TestPiSyncMasterTestFail.class })
public class LeibnitzSlaveTest {

}
