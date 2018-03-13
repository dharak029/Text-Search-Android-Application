/**
 * Homework3
 * Group 09
 * @Author : Dharak Shah, Viranchi Deshpande
 * Sentence.java
 */
package com.example.dharak029.hw3_group09;

import android.content.Context;
import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by dharak029 on 9/22/2017.
 */

public class Sentence extends BaseAdapter {

    private ArrayList<MainActivity.Results> items;
    private Context context;
    private int numItems = 0;

    public Sentence(ArrayList<MainActivity.Results> items, Context context, int numItems) {
        this.items = items;
        this.context = context;
        this.numItems = numItems;
    }


    @Override
    public int getCount() {
        return numItems;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MainActivity.Results item = items.get(position);
        final RelativeLayout itemLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.sentence, parent, false);
        TextView txtSentence = (TextView) itemLayout.findViewById(R.id.txtSentence);
        txtSentence.setText(item.resultString);
        return itemLayout;
    }
}
