package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

class Edge_Graph {
    private Coords start;
    private Coords end;
    private int start_node;
    private int end_node;
    private Line edge;
    private int weight = -2;
    private AnchorPane canvas;

    Edge_Graph(Coords start,int start_node, AnchorPane canvas){
        this.start = start;
        this.start_node = start_node;
        this.edge = new Line(start.getX(), start.getY(), start.getX(), start.getY());
        this.canvas = canvas;
        this.canvas.getChildren().add(edge);
    }

    Line getEdge() {
        return edge;
    }

    void addWeight() {
        Label label = new Label(String.valueOf(weight));
        double med_x = Math.abs(start.getX()-end.getX())/2;
        double med_y = Math.abs(start.getY()-end.getY())/2;
        label.setLayoutX(Math.min(start.getX(), end.getX()) + med_x);
        label.setLayoutY(Math.max(start.getY(), end.getY()) - med_y + 10.0);
        canvas.getChildren().add(label);
    }

    void setWeight(int weight) {
        this.weight = weight;
    }

    int getWeight() {
        return weight;
    }

    int getStart_node() {
        return start_node;
    }

    int getEnd_node() {
        return end_node;
    }

    void updateLine(){
        this.edge.setEndX(end.getX());
        this.edge.setEndY(end.getY());
    }

    void setColor(){
        edge.setStroke(Color.ORANGE);
        edge.setStrokeWidth(2.0f);
        canvas.requestLayout();
    }

    void setEnd_node(int end_node) {
        this.end_node = end_node;
    }

    void setEnd(Coords end) {
        this.end = end;
    }
}
