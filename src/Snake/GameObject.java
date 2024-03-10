/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import javafx.geometry.Point2D;
import javafx.scene.Node;

/**
 *
 * @author KHK
 */
public class GameObject {

    private Node object;
    private Point2D moving = new Point2D(0,0);
    private boolean alive = true;

    public GameObject(Node object) {
        this.object = object;
    }
    
    public boolean isAlive(){
        return this.alive;
    }
    public boolean isDead(){
        return !this.alive;
    }

    public void setAlive(boolean alive){
        this.alive = alive;
    }

    public Node getObject() {
        return object;
    }

    public void setObject(Node object) {
        this.object = object;
    }

    public Point2D getMoving() {
        return moving;
    }

    public void setMoving(Point2D moving) {
        this.moving = moving;
    }

    public void update(){
        object.setTranslateX(object.getTranslateX()+moving.getX());
        object.setTranslateY(object.getTranslateY()+moving.getY());
    }

    public double getrotate(){
        return object.getRotate();
    }

    public void rotateLeft(){
        object.setRotate(object.getRotate()-10);
        setMoving(new Point2D(Math.cos(Math.toRadians(getrotate())),(Math.sin(Math.toRadians(getrotate())))));
    }

    public void rotateRight(){
        object.setRotate(object.getRotate()+10);
        setMoving(new Point2D(Math.cos(Math.toRadians(getrotate())),(Math.sin(Math.toRadians(getrotate())))));
    }

    public void stopMoving(){
        setMoving(new Point2D(0,0));
    }

    public boolean isFighting(GameObject newObj){
        return getObject().getBoundsInParent().intersects(newObj.getObject().getBoundsInParent());
    }
}
