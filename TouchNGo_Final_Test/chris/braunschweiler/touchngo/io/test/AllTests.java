package chris.braunschweiler.touchngo.io.test;

import junit.framework.Test;
import android.test.suitebuilder.TestSuiteBuilder;

public class AllTests {
	public static Test suite() {
		return new TestSuiteBuilder(AllTests.class).includeAllPackagesUnderHere().build();
	}
}