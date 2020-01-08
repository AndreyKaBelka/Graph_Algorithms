package sample;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Globals {
    private static ArrayList<ArrayList<Double>> matrix = new ArrayList<>();

    static void setMatrix() throws IOException {
        FileReader fr = new FileReader("matrix.txt");
        Scanner scan = new Scanner(fr);
        while (scan.hasNextLine()) {
            ArrayList<Double> arr = new ArrayList<>();
            String[] line = scan.nextLine().split(",");
            for (String weight : line) {
                if (weight.trim().equals("0")) {
                    arr.add(Double.POSITIVE_INFINITY);
                } else {
                    arr.add(Double.parseDouble(weight.trim()));
                }
            }
            matrix.add(arr);
        }

        fr.close();
    }

    static ArrayList<ArrayList<Double>> getMatrix() {
        return matrix;
    }
}
