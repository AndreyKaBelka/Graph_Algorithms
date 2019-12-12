package sample;

import java.util.ArrayList;

class Ford_Bell {
    private ArrayList<ArrayList<Double>> matrix;
    private ArrayList<Double> matrix2 = new ArrayList<>();

    Ford_Bell(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;

        for (int n = 0; n < matrix.size(); n++) {
            matrix2.add(Double.POSITIVE_INFINITY);
        }

        matrix2.set(0, 0.0);
    }

    double min_way() {
        for (int k = 1; k < matrix.size(); k++) {
            for (int i = 0; i < matrix.size(); i++) {
                ArrayList<Double> stroke = matrix.get(i);
                for (int j = 0; j < stroke.size(); j++) {
                    if (matrix2.get(j) + matrix.get(j).get(i) < matrix2.get(i)){
                        matrix2.set(i, matrix2.get(j) + matrix.get(j).get(i));
                    }
                }
            }
        }

        return matrix2.get(6);
    }
}
