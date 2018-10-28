package chris.braunschweiler.touchngo.controller.test;

import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
import org.anddev.andengine.entity.scene.Scene;

import junit.framework.Assert;
import chris.braunschweiler.touchngo.controller.LevelHandler;
import chris.braunschweiler.touchngo.entities.Goal;
import chris.braunschweiler.touchngo.entities.ICollidable;
import chris.braunschweiler.touchngo.entities.IPlayer;
import chris.braunschweiler.touchngo.entities.ICollidedListener;
import chris.braunschweiler.touchngo.exceptions.LevelLayerException;
import chris.braunschweiler.touchngo.exceptions.LevelNotFoundException;
import chris.braunschweiler.touchngo.exceptions.MapLoadException;
import chris.braunschweiler.touchngo.io.ILevelLoader;
import chris.braunschweiler.touchngo.io.LevelDTO;
import chris.braunschweiler.touchngo.model.ILevelDataHandler;

import com.google.android.testing.mocking.AndroidMock;
import com.google.android.testing.mocking.UsesMocks;

import android.test.AndroidTestCase;

public class TestLevelHandler extends AndroidTestCase{
	
	private LevelHandler testeeLevelHandler;
	
	@UsesMocks(ILevelLoader.class)
	public void testLoadLevelThenSetLevelProperties() throws ClassNotFoundException{
		int expectedNumberOfPermissibleMoves = 2;
		String expectedLevelMap = "Level1map.tmx";
		int expectedMoveCount = 0;
		LevelDTO expectedLevel = new LevelDTO(expectedNumberOfPermissibleMoves, expectedLevelMap);
		ILevelLoader levelLoaderMock = AndroidMock.createMock(ILevelLoader.class);
		AndroidMock.expect(levelLoaderMock.loadLevel()).andReturn(expectedLevel);
		
		AndroidMock.replay(levelLoaderMock);
		testeeLevelHandler = new LevelHandler(levelLoaderMock);
		testeeLevelHandler.loadLevel();
		int actualNumberOfPermissibleMoves = testeeLevelHandler.getNumberOfPermissibleMoves();
		String actualLevelMap = testeeLevelHandler.getCurrentLevel();
		int actualMoveCount = testeeLevelHandler.getMoveCount();
		
		AndroidMock.verify(levelLoaderMock);
		Assert.assertEquals(expectedNumberOfPermissibleMoves, actualNumberOfPermissibleMoves);
		Assert.assertEquals(expectedLevelMap, actualLevelMap);
		Assert.assertEquals(expectedMoveCount, actualMoveCount);
	}
	
	@UsesMocks(ILevelLoader.class)
	public void testLoadSpecificLevelThatExistsThenSetLevelProperties() throws ClassNotFoundException{
		try{
			int expectedNumberOfPermissibleMoves = 5;
			String expectedLevelMap = "Level3map.tmx";
			int expectedMoveCount = 0;
			LevelDTO expectedLevel = new LevelDTO(expectedNumberOfPermissibleMoves, expectedLevelMap);
			ILevelLoader levelLoaderMock = AndroidMock.createMock(ILevelLoader.class);
			AndroidMock.expect(levelLoaderMock.loadLevel(expectedLevelMap)).andReturn(expectedLevel);
			
			AndroidMock.replay(levelLoaderMock);
			testeeLevelHandler = new LevelHandler(levelLoaderMock);
			testeeLevelHandler.loadLevel(expectedLevelMap);
			int actualNumberOfPermissibleMoves = testeeLevelHandler.getNumberOfPermissibleMoves();
			String actualLevelMap = testeeLevelHandler.getCurrentLevel();
			int actualMoveCount = testeeLevelHandler.getMoveCount();
			
			AndroidMock.verify(levelLoaderMock);
			Assert.assertEquals(expectedNumberOfPermissibleMoves, actualNumberOfPermissibleMoves);
			Assert.assertEquals(expectedLevelMap, actualLevelMap);
			Assert.assertEquals(expectedMoveCount, actualMoveCount);
		}
		catch(LevelNotFoundException e){
		}
	}
	
	@UsesMocks(ILevelLoader.class)
	public void testLoadSpecificLevelThatDoesNotExistThenFail() throws ClassNotFoundException{
		try{
			String expectedLevelMap = "levelthatdoesnotexist.tmx";
			ILevelLoader levelLoaderMock = AndroidMock.createMock(ILevelLoader.class);
			AndroidMock.expect(levelLoaderMock.loadLevel(expectedLevelMap)).andThrow(new LevelNotFoundException("The level could not be found."));
			
			AndroidMock.replay(levelLoaderMock);
			testeeLevelHandler = new LevelHandler(levelLoaderMock);
			testeeLevelHandler.loadLevel(expectedLevelMap);
			
			Assert.fail("Should not have been able to load the level because the level map does not exist.");
		}
		catch(LevelNotFoundException e){
			Assert.assertTrue(true);
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testAddLevelLayersToScene() throws ClassNotFoundException{
		try{
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.addLevelLayersToScene((Scene) AndroidMock.anyObject())).andReturn(true).times(1);
			
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			testeeLevelHandler.addLevelLayersToScene( new Scene(0));
			
			AndroidMock.verify(levelDataHandlerMock);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testCheckForPlayerLevelEntityCollisions() throws ClassNotFoundException{
		try{
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.checkForPlayerLevelEntityCollisions(AndroidMock.anyFloat(), AndroidMock.anyFloat())).andReturn(true).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			testeeLevelHandler.checkForPlayerLevelEntityCollisions(100, 100);
			
			AndroidMock.verify(levelDataHandlerMock);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testCheckForPlayerLevelEntityCollisionsWithICollidablePlayer() throws ClassNotFoundException{
		try{
			IPlayer player = null;
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.checkForPlayerLevelEntityCollisions(AndroidMock.anyFloat(), AndroidMock.anyFloat(), player)).andReturn(true).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			testeeLevelHandler.checkForCollidablePlayerLevelEntityCollisions(100, 100, player);
			
			AndroidMock.verify(levelDataHandlerMock);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testGetNumberOfPlayersInLevel(){
		try{
			int expectedNumberOfPlayersInLevel = 2;
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.getNumberOfPlayersInLevel()).andReturn(expectedNumberOfPlayersInLevel).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			int actualNumberOfPlayersInLevel = testeeLevelHandler.getNumberOfPlayersInLevel();
			
			AndroidMock.verify(levelDataHandlerMock);
			Assert.assertEquals(expectedNumberOfPlayersInLevel, actualNumberOfPlayersInLevel);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testGetPlayerStartPositionTile(){
		try{
			TMXTile expectedStartPosition = new TMXTile(0, 0, 0, 0, 0, null);
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.getPlayerStartPositionTile(AndroidMock.anyInt())).andReturn(expectedStartPosition).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			TMXTile actualStartPosition = testeeLevelHandler.getPlayerStartPositionTile(0);
			
			AndroidMock.verify(levelDataHandlerMock);
			Assert.assertEquals(expectedStartPosition, actualStartPosition);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
		catch(MapLoadException e){
			Assert.fail("MapLoadException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testSetTMXLevelMap(){
		TMXTiledMap levelMap = null;
		ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
		AndroidMock.expect(levelDataHandlerMock.setTMXLevelMap((TMXTiledMap)AndroidMock.anyObject())).andReturn(true).times(1);
	
		AndroidMock.replay(levelDataHandlerMock);
		testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
		testeeLevelHandler.setTMXLevelMap(levelMap);
		
		AndroidMock.verify(levelDataHandlerMock);
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testLoadLevelCollidables(){
		/*try{
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.loadLevelEntities(null)).andReturn(true).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			testeeLevelHandler.loadLevelCollidables(null);
			
			AndroidMock.verify(levelDataHandlerMock);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}*/
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testGetTMXTileAtPosition(){
		try{
			TMXTile expectedTile = new TMXTile(0, 0, 0, 0, 0, null);
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.getTMXTileAtPosition(AndroidMock.anyFloat(), AndroidMock.anyFloat())).andReturn(expectedTile).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			TMXTile actualTile = testeeLevelHandler.getTMXTileAtPosition(100, 100);
			
			AndroidMock.verify(levelDataHandlerMock);
			Assert.assertEquals(expectedTile, actualTile);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testGetTMXTileAtColAndRow(){
		try{
			TMXTile expectedTile = new TMXTile(0, 0, 0, 0, 0, null);
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.getTMXTileAtColAndRow(AndroidMock.anyInt(), AndroidMock.anyInt())).andReturn(expectedTile).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			TMXTile actualTile = testeeLevelHandler.getTMXTileAtColAndRow(3, 4);
			
			AndroidMock.verify(levelDataHandlerMock);
			Assert.assertEquals(expectedTile, actualTile);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testGetNumberOfGoalsOfCurrentLevel(){
		int expectedNumberOfGoals = 3;
		ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
		AndroidMock.expect(levelDataHandlerMock.getNumberOfGoalsOfCurrentLevel()).andReturn(expectedNumberOfGoals).times(1);
	
		AndroidMock.replay(levelDataHandlerMock);
		testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
		int actualNumberOfGoals = testeeLevelHandler.getNumberOfGoalsOfCurrentLevel();
		
		AndroidMock.verify(levelDataHandlerMock);
		Assert.assertEquals(expectedNumberOfGoals, actualNumberOfGoals);
	}
	
	@UsesMocks(ILevelDataHandler.class)
	public void testExistsGoalAtPosition(){
		try{
			boolean expectedResult = true;
			ILevelDataHandler levelDataHandlerMock = AndroidMock.createMock(ILevelDataHandler.class);
			AndroidMock.expect(levelDataHandlerMock.existsGoalAtPosition(AndroidMock.anyFloat(), AndroidMock.anyFloat())).andReturn(expectedResult).times(1);
		
			AndroidMock.replay(levelDataHandlerMock);
			testeeLevelHandler = new LevelHandler(levelDataHandlerMock);
			boolean actualResult = testeeLevelHandler.existsGoalAtPosition(100, 100);
			
			AndroidMock.verify(levelDataHandlerMock);
			Assert.assertEquals(expectedResult, actualResult);
		}
		catch(LevelLayerException e){
			Assert.fail("LevelLayerException occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
