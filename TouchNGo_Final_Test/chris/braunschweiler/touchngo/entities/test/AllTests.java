package chris.braunschweiler.touchngo.entities.test;

import junit.framework.Test;
import android.test.suitebuilder.TestSuiteBuilder;

public class AllTests {
	public static Test suite() {
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
}
