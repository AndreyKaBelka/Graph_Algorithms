package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

class MyRunnable implements Runnable {
    private AnchorPane coords;

    void setCoords(AnchorPane coords) {
        this.coords = coords;
    }

    @Override
    public void run() {
        coords.setOnMouseMoved(mouseEvent -> {
            if (Controller.isConnected) {
                Controller.edgeGraphs.get(Controller.edgeGraphs.size() - 1).setEnd(new Coords(mouseEvent.getX(), mouseEvent.getY()));
                Controller.edgeGraphs.get(Controller.edgeGraphs.size() - 1).updateLine();
                coords.requestLayout();
            }
        });
    }
}

public class Controller {

    @FXML
    private Pane topPane;

    @FXML
    private ImageView closeButton;

    @FXML
    private VBox main_menu;

    @FXML
    private Button ostov_btn;

    @FXML
    private Button short_path;

    @FXML
    private Button max_way_btn;

    @FXML
    private VBox menu_3;

    @FXML
    private Button prima_btnm;

    @FXML
    private Button krask_btn;

    @FXML
    private AnchorPane canvas_pane;

    @FXML
    private MenuBar menubar;

    @FXML
    private MenuItem kraskal_btn;

    @FXML
    private MenuItem prima_btn;

    @FXML
    private MenuItem back_menu;

    @FXML
    private AnchorPane falker_canv;

    @FXML
    private MenuItem falker_btn;

    @FXML
    private MenuItem back_btnmen;

    @FXML
    private VBox menu_2;

    @FXML
    private Button deikstra_btn;

    @FXML
    private Button ford_btn;

    @FXML
    private Button floyd_btn;

    @FXML
    private ImageView graph;

    @FXML
    private ImageView task;

    @FXML
    private Label answer;

    @FXML
    private ImageView back;

    @FXML
    private MenuBar falk_bar;

    private double xOffset;
    private double yOffset;

    private boolean isPrima = false;
    static boolean isConnected = false;

    private Stage stage = null;
    private int i = 0;

    static ArrayList<Node_graph> nodeGraphs = new ArrayList<>();
    static ArrayList<Edge_Graph> edgeGraphs = new ArrayList<>();

    private void setVis(boolean isp) {
        kraskal_btn.setVisible(!isp);
        prima_btn.setVisible(isp);
    }

    @FXML
    void initialize() throws IOException {
        //Считываем матрицу из файла
        Globals.setMatrix();
        ArrayList<Coords> coords = new ArrayList<>();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<ArrayList<Double>> matrix = new ArrayList<>();


        //Ниже приведен код для перетаскивания окна
        Platform.runLater(() -> topPane.getScene().addEventFilter(MouseEvent.MOUSE_ENTERED, event -> stage = (Stage) topPane.getScene().getWindow()));

        topPane.setOnMousePressed(event -> {
            xOffset = stage.getX() - event.getScreenX();
            yOffset = stage.getY() - event.getScreenY();
        });

        topPane.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });

        //Кнопка оставных деревьев
        ostov_btn.setOnMouseClicked(mouseEvent -> {
            main_menu.setVisible(false);
            menu_3.setVisible(true);
            back.setVisible(true);
        });

        //Вернуться назад
        back_menu.setOnAction(actionEvent -> {
            canvas_pane.setVisible(false);
            menu_3.setVisible(true);
            back.setVisible(true);
            clear_canv(canvas_pane, menubar);
            nodeGraphs.clear();
            edgeGraphs.clear();
        });

        prima_btnm.setOnMouseClicked(mouseEvent -> {
            canvas_pane.setVisible(true);
            menu_3.setVisible(false);
            back.setVisible(false);
            isPrima = true;
            setVis(true);
            i = 0;
        });

        krask_btn.setOnMouseClicked(mouseEvent -> {
            canvas_pane.setVisible(true);
            menu_3.setVisible(false);
            back.setVisible(false);
            isPrima = false;
            setVis(false);
            i = 0;
        });

        canvas_pane.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {//добавление вершины
                Node_graph node = new Node_graph(mouseEvent.getX(), mouseEvent.getY(), i, canvas_pane);
                nodeGraphs.add(node);
                if (!isPrima) {
                    nodeGraphs.get(nodeGraphs.size() - 1).addNumver();
                    i++;
                }
                if (isPrima) {
                    try {
                        Parent root = FXMLLoader.load(getClass().getResource("coords_win.fxml"));
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root, 228, 178));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (node.getCoord_x() == -100) {
                        canvas_pane.getChildren().remove(node.getNode());
                        nodeGraphs.remove(node);
                    } else {
                        i++;
                        nodeGraphs.get(nodeGraphs.size() - 1).addCoor();
                    }
                }
            } else if (mouseEvent.getButton() == MouseButton.SECONDARY && !isPrima && !isConnected) {
                for (Node_graph node : nodeGraphs) {
                    System.out.println(node.Entered(mouseEvent.getX(), mouseEvent.getY()));
                    if (node.Entered(mouseEvent.getX(), mouseEvent.getY())) {
                        isConnected = true;
                        edgeGraphs.add(new Edge_Graph(new Coords(mouseEvent.getX(), mouseEvent.getY()), node.getNumber(), canvas_pane));
                        MyRunnable myRunnable = new MyRunnable();
                        myRunnable.setCoords(canvas_pane);
                        Thread thread = new Thread(myRunnable);
                        thread.start();
                        break;
                    }
                }

            } else if (mouseEvent.getButton() == MouseButton.SECONDARY && !isPrima && isConnected) {
                for (Node_graph node : nodeGraphs) {
                    if (node.Entered(mouseEvent.getX(), mouseEvent.getY())) {
                        isConnected = false;
                        edgeGraphs.get(edgeGraphs.size() - 1).setEnd_node(node.getNumber());
                        edgeGraphs.get(edgeGraphs.size() - 1).setEnd(new Coords(node.getX(), node.getY()));
                        edgeGraphs.get(edgeGraphs.size() - 1).updateLine();
                        canvas_pane.requestLayout();
                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("weight_win.fxml"));
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root, 232, 111));
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.showAndWait();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (edgeGraphs.get(edgeGraphs.size() - 1).getWeight() == -2) {
                            canvas_pane.getChildren().remove(edgeGraphs.get(edgeGraphs.size() - 1).getEdge());
                            edgeGraphs.remove(edgeGraphs.get(edgeGraphs.size() - 1));
                        } else {
                            edgeGraphs.get(edgeGraphs.size() - 1).addWeight();
                        }
                    }
                }
                if (isConnected) {
                    isConnected = false;
                    canvas_pane.getChildren().remove(edgeGraphs.get(edgeGraphs.size() - 1).getEdge());
                    edgeGraphs.remove(edgeGraphs.size() - 1);
                }
            }
        });

        prima_btn.setOnAction(actionEvent -> {
            System.out.println(nodeGraphs.size());
            for (Node_graph nodeGraph : nodeGraphs) {
                coords.add(new Coords(nodeGraph.getCoord_x(), nodeGraph.getCoord_y()));
            }
            Prima prima = new Prima(coords);
            double answer = prima.min_way();
            ArrayList<Integer> nodes = prima.getNodes();
            int it = nodes.get(1);
            int previous = nodes.get(0);
            int iter = 1;
            System.out.println("asdasdasd" + nodes);
            while (iter < nodes.size()) {
                System.out.println("ads" + previous);
                System.out.println(it);
                System.out.println("Iter:" + iter);

                Coords coords_start_edge = new Coords(nodeGraphs.get(previous).getX(), nodeGraphs.get(previous).getY());
                Coords coords_end_edge = new Coords(nodeGraphs.get(it).getX(), nodeGraphs.get(it).getY());
                Edge_Graph newedge = new Edge_Graph(coords_start_edge, previous, canvas_pane);
                newedge.setEnd(coords_end_edge);
                newedge.setEnd_node(it);
                newedge.updateLine();
                iter++;
                previous = it;
                try {
                    it = nodes.get(iter);
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
            nodes.clear();
            coords.clear();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("" + answer);
            alert.setHeaderText("Ответ:");
            alert.setTitle("Прима");
            alert.showAndWait();
        });

        kraskal_btn.setOnAction(actionEvent -> {
            for (Edge_Graph edgeGraph : edgeGraphs) {
                edges.add(new Edge(edgeGraph.getStart_node()+1, edgeGraph.getEnd_node()+1, edgeGraph.getWeight()));
            }
            edges.sort(Edge::compareTo);
            Kraskal kraskal = new Kraskal(edges, i);
            ArrayList<Double> ans = kraskal.min_way();
            System.out.println(ans);
            for(int i1 = 0; i1 < ans.size()-2;i1+=2){
                System.out.println(ans.get(i1) + " " + ans.get(i1+1));
                Edge_Graph edge = getEdge(ans.get(i1)-1, ans.get(i1+1)-1);
                if (edge != null) {
                    edge.setColor();
                } else System.out.println("Не нашел");
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ответ");
            alert.setContentText("Максимальная длина кабеля:" + ans.get(ans.size()-2));
            alert.showAndWait();
        });

        //Кнопка кратчайшего пути
        short_path.setOnMouseClicked(mouseEvent -> {
            main_menu.setVisible(false);
            menu_2.setVisible(true);
            back.setVisible(true);
        });

        //Кнопка Дейкстры
        deikstra_btn.setOnMouseClicked(mouseEvent -> {
            menu_2.setVisible(false);
            task.setVisible(true);
            graph.setVisible(true);
            answer.setVisible(true);
            Deikstra deikstra = new Deikstra(Globals.getMatrix());
            answer.setText("Минимальный путь (0) -> (6) = " + deikstra.min_way());
        });

        //Кнопка Флойда
        floyd_btn.setOnMouseClicked(mouseEvent -> {
            menu_2.setVisible(false);
            task.setVisible(true);
            graph.setVisible(true);
            answer.setVisible(true);
            Floyd floyd = new Floyd(Globals.getMatrix());
            answer.setText("Минимальный путь (0) -> (6) = " + floyd.min_way());
        });

        //Кнопка Форда-Беллмана
        ford_btn.setOnMouseClicked(mouseEvent -> {
            menu_2.setVisible(false);
            task.setVisible(true);
            graph.setVisible(true);
            answer.setVisible(true);
            Ford_Bell fordBell = new Ford_Bell(Globals.getMatrix());
            answer.setText("Минимальный путь (0) -> (6) = " + fordBell.min_way());
        });

        Ground[] grounds = new Ground[1];

        max_way_btn.setOnMouseClicked(mouseEvent -> {
            falker_canv.setVisible(true);
            main_menu.setVisible(false);
            grounds[0] = new Ground(100, 100, 6, falker_canv);
        });

        falker_btn.setOnAction(actionEvent -> {
            try {
                ArrayList<ArrayList<Integer>> falk_matrix = grounds[0].getMatrix();
                Ford_Falkerson fordFalkerson = new Ford_Falkerson(falk_matrix, Ground.getMonstr_cell().getNumber(), falk_matrix.size() - 1);
                System.out.println(fordFalkerson.max_pot());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });

        back_btnmen.setOnAction(actionEvent -> {
            main_menu.setVisible(true);
            falker_canv.setVisible(false);
            clear_canv(falker_canv, falk_bar);
        });

        //кнопка назад
        back.setOnMouseClicked(mouseEvent -> {
            if (menu_2.isVisible()) {
                menu_2.setVisible(false);
                main_menu.setVisible(true);
                back.setVisible(false);
            } else if (graph.isVisible()) {
                menu_2.setVisible(true);
                task.setVisible(false);
                graph.setVisible(false);
                answer.setVisible(false);
                answer.setText("");
            } else if (menu_3.isVisible()) {
                menu_3.setVisible(false);
                main_menu.setVisible(true);
                back.setVisible(false);
            }
        });

        //Кнопка закрытия приложения
        closeButton.setOnMouseClicked(mouseEvent -> System.exit(0));
    }

    private void clear_canv(AnchorPane canvas, MenuBar menubar) {
        int cnt = canvas.getChildren().size();
        int k = 0;
        while (cnt != 1) {
            if (!(canvas.getChildren().get(k).idProperty() == menubar.idProperty())) {
                canvas.getChildren().remove(canvas.getChildren().get(k));

                cnt -= 1;
            } else {
                k++;
            }
        }
    }

    private Edge_Graph getEdge(Double start_node, Double end_node){
        for(Edge_Graph edgeGraph:edgeGraphs){
            if (edgeGraph.getStart_node() == start_node && edgeGraph.getEnd_node() == end_node){
                return edgeGraph;
            }
        }
        return null;
    }
}
