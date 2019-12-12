package sample;

import java.util.ArrayList;

class Deikstra {
    private ArrayList<ArrayList<Double>> matrix;

    Deikstra(ArrayList<ArrayList<Double>> matrix){
        this.matrix = matrix;
    }

    /**
     * Функция ищем минимальный путь для вершин 1-6
     * @return минимальный путь для 7 вершины
     */
    double min_way(){
        ArrayList<Integer> notused = new ArrayList<>();//Множество не использованных вершин
        for(int i = 1; i < matrix.size(); i++){
            notused.add(i);//добавляем в масисв неиспользованных вершин, вершины 1-11(0 - первая вершина)
        }
        ArrayList<Double> stroke = matrix.get(0);//берем пути для 1 вершины
        while(true){
            double min_weight = Double.POSITIVE_INFINITY;//инициализируем переменную, хранящую минимальный вес строки
            for(int i : notused){//для каждой неиспользованной вершины
                if (min_weight > stroke.get(i)){//ищем минимальный вес
                    min_weight = stroke.get(i);
                }
            }
            int index_min = stroke.indexOf(min_weight);//индекс вершины с минимальным весом
            notused.remove((Integer) index_min);//удаляем вершину из неиспользованных
            if (index_min == 6){//если найден минимальный путь из Ставрополя в Б. завершаем цикл
                break;
            } else{
                for(int i : notused){//для каждой неиспользованной вершины
                    stroke.set(i, Math.min(stroke.get(i), matrix.get(index_min).get(i) + min_weight));//выбираем минимальное значение предыдущего, либо значение из заданной матрицы + минимальный путь
                }
            }
        }
        return stroke.get(6);//возвращаем минимальный путь из 0 в 6 вершину
    }

}
