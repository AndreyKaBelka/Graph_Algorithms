package sample;

import java.util.ArrayList;

class Kraskal {
    private ArrayList<Edge> edges;
    private int count_node = 0;

    Kraskal(ArrayList<Edge> edges, int node) {
        this.edges = edges;
        edges.forEach(edge -> System.out.println(edge.toString()));
        count_node = node;
    }

    ArrayList<Double> min_way() {
        double max_w = 0;//максимальный вес ребра
        double count = 0;//кол-во использованных ребер
        ArrayList<Double> mass = new ArrayList<>();
        Components components = new Components(count_node);
        for (Edge edge : edges) {//делаем алгоритм для каждого ребра
            if (!components.comp(edge)) {//если вершины в разных компонентах
                components.unite(edge);//объединяем вершины в 1 компоненту
                mass.add((double) edge.getU());
                mass.add((double) edge.getV());
                count++;
                if (edge.getW() > max_w) {
                    max_w = edge.getW();
                }
            }
        }
        mass.add(max_w);
        mass.add(count);
        return mass;
    }

}

/**
 * Класс ребра
 */
class Edge {
    private int v;
    private int u;
    private double w;

    Edge(int u, int v, double w) {
        this.v = v;
        this.u = u;
        this.w = w;
    }

    double getW() {
        return w;
    }

    int getV() {
        return v;
    }

    int getU() {
        return u;
    }

    /**
     * Функция нужная для сортировки массива ребер
     *
     * @param edge ребро с которым нужна сравнить данное ребро
     * @return -1 если вес данного ребра меньше введенного, 1 - если наоборот и 0 - если веса равны
     */
    int compareTo(Edge edge) {
        if (w != edge.w) return w < edge.w ? -1 : 1;
        return 0;
    }

    @Override
    public String toString() {
        return "{" + v + ";" + u + ";w=" + w + "}\n";
    }
}

class Components {
    private ArrayList<Integer> array_comp = new ArrayList<>();

    Components(int size) {
        for (int i = 0; i < size; i++) {
            array_comp.add(i);
        }
    }

    boolean comp(Edge edge) {
        return (array_comp.get(edge.getV() - 1).equals(array_comp.get(edge.getU() - 1)));
    }

    void unite(Edge edge) {
        if (array_comp.get(edge.getV() - 1) < array_comp.get(edge.getU() - 1)) {
            Integer prev = array_comp.get(edge.getU() - 1);
            array_comp.set(edge.getU() - 1, array_comp.get(edge.getV() - 1));
            for (int i = 0; i < array_comp.size(); i++) {
                int val = array_comp.get(i);
                if (val == (prev)){
                    array_comp.set(i, array_comp.get(edge.getV() - 1));
                }
            }
        } else {
            Integer prev = array_comp.get(edge.getV() - 1);
            array_comp.set(edge.getV() - 1, array_comp.get(edge.getU() - 1));
            for (int i = 0; i < array_comp.size(); i++) {
                int val = array_comp.get(i);
                if (val == (prev)){
                    array_comp.set(i, array_comp.get(edge.getU() - 1));
                }
            }
        }
    }
}
