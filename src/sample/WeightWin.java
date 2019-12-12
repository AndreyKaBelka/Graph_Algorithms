package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class WeightWin {

    @FXML
    private TextField weight_val;

    @FXML
    private Button oky_btn;

    public void initialize(){
        oky_btn.setOnMouseClicked(mouseEvent -> {
            try {
                if (weight_val.getText().trim().equals("")  || Integer.parseInt(weight_val.getText().trim()) < 0 ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setContentText("Вы ввели некорректный вес!");
                    alert.showAndWait();
                } else {
                    Controller.edgeGraphs.get(Controller.edgeGraphs.size() - 1).setWeight(Integer.parseInt(weight_val.getText().trim()));
                    Stage stage = (Stage) oky_btn.getScene().getWindow();
                    stage.close();
                }
            }
            catch (Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setContentText("Вы ввели Неккоректный вес!");
                alert.showAndWait();
            }
        });
    }
}
