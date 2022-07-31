package com.blackshirts.movieshelf.util;

import com.sun.tools.javac.util.List;

import java.util.Arrays;

// Movie rank 관련 함수 가질 클래스
public class MovieRank {
    public double calculateRank(List<Integer> list) {
        int[] array = listToArray(list);
        double resultMovieRate = Arrays.stream(array).average().getAsDouble();
        return resultMovieRate;
    }

    public int[] listToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        int size = 0;
        for (int temp : list) {
            array[size++] = temp;
        }
        return array;
    }


}
