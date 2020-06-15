package com.example.tp_ihc_android.ui.history;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp_ihc_android.R;

import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    TextView tvMonth;
    ImageView ivProximoMes;
    ImageView ivAnteriorMes;
    String month;
    int currentMonth, currentDay;
    private Calendar calendar;

    View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_history, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        historyViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });


        tvMonth = root.findViewById(R.id.tvMonth);

        ivProximoMes = root.findViewById(R.id.ivProximoMes);
        ivProximoMes.setOnClickListener(prevNextListener);
        ivAnteriorMes = root.findViewById(R.id.ivAnteriorMes);
        ivAnteriorMes.setOnClickListener(prevNextListener);

        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);

        changeMonth("current");
        fillCalendarTable();

        return root;
    }


    private void changeMonth(String command){
        if(command.equals("prev")){
            calendar.add(Calendar.MONTH,-1);
        }
        else if(command.equals("next")){
            calendar.add(Calendar.MONTH,1);
        }

        final Locale myLocale = new Locale("pt", "BR");
        month = "";
        month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, myLocale);
        String textToTvMonth = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + "/" + calendar.get(Calendar.YEAR);
        tvMonth.setText(textToTvMonth);

        if(!command.equals("current")) {
            clearCalendarTable();
            if (currentMonth == calendar.get(Calendar.MONTH))
                fillCalendarTable();
            else
                updateCalendarTable();
        }
    }

    private void fillCalendarTable(){
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
        boolean isWeekend = calendar.get(Calendar.DAY_OF_WEEK) == 1 || calendar.get(Calendar.DAY_OF_WEEK) == 7;

        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maximumDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int dayOfMonth = 1;
        ViewGroup row;
        row = (ViewGroup)root.findViewById(R.id.tr1);
        for(int i=0; i<row.getChildCount(); i++){
            if(i < calendar.get(Calendar.DAY_OF_WEEK)-1) continue;
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;

                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr2);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }
        row = (ViewGroup)root.findViewById(R.id.tr3);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr4);
        for(int i=0; i<row.getChildCount(); i++){
            if(dayOfMonth > maximumDays) continue;
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr5);
        for(int i=0; i<row.getChildCount(); i++){
            if(dayOfMonth > maximumDays) continue;
            //if(i < calendar.get(Calendar.DAY_OF_WEEK)-1) continue;
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr6);
        for(int i=0; i<row.getChildCount(); i++){
            if(dayOfMonth > maximumDays) continue;
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(currentDay == dayOfMonth && !isWeekend){
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                }
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
    }

    private void updateCalendarTable(){
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maximumDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int dayOfMonth = 1;
        ViewGroup row;
        row = (ViewGroup)root.findViewById(R.id.tr1);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(i < calendar.get(Calendar.DAY_OF_WEEK)-1) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;

                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr2);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr3);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr4);
        for(int i=0; i<row.getChildCount(); i++){

            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr5);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr6);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }
    }

    private View.OnClickListener prevNextListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivAnteriorMes:
                    changeMonth("prev");
                    break;
                case R.id.ivProximoMes:
                    changeMonth("next");
                    break;
            }
        }
    };


    private void clearCalendarTable(){
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        int maximumDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int dayOfMonth = 1;
        ViewGroup row;
        row = (ViewGroup)root.findViewById(R.id.tr1);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0 || i ==  row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(i < calendar.get(Calendar.DAY_OF_WEEK)-1) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr2);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0 || i == row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr3);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0 || i == row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }

            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr4);
        for(int i=0; i<row.getChildCount(); i++){

            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0 || i == row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }

            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr5);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0 || i == row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }
            }
        }

        row = (ViewGroup)root.findViewById(R.id.tr6);
        for(int i=0; i<row.getChildCount(); i++){
            View child = row.getChildAt(i);
            if(child instanceof FrameLayout){
                if(i == 0)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_round_bottom_left_gray));
                else if (i ==  row.getChildCount() - 1)
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_round_bottom_right_gray));
                else
                    child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared));

                clearCheck(child);

                View day = ((FrameLayout) child).getChildAt(0);
                if(day instanceof TextView){
                    if(dayOfMonth > maximumDays) {
                        ((TextView) day).setText("");
                        continue;
                    }
                    ((TextView) day).setText(String.valueOf(dayOfMonth));
                    dayOfMonth++;
                }

            }
        }
    }

    private void clearCheck(View v){
        if(((FrameLayout) v).getChildCount() > 1 ){
            View check = ((FrameLayout) v).getChildAt(1);
            if(check instanceof TextView){
                check.setBackground(getResources().getDrawable(R.drawable.ic_check_void));
            }
        }
    }

}
