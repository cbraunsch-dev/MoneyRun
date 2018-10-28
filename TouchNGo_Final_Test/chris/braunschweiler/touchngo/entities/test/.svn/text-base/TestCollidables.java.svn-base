package chris.braunschweiler.touchngo.entities.test;

import chris.braunschweiler.touchngo.entities.Goal;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.ICollidedListener;

import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;

import android.test.AndroidTestCase;

public class TestCollidables extends AndroidTestCase{

	private ICollidable testeeGoal;
	
	public void setUp(){
		testeeGoal = new Goal(0, 0, 0);
	}
	
	@UsesMocks(ICollidedListener.class)
	public void testCollideWithGoalThenNotifyCollidedListener() throws ClassNotFoundException{
		ICollidedListener collidedListenerMock = AndroidMock.createMock(ICollidedListener.class);
		AndroidMock.expect(collidedListenerMock.collided(testeeGoal)).andReturn(true).times(1);
		
		AndroidMock.replay(collidedListenerMock);
		
		testeeGoal.registerCollidedListener(collidedListenerMock);
		testeeGoal.collideWith();
		
		AndroidMock.verify(collidedListenerMock);
	}
}
