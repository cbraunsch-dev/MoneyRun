package chris.braunschweiler.touchngo.io.test;

import junit.framework.Assert;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;
import chris.braunschweiler.touchngo.io.ILevelLoader;
import chris.braunschweiler.touchngo.io.LevelDTO;
import chris.braunschweiler.touchngo.io.LevelLoaderImpl;

import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;

import android.test.AndroidTestCase;

/**
 * Test class for level loader.
 * IMPORTANT NOTE: In order for the test methods to be called by the testing framework, their names have to begin with "test".
 * @author chrisbraunschweiler
 *
 */
public class TestLevelLoader extends AndroidTestCase{
	
	public void testLoadFirstLevelMap() throws ClassNotFoundException{
		ILevelLoader testee = new LevelLoaderImpl();
		String expectedLevelMap = "tmx/gamelevel1map.tmx";
		
		LevelDTO level = testee.loadLevel();
		String actualLevelMap = level.getLevelMap();
		
		Assert.assertEquals(expectedLevelMap, actualLevelMap);
	}
	
	public void testLoadFirstLevelNumberOfPermissibleMoves() throws ClassNotFoundException{
		ILevelLoader testee = new LevelLoaderImpl();
		int expectedNumberOfPermissibleMoves = 2;
		
		LevelDTO level = testee.loadLevel();
		int actualNumberOfPermissibleMoves = level.getNumberOfPermissibleMoves();
		
		Assert.assertEquals(expectedNumberOfPermissibleMoves, actualNumberOfPermissibleMoves);
	}
	
	public void testLoadMiddleLevelMap() throws ClassNotFoundException{
		ILevelLoader testee = new LevelLoaderImpl();
		String expectedLevelMap = "tmx/gamelevel2map.tmx";
		
		testee.loadLevel();
		LevelDTO level = testee.loadLevel();
		String actualLevelMap = level.getLevelMap();
		
		Assert.assertEquals(expectedLevelMap, actualLevelMap);
	}
	
	public void testLoadMiddleLevelNumberOfPermissibleMoves() throws ClassNotFoundException{
		ILevelLoader testee = new LevelLoaderImpl();
		int expectedNumberOfPermissibleMoves = 3;
		
		testee.loadLevel();
		LevelDTO level = testee.loadLevel();
		int actualNumberOfPermissibleMoves = level.getNumberOfPermissibleMoves();
		
		Assert.assertEquals(expectedNumberOfPermissibleMoves, actualNumberOfPermissibleMoves);
	}
	
	public void testLoadSpecificLevelThatExistsThenSucceed() throws ClassNotFoundException{
		try{
			ILevelLoader testee = new LevelLoaderImpl();
			int expectedNumberOfPermissibleMoves = 5;
			String expectedLevelMap = "tmx/gamelevel3map.tmx";
			int expectedLevelIndex = 3;
			
			LevelDTO level = testee.loadLevel(expectedLevelMap);
			int actualNumberOfPermissibleMoves = level.getNumberOfPermissibleMoves();
			String actualLevelMap = level.getLevelMap();
			int actualLevelIndex = testee.getIndexOfNextLevel();
			
			Assert.assertEquals(expectedNumberOfPermissibleMoves, actualNumberOfPermissibleMoves);
			Assert.assertEquals(expectedLevelMap, actualLevelMap);
			Assert.assertEquals(expectedLevelIndex, actualLevelIndex);
		}
		catch(LevelNotFoundException e){
		}
	}
	
	public void testLoadSpecificLevelThatDoesNotExistThenFail() throws ClassNotFoundException{
		try{
			ILevelLoader testee = new LevelLoaderImpl();
			String levelWhichDoesNotExist = "levelwhichdoesnotexist.tmx";
			
			testee.loadLevel(levelWhichDoesNotExist);
			
			Assert.fail("loadLevel() Method should have thrown a LevelNotFoundException");
		}
		catch(LevelNotFoundException e){
			Assert.assertTrue(true);
		}
	}
}
