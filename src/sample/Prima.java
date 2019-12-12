package sample;

import java.util.ArrayList;

/**
 * Класс для хранения координат домиков
 */
class Coords {
    private double x;
    private double y;

    Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "{" + x + ";" + y + "}";
    }
}

/**
 * Класс для решения задачи методом Примы
 */
class Prima {
    private ArrayList<ArrayList<Double>> array_weight = new ArrayList<>();
    private ArrayList<Integer> used = new ArrayList<>();

    /**
     * @param coor1 коорлинаты 1 домика
     * @param coor2 координаты 2 домика
     * @return расстояние между ними, округленное до 2-х знаков после запятой
     */
    private double distance(Coords coor1, Coords coor2) {
        int xoff = (int) Math.abs(coor1.getX() - coor2.getX());
        int yoff = (int) Math.abs(coor1.getY() - coor2.getY());
        double v = Math.floor(Math.sqrt(xoff * xoff + yoff * yoff) * 100) / 100;
        return v == 0 ? Double.POSITIVE_INFINITY : v;
    }

    /**
     * Функция добавляет в массив used вершину и меняет массив ребер
     *
     * @param array_min_weight массив минимальных длин для каждой вершины
     * @param used             массив использованных вершин
     * @param minim            массив ребра(длина, вершина1, вершина2)
     */
    private void replace(ArrayList<double[]> array_min_weight, ArrayList<Integer> used, double[] minim) {
        used.add((int) minim[2]);
        array_weight.get((int) minim[1]).set((int) minim[2], Double.POSITIVE_INFINITY);
        array_weight.get((int) minim[2]).set((int) minim[1], Double.POSITIVE_INFINITY);
        array_min_weight.set((int) minim[1], min_weight(array_weight.get((int) minim[1])));
        array_min_weight.set((int) minim[2], min_weight(array_weight.get((int) minim[2])));
    }

    /**
     * функция ищет минимальное ребро для вершины
     *
     * @param arrayList строка матрицы длин
     * @return минимальное ребро для вершины
     */
    private double[] min_weight(ArrayList<Double> arrayList) {
        double min = Double.POSITIVE_INFINITY;//минимальная длина между выбранным домом и другими
        int index_house = 0;//счетчик домов
        int index1 = 0;
        for (double weight : arrayList) {
            if (min > weight) {
                min = weight;
                index1 = index_house;
            }
            index_house++;
        }
        return new double[]{min, index1};
    }

    /**
     * В конструкторе вычисяется расстояние между домиками, т.е. вес ребер графа.
     *
     * @param array_coords массив координат домиков
     */
    Prima(ArrayList<Coords> array_coords) {
        for (int i = 0; i < array_coords.size(); i++) {
            array_weight.add(new ArrayList<>());
        }

        for (int i = 0; i < array_coords.size(); i++) {
            for (Coords array_coord : array_coords) {
                array_weight.get(i).add(distance(array_coords.get(i), array_coord));
            }
            System.out.println(array_weight.get(i));
        }
    }

    /**
     * Функция вычисляет минимальную длину веревки
     *
     * @return минимальная длина веревки
     */
    double min_way() {
        ArrayList<double[]> array_min_weight = new ArrayList<>();
        double[] min_arr = new double[]{Double.POSITIVE_INFINITY, 0, 0};
        int ind = 0;
        for (ArrayList<Double> arrayList : array_weight) {
            double[] minimal = min_weight(arrayList);
            if (minimal[0] < min_arr[0]) {
                min_arr[0] = minimal[0];
                min_arr[1] = ind;
                min_arr[2] = minimal[1];
            }
            array_min_weight.add(minimal);
            ind++;
        }
        used.add((int) min_arr[1]);
        replace(array_min_weight, used, min_arr);
        int cnt = 2;//счетчик вершин в новом графе
        double minimal_weight = min_arr[0];
        /*
        В цикле ищем новое минимальное ребро, которое соединяет уже использованные вершины
        Как нашли, прибавляем ее длину к счетчику, и удаляем даненое ребро из массива (для 2х вершин)
        Повторяем до тех пор, пока кол-во вершин в массиве used не станет равно кол-ву заданных вершин
         */
        while (cnt < array_min_weight.size()) {
            double[] minim = new double[]{Double.POSITIVE_INFINITY, 0, 0};
            for (int index : used) {
                if (minim[0] > array_min_weight.get(index)[0]) {
                    minim[0] = array_min_weight.get(index)[0];
                    minim[1] = index;
                    minim[2] = array_min_weight.get(index)[1];
                }
            }
            replace(array_min_weight, used, minim);

            minimal_weight += minim[0];
            cnt++;
        }

        return minimal_weight;
    }

    ArrayList<Integer> getNodes(){
        return used;
    }
}