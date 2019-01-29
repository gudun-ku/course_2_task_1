package com.beloushkin.learning.android.consoleapp;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PeopleCounter counter = new PeopleCounter(22,4);

        System.out.println("================ PEOPLE COUNTER ========================");
        System.out.println("Initial conditions: ");
        System.out.println("Levels:" + counter.getNumOfLevels());
        System.out.println("People at level:" + 100);
        System.out.println("Exact number of people (calculated): " + counter.getNumOfLevels() * counter.NUM_OF_PEOPLE);
        System.out.println("Threads for execution: " + counter.getNumOfThreads());

        System.out.println("================ COUNT =================================");
        counter.count();
        System.out.println("================ END OF COUNT ==========================");

        finish();
    }
}
