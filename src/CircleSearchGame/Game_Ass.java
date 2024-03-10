package CircleSearchGame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author KHK
 */
public class Game_Ass extends Application{
    
    private Scene scene;
    private AnimationTimer timer;
    private Button easy,medium,hard;
    private final double SX = Screen.getPrimary().getBounds().getMaxX();
    private final double SY = Screen.getPrimary().getBounds().getMaxY();
    private Pane PV = new Pane();
    private Pane HV = new Pane();
    private final ObservableList<Circle> Hide = FXCollections.observableArrayList();
    private int lev = 1 ,score = 0 , size = 20,circle = lev+1,maxlev = 0,Count;
    private OShape search;
    private Label lbscore = new Label(),lbcircle = new Label(),lblevel = new Label();

    private Parent ProcessView(){
        PV =new Pane();
        HBox H = new HBox();
        H.getChildren().addAll(lblevel,lbscore,lbcircle);
        H.setTranslateX(SX/2.2);
        H.setPadding(new Insets(5));
        H.setSpacing(20);
        PV.getChildren().addAll(search,H);
        PV.getChildren().addAll(Hide);
        search.MoveWithMouse();
        System.out.println("Search\n"+search+"\nHide");
        for(int i = 0;i < Hide.size();i++){
        System.out.println(Hide.get(i));
        }
        return PV;
    }
    private Parent HomeView(){
        HV = new Pane();
        this.easy = new Button("Easy");
        this.medium = new Button("Medium");
        this.hard = new Button("Hard");
        easy.setMinWidth(150);
        medium.setMinWidth(150);
        hard.setMinWidth(150);
        VBox vbox = new VBox();
        vbox.getChildren().addAll(this.easy,this.medium,this.hard);
        vbox.setTranslateX(SX/2.1);
        vbox.setTranslateY(SY/4);
        vbox.setSpacing(50);
        HV.getChildren().add(vbox);
        vbox.setAlignment(Pos.CENTER);
        return HV;
    }
    private void process(){
        AddCircle();
        check();
    }
    private void check(){ 
        this.Count = this.circle;
        this.timer = new AnimationTimer(){
            
            @Override
            public void handle(long now) {
                
                for(int i = 0 ; i < Hide.size();i++){
                     if(search.getBoundsInParent().contains(Hide.get(i).getBoundsInParent()) && Hide.get(i).getFill().equals(Color.WHITESMOKE)){
                        Hide.get(i).setFill(Color.BLUE);
                        lbscore.setText("Score: "+(score+=10));
                        Count--;
                        lbcircle.setText("Circle: "+(Count));
                    }
                }
                if(lev == maxlev+1){
                    System.out.println("End");
                    complete();
                }
                if(Count == 0){
                     System.out.println("Complete Level"+lev+"\n======================================\n");
                     updatelevel();
                 }
            }
        };  
        timer.start();
    }
    private void updatelevel(){
        this.timer.stop();
        this.lev+=1;
        this.circle+=1;
        if(this.lev > 20 && this.size > 30){
            this.size -= 10;
            this.circle+=3;
        }else if(this.lev > 40 && this.size < 30){
            this.size -= 15;
            this.circle+=2;
        }
        process();
        this.scene.setRoot(ProcessView());
    }
    private void complete(){
        this.timer.stop();
        PV =new Pane();
        Font F1 = Font.font("Brush Script MT",FontWeight.BOLD, FontPosture.REGULAR, 150);
        Text T1 = new Text();
        T1.setText("Congratulation\nYour Score : "+score+"\nComing Soon......");
        T1.setTextAlignment(TextAlignment.JUSTIFY);
        T1.setTranslateX(SX/3);
        T1.setTranslateY(SY/2);
        T1.setFont(F1);
        PV.getChildren().add(T1);
        scene.setRoot(PV);
    }
    private void RandomPlace(){
        double X =  Math.random() * SX;
        double Y =  Math.random() * SY;
        while( X  > SX-50){
            X -= 100;
        }
        while( Y  > SY-80){
            Y -= 100;
        }
        X = (int) X;
        Y = (int) Y;
        if(X%5!=0 || Y%5!=0){
            X -= X%5;
            Y -= Y%5;
        }
        if(X < (size*2)){
            X += size*2;
        }
        if(Y < (size*2)){
            Y += size*2;
        }
        Hide.add(new Circle(X,Y,size,Color.WHITESMOKE));
    }

    private void AddCircle(){
        this.Hide.clear();
        for(int i = 0; i < circle ; i++){
            RandomPlace();
        }
        search = new OShape(300,300,size+3,Color.RED);
        lblevel.setText("Level : "+lev);
        lbcircle.setText("Circle : "+circle);
        lbscore.setText("Score: "+score);
    }

    private void Level(){
        this.easy.setOnAction(e->{
            this.circle = lev+1;
            this.size = 50;
            this.maxlev = 60;
            process();
            this.scene.setRoot(ProcessView()); 
        });
        this.medium.setOnAction(e->{
            this.circle = lev+3;
            this.size = 30;
            this.maxlev = 40;
            process();
            this.scene.setRoot(ProcessView()); 
        });
        this.hard.setOnAction(e->{ 
            this.circle = lev+5;
            this.size = 15;
            this.maxlev = 20;
            process();
            this.scene.setRoot(ProcessView()); 
        });
    }
    @Override
    public void start(Stage stage) {   
        this.scene = new Scene(HomeView(),900,900); 
        Level();
        scene.setOnKeyPressed(e->{ 
            switch(e.getCode()){
                case A:
                    search.MoveLeft();break;
                case D:
                    search.MoveRight();break;
                case S:
                    search.MoveDown();break;
                case W:
                    search.MoveUp();break;
            }
        });
        stage.setTitle("Game");
        stage.setScene(this.scene);
//        stage.setMaximized(true);
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[]args){
        launch(args);
    }
}

class OShape extends Circle{
    private double orgX,orgY;

    public OShape(double x,double y,double r,Color color) {
        super(x,y,r,color);
    }

    public void MoveUp(){
        setTranslateY(getTranslateY()-5);
    }
    
    public void MoveDown(){
        setTranslateY(getTranslateY()+5);
    }

    public void MoveLeft(){
        setTranslateX(getTranslateX()-5);
    }

    public void MoveRight(){
        setTranslateX(getTranslateX()+5);
    }

    public void MoveWithMouse(){
        setCursor(Cursor.HAND);
        setOnMousePressed(e->{  
            orgX = e.getSceneX();
            orgY = e.getSceneY();
        });
        setOnMouseDragged(e->{
            double newX = e.getSceneX() - orgX;
            double newY = e.getSceneY() - orgY;
            Circle c = (Circle) e.getSource();
            c.setCenterX(c.getCenterX()+newX);
            c.setCenterY(c.getCenterY()+newY);
            orgX = e.getSceneX();
            orgY = e.getSceneY();
        });
    }
}