package midterm.test;


import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class SortingTest {

    public static void main(String[] args) throws Exception {
        // Given sample code with instructions
        List<Integer> numList = new ArrayList<>();
        numList.add(25);
        numList.add(19);
        numList.add(5);
        numList.add(2);
        numList.add(1);

        // implemetation section
        // bubble sort
        // it pushes the largest number to the right
        // 25    19   5   2  1
        // first iteration
        // pick 25 in the outter loop
        // compare and swap in the inner loop
        // 25 > 19,  25 > 5, 25 > 2, 25 > 1
        // after swap: 19 5 2 1 25
        // 1    2   5   19  25
        int n = numList.size();
        for(int i=0; i<n; i++){ // outter loop
            for (int j=0; j<n-i-1; j++){ // inner loop
                // compare and sawp
                if(numList.get(j) > numList.get(j+1)){
                    int temp = numList.get(j);
                    numList.set(j, numList.get(j+1));
                    numList.set(j+1, temp);
                }
            }
        }
        for (int k=0; k<numList.size(); k++){
            System.out.println(numList.get(k));
        }

        System.out.println(" Step 1");

        // store the result in the database
        ConnectDB db = new ConnectDB();
       db.InsertDataFromArrayListToMySql(numList,"bubble_sort_table", "number");

        System.out.println(" Step 2");

        // retrieve the result from database

        List<String> resultSet = db.readDataBase("bubble_sort_table","number");

        for (String str: resultSet){
            System.out.println(str);
        }

        // assert the result
        // expected result
        List<Integer> expectedNumber = new ArrayList<>();
        expectedNumber.add(1);
        expectedNumber.add(2);
        expectedNumber.add(5);
        expectedNumber.add(19);
        expectedNumber.add(25);

        for (int i=0; i<5; i++){
            Integer actualNumber = Integer.parseInt(resultSet.get(i));
            //System.out.println("a" + actualNumber);
            //System.out.println("e" + expectedNumber.get(i));
            Assert.assertEquals(actualNumber , expectedNumber.get(i));
            i++;
           // System.out.println("assertion pass");
        }
        System.out.println("assertion pass");
    }
    }

















