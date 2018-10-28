package chris.braunschweiler.touchngo.activities.test;

import junit.framework.Assert;
import chris.braunschweiler.touchngo.activities.MainMenuActivity;
import chris.braunschweiler.touchngo.activities.MainMenuActivity.MainMenuActivityHelper;
import android.test.ActivityInstrumentationTestCase2;

public class MainMenuActivityTest extends
		ActivityInstrumentationTestCase2<MainMenuActivity> {

	private MainMenuActivity testee;

	public MainMenuActivityTest() {
		super("chris.braunschweiler.touchngo.activities", MainMenuActivity.class);
	}
	
	public void setUp()throws Exception{
		super.setUp();
		
		testee = getActivity();
	}
	
	public void testLoadMenuScreenWhenNoGameProgressExists(){
		int expectedLayout = testee.getLayoutWhenNoGameProgressExists();
		MainMenuActivityHelperMock helperMock = new MainMenuActivityHelperMock();
		this.testee.setMainMenuActivityHelper(helperMock);
		this.testee.setupMenuLayout();
		int actualLayout = testee.getMenuLayout();
		
		Assert.assertEquals(expectedLayout, actualLayout);
	}
	
	public void testLoadMenuScreenWhenGameProgressExists(){
		int expectedLayout = testee.getLayoutWhenGameProgressExists();
		MainMenuActivityHelperMock helperMock = new MainMenuActivityHelperMock();
		helperMock.setWhetherGameProgressExists(true);
		
		this.testee.setMainMenuActivityHelper(helperMock);
		this.testee.setupMenuLayout();
		int actualLayout = testee.getMenuLayout();
		
		Assert.assertEquals(expectedLayout, actualLayout);
	}
	
	private class MainMenuActivityHelperMock extends MainMenuActivityHelper{
		
		private boolean gameProgressExists;
		
		public MainMenuActivityHelperMock(){
			testee.super();
			gameProgressExists = false;
		}
		
		public void setInRealUse(boolean realUse){
			super.setInRealUse(false);
		}
		
		public void setWhetherGameProgressExists(boolean gameProgressExists){
			this.gameProgressExists = gameProgressExists;
		}
		
		public boolean existsGameProgress(){
			return this.gameProgressExists;
		}
	}
}
