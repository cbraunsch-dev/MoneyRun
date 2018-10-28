package chris.braunschweiler.touchngo.gameengine;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import chris.braunschweiler.touchngo.gameentities.GameEntity;
import chris.braunschweiler.touchngo.level.LevelEntity;
import chris.braunschweiler.touchngo.activities.R;
import chris.braunschweiler.touchngo.callbackinterfaces.GameEngineListener;
import android.util.Log;

/**
 * The game engine that displays and updates the game level.
 * @author chrisbraunschweiler March 2010.
 *
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	public class GameThread extends Thread implements OnTouchListener{
		
		private int canvasWidth;
		private int canvasHeight;
		private boolean running;
		private SurfaceHolder mSurfaceHolder;
		private Context mContext;
		private GameEngineListener gameEngineListener;	//Call-back for game-engine related events such as level-loading
		private LevelEntity currentLevel;
		private Bitmap mBackgroundImage;
		private boolean restartLevel;
		//TODO: Add grid cells and scale images so that they're proportional to screen's resolution
		//Default screen's resolution is: 320x430 pixels.
		
		public GameThread(SurfaceHolder surfaceHolder, Context context) {
			// get handles to some important objects
            mSurfaceHolder = surfaceHolder;
            mContext = context;
            // load background image as a Bitmap instead of a Drawable b/c
            // we don't need to transform it and it's faster to draw this way
            Resources res = context.getResources();
            mBackgroundImage = BitmapFactory.decodeResource(res,
                    R.drawable.background);
            setRunning(false);
            restartLevel = false;
        }
		
		public boolean onTouch(View v, MotionEvent event) {
			if(currentLevel!=null){
				currentLevel.handleTouchEvent((int)event.getX(),(int)event.getY());
			}
			return false;
		}
		
		/* Callback invoked when the surface dimensions change. */
        public void setSurfaceSize(int width, int height) {
            // synchronized to make sure these all change atomically
            synchronized (mSurfaceHolder) {
                canvasWidth = width;
                canvasHeight = height;

                // don't forget to resize the background image
                /*mBackgroundImage = mBackgroundImage.createScaledBitmap(
                        mBackgroundImage, width, height, true);*/
            }
        }
		
		public void setRunning(boolean running) {
			this.running = running;
		}

		public boolean isRunning() {
			return running;
		}
		
		
		public void run() {
            while (isRunning()) {
            	if(gameEngineListener!=null){
            		if(restartLevel){
            			restartLevel = false;
            			if(currentLevel!=null){
            				gameEngineListener.moveToLevel(currentLevel.getLevelId());
            			}
            		}
            		currentLevel = gameEngineListener.loadNextLevel();
            		setupLevelScene();
            	}
            	if(currentLevel!=null){
            		while(isRunning()&&!restartLevel&&!currentLevel.isLevelFinished()){
            			Canvas c = null;
                        try {
                            c = mSurfaceHolder.lockCanvas(null);
                            synchronized (mSurfaceHolder) {
                            	currentLevel.updateLevel();
                            	drawGame(c);
                            }
                        } finally {
                            // do this in a finally so that if an exception is thrown
                            // during the above, we don't leave the Surface in an
                            // inconsistent state
                            if (c != null) {
                                mSurfaceHolder.unlockCanvasAndPost(c);
                            }
                        }
            		}
            	}
            }
        }
		
		public void restartLevel(){
			restartLevel = true;
		}
		
		/**
		 * Sets up the level scene by reading all the information out of the current level
		 * object and setting up the level objects accordingly.
		 */
		private void setupLevelScene(){
			Resources res = mContext.getResources();
			if(currentLevel!=null){
				for(GameEntity wall:currentLevel.getHorizontalWalls()){
		            // cache handles to our key sprites & other drawables
		            wall.setActiveImage(res.getDrawable(
		                    R.drawable.horizontalwall));
		            wall.setInactiveImage(res.getDrawable(R.drawable.inactive_horizontalwall));
				}
				for(GameEntity wall:currentLevel.getVerticalWalls()){
		            // cache handles to our key sprites & other drawables
		            wall.setActiveImage(res.getDrawable(
		                    R.drawable.verticalwall));
		            wall.setInactiveImage(res.getDrawable(R.drawable.inactive_verticalwall));
				}
				for(GameEntity ball:currentLevel.getBalls()){
		            // cache handles to our key sprites & other drawables
		            ball.setActiveImage(res.getDrawable(
		                    R.drawable.ball));
		            ball.setInactiveImage(res.getDrawable(R.drawable.inactive_ball));
				}
				for(GameEntity goal:currentLevel.getGoals()){
					goal.setActiveImage(res.getDrawable(R.drawable.goal));
					goal.setInactiveImage(res.getDrawable(R.drawable.inactive_goal));
				}
				for(GameEntity mySwitch:currentLevel.getSwitches()){
					mySwitch.setActiveImage(res.getDrawable(R.drawable.sweetch));
					mySwitch.setInactiveImage(res.getDrawable(R.drawable.inactive_sweetch));
				}
			}
		}
		
		private void drawGame(Canvas canvas){
			canvas.drawBitmap(mBackgroundImage, null, new Rect(0,0,canvasWidth,canvasHeight), null);
			canvas.save();
			for(GameEntity wall:currentLevel.getHorizontalWalls()){
				Drawable imageToDraw;
				if(wall.isActive())
					imageToDraw = wall.getActiveImage();
				else
					imageToDraw = wall.getInactiveImage();
				//Move collision box to current location of game entity
				wall.getCollisionBox().setBounds((int)wall.getxCoord(),(int)wall.getyCoord(),
						(int)(wall.getxCoord()+wall.getWidth()),(int)(wall.getyCoord()+wall.getHeight()));
				imageToDraw.setBounds((int)wall.getxCoord(),(int)wall.getyCoord(),
						(int)(wall.getxCoord()+wall.getWidth()),(int)(wall.getyCoord()+wall.getHeight()));
				imageToDraw.draw(canvas);
			}
			for(GameEntity wall:currentLevel.getVerticalWalls()){
				Drawable imageToDraw;
				if(wall.isActive())
					imageToDraw = wall.getActiveImage();
				else
					imageToDraw = wall.getInactiveImage();
				//Move collision box to current location of game entity
				wall.getCollisionBox().setBounds((int)wall.getxCoord(),(int)wall.getyCoord(),
						(int)(wall.getxCoord()+wall.getWidth()),(int)(wall.getyCoord()+wall.getHeight()));
				imageToDraw.setBounds((int)wall.getxCoord(),(int)wall.getyCoord(),
						(int)(wall.getxCoord()+wall.getWidth()),(int)(wall.getyCoord()+wall.getHeight()));
				imageToDraw.draw(canvas);
			}
			for(GameEntity goal:currentLevel.getGoals()){
				Drawable imageToDraw;
				if(goal.isActive())
					imageToDraw = goal.getActiveImage();
				else
					imageToDraw = goal.getInactiveImage();
				goal.getCollisionBox().setBounds((int)goal.getxCoord(),(int)goal.getyCoord(),
						(int)(goal.getxCoord()+goal.getWidth()),(int)(goal.getyCoord()+goal.getHeight()));
				imageToDraw.setBounds((int)goal.getxCoord(),(int)goal.getyCoord(),
						(int)(goal.getxCoord()+goal.getWidth()),(int)(goal.getyCoord()+goal.getHeight()));
				imageToDraw.draw(canvas);
			}
			for(GameEntity mySwitch:currentLevel.getSwitches()){
				Drawable imageToDraw;
				if(mySwitch.isActive())
					imageToDraw = mySwitch.getActiveImage();
				else
					imageToDraw = mySwitch.getInactiveImage();
				mySwitch.getCollisionBox().setBounds((int)mySwitch.getxCoord(),(int)mySwitch.getyCoord(),
						(int)(mySwitch.getxCoord()+mySwitch.getWidth()),(int)(mySwitch.getyCoord()+mySwitch.getHeight()));
				imageToDraw.setBounds((int)mySwitch.getxCoord(),(int)mySwitch.getyCoord(),
						(int)(mySwitch.getxCoord()+mySwitch.getWidth()),(int)(mySwitch.getyCoord()+mySwitch.getHeight()));
				imageToDraw.draw(canvas);
			}
			for(GameEntity ball:currentLevel.getBalls()){
				Drawable imageToDraw;
				if(ball.isActive())
					imageToDraw = ball.getActiveImage();
				else
					imageToDraw = ball.getInactiveImage();
				
				int ballX = (int)(ball.getxCoord()-ball.getWidth()/2);
				int ballY = (int)(ball.getyCoord()-ball.getHeight()/2);
				int ballX2 = (int)(ballX+ball.getWidth());
				int ballY2 = (int)(ballY+ball.getHeight());
				ball.getCollisionBox().setBounds(ballX,ballY,
						ballX2,ballY2);
				imageToDraw.setBounds(ballX,ballY,
						ballX2,ballY2);
				imageToDraw.draw(canvas);
			}
            canvas.restore();
		}

		public void setGameEngineListener(GameEngineListener gameEngineListener) {
			this.gameEngineListener = gameEngineListener;
		}
	}
	
	private static final String LOG_TAG = "TouchNGoView";
	
	private GameThread thread;
	private Context context;
	private boolean viewPaused;
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);

        // register our interest in hearing about changes to our surface
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        // create thread only; it's started in surfaceCreated()
        this.context = context;
        thread = new GameThread(holder, context);
        setViewPaused(false);

        setFocusable(true); // make sure we get key events
	}

	public GameThread getThread(){
		return thread;
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		thread.setSurfaceSize(width, height);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		startGame(holder);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		killThread();
	}
	
	private void startGame(SurfaceHolder holder){
		Log.v(LOG_TAG, "Thread state: " + thread.getState());
		
		if(!thread.getState().equals(Thread.State.NEW)){
			killThread();
			thread = new GameThread(holder,context);
		}
		if(thread.getState().equals(Thread.State.NEW)){
			thread.setRunning(true);
		    thread.start();
		}
	}
	
	public void killThread(){
		// we have to tell thread to shut down & wait for it to finish, or else
        // it might touch the Surface after we return and explode
        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
	}

	public void setViewPaused(boolean viewPaused) {
		this.viewPaused = viewPaused;
	}

	public boolean isViewPaused() {
		return viewPaused;
	}
}
