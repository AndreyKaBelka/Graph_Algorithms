package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.InputStream;
import java.util.ArrayList;

class Ground {
    private ArrayList<Cell> cells;
    private static ImageView monstr;
    private static Cell monstr_cell;
    private int cnt;
    private int start_x;
    private int start_y;

    Ground(int start_x, int start_y, int cnt, AnchorPane canvas) {
        cells = new ArrayList<>();
        Class<?> clazz = Ground.class;

        this.cnt = cnt;
        this.start_x = start_x;
        this.start_y = start_y;

        InputStream input = clazz.getResourceAsStream("/images/Yoda.png");

        Image image = new Image(input);
        monstr = new ImageView(image);
        monstr.setY(-100);
        monstr.setFitWidth(25.0f);
        monstr.setFitHeight(25.0f);
        for (double y = start_y; y < cnt * 30.0f + start_y; y += 30.0f) {
            for (double x = start_x; x < cnt * 30.0f + start_x; x += 30.0f) {
                cells.add(new Cell(x, y, canvas));
            }
        }
        canvas.getChildren().add(monstr);
    }

    static Cell getMonstr_cell() {
        return monstr_cell;
    }

    static void setMonstr(Cell parent) {
        monstr.setX(parent.getX() + 2);
        monstr.setY(parent.getY() + 2);
        monstr_cell = parent;
    }

    ArrayList<ArrayList<Integer>> getMatrix() throws Exception {
        boolean cantGet = false;

        ArrayList<ArrayList<Integer>> adj_matrix = new ArrayList<>();
        for (int i = 0; i < getCountUnBlocked() + 1; i++) {
            adj_matrix.add(new ArrayList<>());
            for (int j = 0; j < getCountUnBlocked() + 1; j++) {
                adj_matrix.get(i).add(-1);
            }
        }

        ArrayList<Integer> used_coords = new ArrayList<>();
        ArrayList<Integer> temp = new ArrayList<>();
        ArrayList<Integer> indexes = new ArrayList<>();
        Coords monstr_coords = new Coords((monstr_cell.getX() - start_x) / 30, (monstr_cell.getY() - start_y) / 30);
        indexes.add(getIndex(monstr_coords));
        used_coords.add(getIndex(monstr_coords));

        int iter = 0;
        int cnt_unblocked = getCountUnBlocked();

        if (isVerge((int) monstr_coords.getX(), (int) monstr_coords.getY()) || monstr_cell.isBlocked()) {
            throw new Exception("Неправильно установлен монстр, исправьте!");
        }

        while (used_coords.size() != cnt_unblocked) {
            for (int index : indexes) {
                int y = index / cnt;
                int x = index % cnt;
                Coords coords = new Coords(x, y);
                ArrayList<Coords> neighbours = new ArrayList<>();
                neighbours.add(new Coords(coords.getX() - 1, coords.getY()));
                neighbours.add(new Coords(coords.getX() + 1, coords.getY()));
                neighbours.add(new Coords(coords.getX(), coords.getY() - 1));
                neighbours.add(new Coords(coords.getX(), coords.getY() + 1));
                for (Coords coor : neighbours) {
                    if (coor.getX() >= 0 && coor.getY() >= 0 && coor.getX() < cnt && coor.getY() < cnt && !used_coords.contains(getIndex(coor))) {
                        if (!cells.get(getIndex(coor)).isBlocked()) {
                            iter++;
                            cells.get(getIndex(coor)).setNumber(iter);
                            cells.get(getIndex(coor)).setNumb();
                            adj_matrix.get(cells.get(index).getNumber()).set(cells.get(getIndex(coor)).getNumber(), 1);
                            adj_matrix.get(cells.get(getIndex(coor)).getNumber()).set(cells.get(index).getNumber(), 1);
                            used_coords.add(getIndex(coor));
                            temp.add(getIndex(coor));
                        }
                    }
                }
                if (isVerge((int) coords.getX(), (int) coords.getY())) {
                    adj_matrix.get(cells.get(index).getNumber()).set(adj_matrix.size() - 1, 1);
                }
            }
            indexes.clear();
            indexes.addAll(temp);
            temp.clear();
            if (indexes.isEmpty()) {
                cantGet = true;
                break;
            }
        }

        if (cantGet) {
            throw new Exception("0");
        }

        for (int i = 0; i < adj_matrix.size() - 1; i++) {
            adj_matrix.get(adj_matrix.size() - 1).set(i, adj_matrix.get(i).get(adj_matrix.get(i).size() - 1));
        }

        cells.forEach(Cell::resetNumber);
        monstr_cell.clearNumb();

        return adj_matrix;
    }

    private boolean isVerge(int x, int y) {
        return x == 0 || y == 0 || x == cnt - 1 || y == cnt - 1;
    }

    private int getIndex(Coords coor) {
        return (int) (coor.getY() * cnt + coor.getX());
    }

    private int getCountUnBlocked() {
        int[] cnt = new int[]{0};
        cells.forEach(cell -> {
            if (!cell.isBlocked()) {
                cnt[0]++;
            }
        });
        return cnt[0];
    }
}
