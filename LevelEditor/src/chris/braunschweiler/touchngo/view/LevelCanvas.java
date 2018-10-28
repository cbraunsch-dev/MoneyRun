/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.view;

import chris.braunschweiler.touchngo.level.GameEntity;
import chris.braunschweiler.touchngo.level.LevelEntity;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author chrisbraunschweiler
 */
public class LevelCanvas extends JPanel implements MouseListener,
        MouseMotionListener, Runnable{

    private Thread canvasThread;
    private LevelEntity currentLevel;
    private boolean draggingObject;

    public LevelCanvas(){
        addMouseListener(this);
        addMouseMotionListener(this);
        start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        drawLevel(g2d);
    }

    private void drawLevel(Graphics2D g2d){
        if(getCurrentLevel()!=null){
            for(GameEntity horizontalWall:currentLevel.getHorizontalWalls()){
                g2d.setColor(Color.red);
                g2d.fillRect((int)horizontalWall.getxCoord(),
                        (int)horizontalWall.getyCoord(),
                        (int)horizontalWall.getWidth(),
                        (int)horizontalWall.getHeight());
            }
            for(GameEntity verticalWall:currentLevel.getVerticalWalls()){
                g2d.setColor(Color.blue);
                g2d.fillRect((int)verticalWall.getxCoord(),
                        (int)verticalWall.getyCoord(),
                        (int)verticalWall.getWidth(),
                        (int)verticalWall.getHeight());
            }
            for(GameEntity ball:currentLevel.getBalls()){
                g2d.setColor(Color.ORANGE);
                g2d.fillOval((int)ball.getxCoord(),
                        (int)ball.getyCoord(),
                        (int)ball.getWidth(),
                        (int)ball.getHeight());
            }
            for(GameEntity goal:currentLevel.getGoals()){
                g2d.setColor(Color.GREEN);
                g2d.fillRect((int)goal.getxCoord(),
                        (int)goal.getyCoord(),
                        (int)goal.getWidth(),
                        (int)goal.getHeight());
            }
            for(GameEntity theSwitch:currentLevel.getSwitches()){
                g2d.setColor(Color.YELLOW);
                g2d.fillRect((int)theSwitch.getxCoord(),
                        (int)theSwitch.getyCoord(),
                        (int)theSwitch.getWidth(),
                        (int)theSwitch.getHeight());
            }
        }
    }

    private void start(){
        canvasThread = new Thread(this);
        canvasThread.start();
    }

    private void stop(){
        canvasThread = null;
    }

    public void run(){
        Thread t = Thread.currentThread();
        while(t==canvasThread){
            try{
                Thread.sleep(20);;
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
            repaint();
        }
    }

    public void mouseClicked(MouseEvent e) {
        
    }

    public void mousePressed(MouseEvent e) {
        
    }

    public void mouseReleased(MouseEvent e) {
        if(currentLevel!=null){
            draggingObject = false;
            for(GameEntity entity:currentLevel.getHorizontalWalls()){
                entity.setBeingDragged(false);
            }
            for(GameEntity entity:currentLevel.getVerticalWalls()){
                entity.setBeingDragged(false);
            }
            for(GameEntity entity:currentLevel.getBalls()){
                entity.setBeingDragged(false);
            }
            for(GameEntity entity:currentLevel.getGoals()){
                entity.setBeingDragged(false);
            }
            for(GameEntity entity:currentLevel.getSwitches()){
                entity.setBeingDragged(false);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
        
    }

    public void mouseExited(MouseEvent e) {
        
    }

    public void mouseDragged(MouseEvent e) {
        if(currentLevel!=null){
            if(!draggingObject){
                checkForActiveEntities(currentLevel.getHorizontalWalls(),e.getX(),e.getY());
                checkForActiveEntities(currentLevel.getVerticalWalls(),e.getX(),e.getY());
                checkForActiveEntities(currentLevel.getBalls(),e.getX(),e.getY());
                checkForActiveEntities(currentLevel.getGoals(),e.getX(),e.getY());
                checkForActiveEntities(currentLevel.getSwitches(),e.getX(),e.getY());
            }
            moveActiveEntities(currentLevel.getHorizontalWalls(),e.getX(),e.getY());
            moveActiveEntities(currentLevel.getVerticalWalls(),e.getX(),e.getY());
            moveActiveEntities(currentLevel.getBalls(),e.getX(),e.getY());
            moveActiveEntities(currentLevel.getGoals(),e.getX(),e.getY());
            moveActiveEntities(currentLevel.getSwitches(),e.getX(),e.getY());
        }
    }

    public void mouseMoved(MouseEvent e) {
        
    }

    /**
     * @return the currentLevel
     */
    public LevelEntity getCurrentLevel() {
        return currentLevel;
    }

    /**
     * @param currentLevel the currentLevel to set
     */
    public void setCurrentLevel(LevelEntity currentLevel) {
        this.currentLevel = currentLevel;
        repaint();
    }

    private void checkForActiveEntities(List<GameEntity> entities, int x, int y){
        for(GameEntity entity: entities){
            if(!draggingObject){
                if(entity.getxCoord()<=x&&entity.getxCoord()+entity.getWidth()>=x&&
                        entity.getyCoord()<=y&&entity.getyCoord()+entity.getHeight()>=y){
                    //Mouse is within the entity
                    draggingObject = true;
                    entity.setBeingDragged(true);
                }
            }
        }
    }

    private void moveActiveEntities(List<GameEntity> entities, int x, int y){
        for(GameEntity entity:entities){
            if(entity.isBeingDragged()){
                //Move the entity
                entity.setxCoord(x);
                entity.setyCoord(y);
            }
        }
    }
}
