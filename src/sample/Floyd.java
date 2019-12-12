package sample;

import java.util.ArrayList;

public class Floyd {
    private ArrayList<ArrayList<Double>> matrix;

    Floyd(ArrayList<ArrayList<Double>> matrix) {
        this.matrix = matrix;
    }

    double min_way() {
        ArrayList<ArrayList<Double>> temp = matrix;
        for (int k = 0; k < matrix.size(); k++) {
            for(int i =0;i< matrix.size(); i++){
                ArrayList<Double> stroke = matrix.get(i);
                for (int j = 0; j < stroke.size(); j++){
                    double min = Math.min(stroke.get(j), stroke.get(k)+ matrix.get(k).get(j));
                    temp.get(i).set(j, min);
                }
            }
            matrix = temp;
        }
        return matrix.get(0).get(6);
    }
}
