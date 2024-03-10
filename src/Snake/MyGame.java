/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Snake;

import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author KHK
 */
public class MyGame extends Application {

    private Pane root;
    private GameObject mover;
    List<GameObject> Foodies = new ArrayList<>();
    List<GameObject> Stones = new ArrayList<>();

    public Parent initialize() {
        root = new Pane();
        root.setPrefSize(1200, 800);
        mover = new Player();
        mover.setMoving(new Point2D(0, 0));
        addgameObject(mover, 300, 300);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                OnUpdate();
            }
        };
        timer.start();
        return root;
    }

    /**
     * **
     */

    private void OnUpdate() {
        for (GameObject stone : Stones) {
            for (GameObject foods : Foodies) {
                if (stone.isFighting(foods)) {
                    stone.setAlive(false);
                    foods.setAlive(false);
                    root.getChildren().removeAll(stone.getObject(),foods.getObject());
                }
            }
        }
        Stones.removeIf(GameObject::isDead);
        Foodies.removeIf(GameObject::isDead);

        Stones.forEach(GameObject::update);
//        Foodies.forEach(GameObject::update);

        mover.update();
        if (Math.random() < 0.009) {
            addFoods(new food(), Math.random() * root.getPrefWidth(), Math.random() * root.getPrefHeight());
        }
    }

    /**
     * **
     */
    private void addgameObject(GameObject obj, double x, double y) {
        obj.getObject().setTranslateX(x);
        obj.getObject().setTranslateY(y);
        root.getChildren().add(obj.getObject());
    }

    private void addFoods(GameObject Foods, double x, double y) {
        Foodies.add(Foods);
        addgameObject(Foods, x, y);
    }

    private void addStone(GameObject Stone, double x, double y) {
        Stones.add(Stone);
        addgameObject(Stone, x, y);
    }

    /**
     * **
     */
    private static class Player extends GameObject {

        Player() {
            super(new Rectangle(40, 20, Color.RED));
        }
    }

    private static class food extends GameObject {

        food() {
            super(new Circle(15, 15, 10, Color.BLUE));
        }
    }

    private static class Stone extends GameObject {

        Stone() {
            super(new Circle(5, 5, 5, Color.GREEN));
        }
    }

    /**
     * **
     */
    @Override
    public void start(Stage stage) {
        stage.setScene(new Scene(initialize()));
        stage.getScene().setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.LEFT) {
                mover.rotateLeft();
            } else if (e.getCode() == KeyCode.RIGHT) {
                mover.rotateRight();
            } else if (e.getCode() == KeyCode.DOWN) {
                mover.stopMoving();
            }else if (e.getCode() == KeyCode.UP) {
                Stone stone = new Stone();
                stone.setMoving(mover.getMoving().normalize().multiply(4));
                addStone(stone,mover.getObject().getTranslateX(),mover.getObject().getTranslateY());
            }
        });
        stage.setTitle("Moving Game");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
