package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;

public class Node_graph {
    private double x;
    private double y;
    private int number;
    private int coord_x = -100;
    private int coord_y = -100;
    private double radius = 8.0f;
    private Circle node;
    private AnchorPane canvas;

    Node_graph(double x, double y, int number, AnchorPane canvas) {
        this.x = x;
        this.y = y;
        this.number = number;
        this.node = new Circle(this.x, this.y, this.radius);
        this.canvas = canvas;
        this.canvas.getChildren().add(this.node);
    }

    void addCoor() {
        Label label = new Label("(" + coord_x + ";" + coord_y + ")");
        label.setLayoutX(x - radius - 15.0);
        label.setLayoutY(y - radius - 15.0);
        canvas.getChildren().add(label);
    }

    void addNumver(){
        Label label = new Label(String.valueOf(number+1));
        label.setLayoutX(x - radius - 15.0);
        label.setLayoutY(y - radius - 15.0);
        canvas.getChildren().add(label);
    }

    Circle getNode() {
        return node;
    }

    boolean Entered(double x, double y) {
        return this.x + this.radius >= x && this.x - radius <= x && this.y + this.radius >= y && this.y - radius <= y;
    }

    void setCoord_x(int coord_x) {
        this.coord_x = coord_x;
    }

    void setCoord_y(int coord_y) {
        this.coord_y = coord_y;
    }

    int getCoord_x() {
        return coord_x;
    }

    public int getCoord_y() {
        return coord_y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    int getNumber() {
        return number;
    }
}
