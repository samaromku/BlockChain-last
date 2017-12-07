package ru.savchenko.andrey.blockchain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrey on 12.11.2017.
 */

public class Codility {
    @Test
    public void getBiggestInteger() {
        System.out.println(solution(new int[]{-1, -3}));
    }

    public int solution(int[] A) {
        // write your code in Java SE 8
        List<Integer> positive = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            if (A[i] > 0) {
                positive.add(A[i]);
            }
        }
        List<Integer> allNumbers = new ArrayList<>();
        for (int i = 1; i < positive.size() + 1; i++) {
            allNumbers.add(i);
        }
        for (Integer integer : allNumbers) {
            if (!positive.contains(integer)) {
                return integer;
            }
        }
        if (allNumbers.isEmpty()) {
            return 1;
        }
        return allNumbers.get(allNumbers.size() - 1) + 1;
    }

    @Test
    public void battleShip() {
        System.out.println(solution(12, "1B 2C, 2D 4D", "2B 2D 3D 4D 4A"));
        System.out.println(solution(12, "1A 2A, 12A 12A", "12A"));
    }

    private char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toUpperCase().toCharArray();

    int getIntByChar(char letter) {
        char upperLetter = Character.toUpperCase(letter);
        for (int i = 0; i < alphabet.length; i++) {
            if (upperLetter == alphabet[i]) {
                return i;
            }
        }
        return 0;
    }

    public String solution(int N, String S, String T) {
        String[] ships = S.split(",");
        String[] shouts = T.split(" ");
        List<Ship> shipsList = new ArrayList<>();
        List<Shout> shoutsList = new ArrayList<>();
        for (int i = 0; i < ships.length; i++) {
            shipsList.add(shipCoordes(ships[i].trim()));
        }
        for (int i = 0; i < shouts.length; i++) {
            shoutsList.add(shoutCoordes(shouts[i]));
        }
        return checkShouts(shipsList, shoutsList);
    }

    private String checkShouts(List<Ship> ships, List<Shout> shouts) {
        for (int shoutsCount = 0; shoutsCount < shouts.size(); shoutsCount++) {
            for (int shipsCount = 0; shipsCount < ships.size(); shipsCount++) {
                Shout shout = shouts.get(shoutsCount);
                Ship ship = ships.get(shipsCount);
                if ((ship.startRow <= shout.row)
                        && (ship.endRow >= shout.row)
                        && (ship.startColumn <= shout.column)
                        && (ship.endColumn >= shout.column)) {

                    ship.shouts = ship.shouts + 1;
                }
            }
        }
        int shouted = 0;
        int hit = 0;
        for (int i = 0; i < ships.size(); i++) {
            Ship ship = ships.get(i);
            if (ship.shouts == ship.count) {
                shouted = 1;
            } else if (ship.shouts != 0) {
                hit = 1;
            }
        }
        return hit + "," + shouted;
    }

    private Shout shoutCoordes(String shoutString) {
        char[] parts = shoutString.toCharArray();
        int x = Character.getNumericValue(parts[0]);
        int y = getIntByChar(parts[1]);
        return new Shout(x, y);
    }

    private Ship shipCoordes(String ship) {
        String[] corners = ship.split(" ");
        char[] leftTop = corners[0].toCharArray();
        String numbers = corners[0].replaceAll("\\D+","");
//        int startRow = Character.getNumericValue(leftTop[0]);
        int startRow = Integer.valueOf(numbers);
        int startColumn = getIntByChar(leftTop[leftTop.length-1]);

        char[] rightBottom = corners[1].toCharArray();
        String numbersEnd = corners[1].replaceAll("\\D+","");
//        int endRow = Character.getNumericValue(rightBottom[0]);
        int endRow = Integer.valueOf(numbersEnd);
        int endColumn = getIntByChar(rightBottom[rightBottom.length-1]);

        int height = endRow - startRow + 1;

        int length = endColumn - startColumn + 1;

        int count = height * length;

        return new Ship(startRow, startColumn, endRow, endColumn, count);
    }

    class Shout {
        int row;
        int column;

        public Shout(int x, int y) {
            this.row = x;
            this.column = y;
        }

        @Override
        public String toString() {
            return "Shout{" +
                    "row=" + row +
                    ", column=" + column +
                    '}';
        }
    }

    class Ship {
        int shouts;
        int count;
        int startRow;
        int startColumn;
        int endRow;
        int endColumn;

        @Override
        public String toString() {
            return "Ship{" +
                    "startRow=" + startRow +
                    ", startColumn=" + startColumn +
                    ", endRow=" + endRow +
                    ", endColumn=" + endColumn +
                    ", count=" + count +
                    '}';
        }

        public Ship(int startRow, int startColumn, int endRow, int endColumn, int count) {
            this.startRow = startRow;
            this.startColumn = startColumn;
            this.endRow = endRow;
            this.endColumn = endColumn;
            this.count = count;
        }
    }
}
