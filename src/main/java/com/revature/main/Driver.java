package com.revature.main;

import com.revature.util.Console;
import com.revature.util.List;
import com.revature.util.PoorArrayList;

import java.util.ArrayList;
import java.util.stream.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Driver {
//    public static void main(String[] args) {
//        List<Integer> myArray = new PoorArrayList<>();
//        for (int i = 1; i < 12; i++)
//            myArray.add(i);
//
//        for (int i : myArray) {
//            System.out.println(i);
//        }
//        //   System.out.println(myArray.get(14));
//    }
//}
    public static void main(String[] args) {
        String choice = "y";
        while (choice.equals("y")) {
            LocalDate n = Console.getDate("Enter a number:");
            System.out.println("date entered:  " + n);
            choice = Console.getString("Continue? (y/n): ");
        }

    }
}
