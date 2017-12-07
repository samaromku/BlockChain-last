package ru.savchenko.andrey.blockchain.services.exchange;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 12.11.2017.
 */

public class CodilitySecond {
    @Test
    public void findPairs() {
        System.out.println(solution(new int[]{3,5,6,3,3,5}));
    }

    public int solution(int[] A) {
        return getPairs(A);
    }

    private int getPairs(int[] array) {
        List<Integer>integers = new ArrayList<>();
        int counter = 0;
        for (int i = 0; i < array.length; i++) {
            if(integers.contains(array[i])){
                counter++;
            }
            integers.add(array[i]);
        }
        return counter;
    }


}
