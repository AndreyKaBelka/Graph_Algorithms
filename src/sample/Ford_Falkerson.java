package sample;

import java.util.ArrayList;
import java.util.Arrays;

class Ford_Falkerson {
    private ArrayList<Node> matrix;
    private int istok;
    private int stok;

    Ford_Falkerson(ArrayList<ArrayList<Integer>> matrix, int istok, int stok) {
        this.matrix = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = 0; j < matrix.get(i).size(); j++) {
                if (j < i) {
                    if (matrix.get(i).get(j) != -1) {
                        matrix.get(i).set(j, 0);
                    }
                }
            }
        }
//
//        for (ArrayList<Integer> row : matrix) {
//            for (Integer num : row) {
//                System.out.print(num + ",");
//            }
//            System.out.print('\n');
//        }

        for (ArrayList<Integer> node : matrix) {
            this.matrix.add(new Node(node));
        }

        this.istok = istok;
        this.stok = stok;
    }

    private void change(int i, int j, double f) {
        Node edge1 = matrix.get(i);
        Node edge2 = matrix.get(j);
        if (i < j) {
            edge1.setWeight((int) (edge1.getWeight(j) - f), j);
            edge2.setWeight((int) (edge2.getWeight(i) + f), i);
        } else {
            edge1.setWeight((int) (edge1.getWeight(j) + f), j);
            edge2.setWeight((int) (edge2.getWeight(i) - f), i);
        }
    }

    double max_pot() {
        ArrayList<Integer> way = new ArrayList<>();
        boolean cont = true;
        double max_i = 0;
        while (cont) {
            way.add(istok);
            matrix.get(istok).setMark(new Double[]{Double.POSITIVE_INFINITY, -1.0});
            int it = istok;
            int previous = istok;
            double min = Double.POSITIVE_INFINITY;
            while (it != stok) {
                double[] max = matrix.get(it).max(matrix);
                if (max[0] > 0) {
                    matrix.get((int) max[1]).setMark(new Double[]{max[0], (double) it});
                    previous = it;
                    it = (int) max[1];
                    way.add((int) max[1]);
                    if (min > max[0]) {
                        min = max[0];
                    }
                    if (matrix.get(it).getWeight(stok) == 1) {
                        it = stok;
                    }
                } else if (it == istok) {
                    cont = false;
                    break;
                } else {
                    matrix.get(it).setMark(new Double[]{-1.0, -1.0});
                    way.remove(way.size() - 1);
                    it = previous;
                    if (way.size() > 1) previous = way.get(way.size() - 2);
                    else previous = way.get(way.size() - 1);
                }
            }
            if (cont) {
                for (int i = 0; i < way.size() - 1; i++) {
                    int v1 = way.get(i);
                    int v2 = way.get(i + 1);
                    change(v1, v2, min);
                }
                max_i += min;

                System.out.println(way);
                way.clear();

                for (Node node : matrix) {
                    node.setMark(new Double[]{0.0, 0.0});
                }
            }
        }
        return max_i;
    }
}

class Node {
    private Double[] mark;
    private ArrayList<Integer> out;

    Node(ArrayList<Integer> out) {
        this.mark = new Double[]{0.0, 0.0};
        this.out = out;
    }

    void setMark(Double[] mark) {
        this.mark = mark;
    }

    private boolean notMarked() {
        return Arrays.equals(mark, new Double[]{0.0, 0.0});
    }

    void setWeight(Integer d, int i) {
        out.set(i, d);
    }

    double getWeight(int i) {
        return out.get(i);
    }

    double[] max(ArrayList<Node> nodes) {
        double max = 0;
        int iter = 0;
        int index_max = 0;
        for (Integer weight : out) {
            if (nodes.get(iter).notMarked()) {
                if (max < weight) {
                    index_max = iter;
                    max = weight;
                }
            }
            iter++;
        }

        return new double[]{max, (double) index_max};
    }

}
