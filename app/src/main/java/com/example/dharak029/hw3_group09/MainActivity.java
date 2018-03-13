/**
 * Homework3
 * Group 09
 * @Author : Dharak Shah, Viranchi Deshpande
 * MainActivity.java
 */

package com.example.dharak029.hw3_group09;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static class Results implements Serializable{
        long index;
        Spannable resultString;
    }
    EditText textIn;
    ImageButton buttonAdd;
    LinearLayout container;
    ArrayList<String> wordList;
    byte[] buffer;
    String text;
    int keywordCount=0;
    CheckBox checkBox;
    static ArrayList<Results> searchResultList = new ArrayList<Results>();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (ImageButton)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);
        wordList = new ArrayList<String>();
        checkBox = (CheckBox) findViewById(R.id.ckMatchCase);

        if(getIntent().getExtras()!=null){
            searchResultList.clear();
        }
        try {
            InputStream is = getAssets().open("textfile.txt");
            int size = is.available();
            buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);


        }
        catch (Exception e){

        }



        findViewById(R.id.btnSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count=0;
                String checked = "false";
                if(checkBox.isChecked())
                    checked = "true";


                for(String word: wordList){
                    String [] param = new String[4];
                    param[0] = word;
                    param[1] = ""+(wordList.size()-1);
                    param[2] = ""+count;
                    param[3] = checked;
                    new DoWork().execute(param);
                    count++;
                }
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (keywordCount <= 19) {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    final TextView textOut = (TextView) addView.findViewById(R.id.textout);
                    textOut.setText(textIn.getText().toString());
                    wordList.add(textIn.getText().toString());
                    textIn.setText("");
                    ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);

                    if(keywordCount==19){
                        textIn.setVisibility(View.GONE);
                        buttonAdd.setVisibility(View.GONE);
                    }

                    final View.OnClickListener thisListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ((LinearLayout) addView.getParent()).removeView(addView);
                            wordList.remove(textOut.getText().toString());

                            textIn.setVisibility(View.VISIBLE);
                            buttonAdd.setVisibility(View.VISIBLE);
                            listAllAddView();
                            keywordCount--;
                        }
                    };

                    buttonRemove.setOnClickListener(thisListener);
                    container.addView(addView);

                    listAllAddView();
                    keywordCount++;
                }
            }
        });
    }

    private void listAllAddView(){
        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
        }
    }


    class DoWork extends AsyncTask<String,Integer,ArrayList<Results>>{

        ColorStateList color;
        TextAppearanceSpan textAppearanceSpan;
        String param[];
        @Override
        protected ArrayList<Results> doInBackground(String... params) {

            param = params;
            if(Integer.parseInt(params[2]+1)%3 == 1){
                color =  new ColorStateList(new int[][] { new int[] {}}, new int[] { Color.RED });
                textAppearanceSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, color, null);
            }
            else if(Integer.parseInt(params[2]+1)%3 == 2){
                color =  new ColorStateList(new int[][] { new int[] {}}, new int[] { Color.BLUE });
                textAppearanceSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, color, null);
            }
            else{
                color =  new ColorStateList(new int[][] { new int[] {}}, new int[] { Color.GREEN });
                textAppearanceSpan = new TextAppearanceSpan(null, Typeface.NORMAL, -1, color, null);
            }
            String text1 = text;
            int prgress = 100/Integer.parseInt(params[1]+1);
            if(params[3] == "false"){
                text1 = text1.toLowerCase();
                params[0] = params[0].toLowerCase();
            }

            int index = text1.indexOf(params[0]);
            while (index >= 0) {
                if(index<10){
                    Results results = new Results();
                    Spannable spannable = new SpannableString("..."+text.substring(0,30)+"...");
                    spannable.setSpan(textAppearanceSpan,index+3,index+3+params[0].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    results.index = index;
                    results.resultString = spannable;
                    MainActivity.searchResultList.add(results);
                }
                else if(index>text.length()-30){
                    Results results = new Results();
                    Spannable spannable = new SpannableString("..."+text.substring(text.length()-30,text.length())+"...");
                    spannable.setSpan(textAppearanceSpan,text.length()- index+3,text.length()- index+3+params[0].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    results.index = index;
                    results.resultString = spannable;
                    MainActivity.searchResultList.add(results);
                }
                else {
                    Results results = new Results();
                    Spannable spannable = new SpannableString("..."+text.substring(index - 10, index-10+30)+"...");
                    spannable.setSpan(textAppearanceSpan,13,13+params[0].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    results.index = index;
                    results.resultString = spannable;
                    MainActivity.searchResultList.add(results);
                }
                index = text1.indexOf(params[0], index + 1);
            }
            publishProgress(prgress*Integer.parseInt(params[2]+1));
            return MainActivity.searchResultList;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar = (ProgressBar) findViewById(R.id.progressBar);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setMax(100);
        }

        @Override
        protected void onPostExecute(ArrayList<Results> aVoid) {
            super.onPostExecute(aVoid);
            Log.d("para",param[1]);
            if(Integer.parseInt(param[1]) == Integer.parseInt(param[2])) {

                Intent intent = new Intent(MainActivity.this, WordsFound.class);
                startActivity(intent);
            }

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
        }

    }
}

