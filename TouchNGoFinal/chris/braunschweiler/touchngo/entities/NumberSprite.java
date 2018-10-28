package chris.braunschweiler.touchngo.entities;

import java.util.ArrayList;
import java.util.List;

import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;

import chris.braunschweiler.touchngo.exceptions.DisplayFormatException;

/**
 * Implementation of the INumberSprite interface.
 * @author chrisbraunschweiler
 *
 */
public class NumberSprite implements INumberSprite{
	private static final int NUMBER_OF_DIGITS_TO_DISPLAY = 3;
	private static final int INDIVIDUAL_DIGIT_SPRITE_WIDTH = 40;	//The size (width) of the individual numbers (in pixels)
	private static final int INDIVIDUAL_DIGIT_SPRITE_HEIGHT = 60;	//The size (height) of the individual numbers (in pixels)
	private static final int SPACE_BETWEEN_NUMBERS = 5;
	private List<AnimatedSprite> sprites;
	
	public NumberSprite(ArrayList<TiledTextureRegion> textureRegions, int posX, int posY, Scene scene) throws DisplayFormatException{
		this.sprites = new ArrayList<AnimatedSprite>();
		this.initializeSprites(textureRegions, posX, posY, scene);
	}

	private void initializeSprites(ArrayList<TiledTextureRegion> textureRegions, int posX, int posY, Scene scene) throws DisplayFormatException{
		if(textureRegions.size() > NUMBER_OF_DIGITS_TO_DISPLAY){
			throw new DisplayFormatException("The Number Sprite currently only supports 3 digit numbers. You passed a " + textureRegions.size() + " digit number.");
		}
		
		int i = 0;
		for(TiledTextureRegion textureRegion : textureRegions){
			this.sprites.add(new AnimatedSprite(posX + NumberSprite.SPACE_BETWEEN_NUMBERS + (i * NumberSprite.INDIVIDUAL_DIGIT_SPRITE_WIDTH), posY, textureRegion));
			i++;
		}
		
		//Add sprites to scene
		for(AnimatedSprite sprite : this.sprites){
			scene.getTopLayer().addEntity(sprite);
		}
	}

	public void displayNumber(int number) throws DisplayFormatException {
		if(number > 999 || number < 0){
			throw new DisplayFormatException("The Number Sprite currently only supports 3 digit numbers. You passed " + number + ".");
		}
		
		int hundredsPositionValue = number / 100;
		number %= 100;
		int tensPositionValue = number / 10;
		number %= 10;
		int onesPositionValue = number;
		
		this.sprites.get(0).animate(new long[]{200, 200}, hundredsPositionValue * 2, (hundredsPositionValue * 2) + 1, true);
		this.sprites.get(1).animate(new long[]{200, 200}, tensPositionValue * 2, (tensPositionValue * 2) + 1, true);
		this.sprites.get(2).animate(new long[]{200, 200}, onesPositionValue * 2, (onesPositionValue * 2) + 1, true);
	}
}
