package com.blackshirts.movieshelf.util;

import java.util.Arrays;
import java.util.List;

// Movie rank 관련 함수 가질 클래스
public class MovieRankUtil {
    public static double calculateRank(List<Integer> list) {
        if(list.isEmpty()){
            return 0.0;
        }
        int[] array = listToArray(list);
        double resultMovieRate = Arrays.stream(array).average().getAsDouble();
        return resultMovieRate;
    }

    public static int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        int size = 0;
        for (int temp : list) {
            array[size++] = temp;
        }
        return array;
    }

}
