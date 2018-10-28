/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chris.braunschweiler.touchngo.level;

/**
 *
 * @author chrisbraunschweiler
 */
public class GameEntity {

    private String id;
    private double xCoord;
    private double yCoord;
    private double width;
    private double height;
    private boolean active;
    private boolean beingDragged;

    public GameEntity(String id, double xCoord, double yCoord, double width,
            double height, boolean active){
        this.id = id;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.width = width;
        this.height = height;
        this.active = active;
        this.beingDragged = false;
    }

    /**
     * @return the xCoord
     */
    public double getxCoord() {
        return xCoord;
    }

    /**
     * @param xCoord the xCoord to set
     */
    public void setxCoord(double xCoord) {
        this.xCoord = xCoord;
    }

    /**
     * @return the yCoord
     */
    public double getyCoord() {
        return yCoord;
    }

    /**
     * @param yCoord the yCoord to set
     */
    public void setyCoord(double yCoord) {
        this.yCoord = yCoord;
    }

    /**
     * @return the width
     */
    public double getWidth() {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(double width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public double getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * @return the active
     */
    public boolean isActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the beingDragged
     */
    public boolean isBeingDragged() {
        return beingDragged;
    }

    /**
     * @param beingDragged the beingDragged to set
     */
    public void setBeingDragged(boolean beingDragged) {
        this.beingDragged = beingDragged;
    }
}
