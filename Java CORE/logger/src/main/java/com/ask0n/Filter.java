package com.ask0n;

import java.util.ArrayList;
import java.util.List;

public class Filter {
    private int threshold;
    private final Logger logger = Logger.getInstance();

    public Filter(int threshold) {
        this.threshold = threshold;
    }

    public List<Integer> filterOut(List<Integer> source) {
        List<Integer> result = new ArrayList<>();
        for (int num : source)
            if (num < threshold) logger.log("Элемент \"" + num + "\" не проходит ");
            else result.add(num);
        logger.log("Прошло фильтр " + result.size() + " элемента из " + source.size());
        return result;
    }
}
