package com.example.amma.cir2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ArrayAdapterQuizStatistics extends ArrayAdapter<ScoreStatitics> {
    private Context context;
    private List<ScoreStatitics> scoreStatitics;

    //constructor, call on creation
    public ArrayAdapterQuizStatistics(Context context, int resource, ArrayList<ScoreStatitics> objects) {
        super(context, resource, objects);
        this.context = context;
        this.scoreStatitics = objects;
    }

    //called when rendering the list
    public View getView(int position, View convertView, ViewGroup parent) {

        //get the property we are displaying
        ScoreStatitics score = scoreStatitics.get(position);

        //get the inflater and inflate the XML layout for each item
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.statistics_result_holder, null);

        TextView correct = view.findViewById(R.id.statistics_correctAnswers);
        TextView incorrect = view.findViewById(R.id.statistics_wrongAnsers);
        TextView total = view.findViewById(R.id.statistics_totalQuestions);
        TextView date = view.findViewById(R.id.statistics_dateofExam);
        TextView no = view.findViewById(R.id.statistics_serialnumber);

        no.setText(String.valueOf(position + 1));
        correct.setText(score.getCorrect());
        incorrect.setText(String.valueOf(score.getIncorrect()));
        total.setText(String.valueOf(score.getTotal()));
        date.setText(String.valueOf(score.getDate()));


        return view;
    }
}

