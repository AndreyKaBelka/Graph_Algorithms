package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class CoordsWin {

    @FXML
    private TextField xCoords;

    @FXML
    private TextField yCoords;

    @FXML
    private Button enter_btn;

    public void initialize() {
        enter_btn.setOnMouseClicked(mouseEvent -> {
            try {
                if (yCoords.getText().trim().equals("") || xCoords.getText().trim().equals("") || Integer.parseInt(xCoords.getText().trim()) < 0 || Integer.parseInt(yCoords.getText().trim()) < 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setContentText("Вы ввели некорректные координаты!");
                    alert.showAndWait();
                } else {
                    Controller.nodeGraphs.get(Controller.nodeGraphs.size() - 1).setCoord_x(Integer.parseInt(xCoords.getText().trim()));
                    Controller.nodeGraphs.get(Controller.nodeGraphs.size() - 1).setCoord_y(Integer.parseInt(yCoords.getText().trim()));
                    Stage stage = (Stage) enter_btn.getScene().getWindow();
                    stage.close();
                }
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setContentText("Вы ввели некорректные координаты!");
                alert.showAndWait();
            }
        });
    }
}
