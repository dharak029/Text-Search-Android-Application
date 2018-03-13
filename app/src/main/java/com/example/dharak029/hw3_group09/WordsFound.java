/**
 * Homework3
 * Group 09
 * @Author : Dharak Shah, Viranchi Deshpande
 * WordsFound.java
 */
package com.example.dharak029.hw3_group09;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class WordsFound extends AppCompatActivity {

    ArrayList<MainActivity.Results> foundWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_words_found);

        foundWords = new ArrayList<MainActivity.Results>();
        foundWords = MainActivity.searchResultList;


        Comparator<MainActivity.Results> comparator = new Comparator<MainActivity.Results>() {
            @Override
            public int compare(MainActivity.Results o1, MainActivity.Results o2) {

                long index1 = o1.index;
                long index2 = o2.index;
                int compare = Long.compare(index1,index2);

                return compare;
            }
        };


        Collections.sort(foundWords,comparator);

//        ArrayList<Spannable> results = new ArrayList<Spannable>();
//
//        for(MainActivity.Results strings:foundWords)
//            results.add(strings.resultString);


        final ListAdapter listAdapter = new Sentence(foundWords,this,foundWords.size());
        ListView lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(listAdapter);
        lv.setTextFilterEnabled(true);

        findViewById(R.id.btnFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WordsFound.this,MainActivity.class);
                intent.putExtra("demo","demo");
                startActivity(intent);
            }
        });
    }
}
