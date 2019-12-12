package sample;

import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class Cell {
    private double x;
    private double y;
    private AnchorPane canvas;
    private double width = 30.0f;
    private Rectangle rect;
    private Color color = Color.WHITE;
    private boolean isBlocked = false;
    private int number = 0;
    private Label numb;

    Cell(double x, double y, AnchorPane canvas) {
        this.canvas = canvas;
        this.x = x;
        this.y = y;
        this.rect = new Rectangle(this.x, this.y, this.width, this.width);
        rect.setFill(this.color);
        rect.setStroke(Color.BLACK);
        this.canvas.getChildren().add(rect);
        rect.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                isBlocked = !isBlocked;
                setBlocked();
            }
            else if (mouseEvent.getButton() == MouseButton.SECONDARY){
                Ground.setMonstr(this);
            }
        });

        numb = new Label();
        numb.setLayoutX(this.x+15.0);
        numb.setLayoutY(this.y+15.0);
        numb.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                isBlocked = !isBlocked;
                setBlocked();
            }
            else if (mouseEvent.getButton() == MouseButton.SECONDARY){
                Ground.setMonstr(this);
            }
        });
        canvas.getChildren().add(numb);
    }

    double getY() {
        return y;
    }

    double getX() {
        return x;
    }

    void setNumber(int number) {
        this.number = number;
    }

    int getNumber() {
        return number;
    }

    boolean isBlocked() {
        return isBlocked;
    }

    void setNumb() {
        this.numb.setText(String.valueOf(number));
    }

    void clearNumb(){
        this.numb.setText("");
    }

    void resetNumber(){
        number = 0;
    }

    private void setBlocked() {
        if (isBlocked) {
            rect.setFill(Color.GRAY);
        } else {
            rect.setFill(this.color);
        }
        clearNumb();
        canvas.requestLayout();
    }
}
