package com.example.tp_ihc_android.ui.history;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.tp_ihc_android.PresencasSingleton;
import com.example.tp_ihc_android.R;

import java.util.Calendar;
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
    Button btnMarcarPresenca;

    private float x1,x2;
    static final int MIN_DISTANCE = 150;

    private PresencasSingleton presencas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        calendar = Calendar.getInstance();

        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        root = inflater.inflate(R.layout.fragment_history, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);

        presencas = PresencasSingleton.getInstance();

        btnMarcarPresenca = root.findViewById(R.id.btnMarcarPresenca);
        btnMarcarPresenca.setOnClickListener(myOnclickListener);

        tvMonth = root.findViewById(R.id.tvMonth);

        ivProximoMes = root.findViewById(R.id.ivProximoMes);
        ivProximoMes.setOnClickListener(myOnclickListener);
        ivAnteriorMes = root.findViewById(R.id.ivAnteriorMes);
        ivAnteriorMes.setOnClickListener(myOnclickListener);

        currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentMonth = calendar.get(Calendar.MONTH);

        changeMonth("current");
        fillCalendarTable();

        final GestureDetector gesture = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDown(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                           float velocityY) {
                        final int SWIPE_MIN_DISTANCE = 120;
                        final int SWIPE_MAX_OFF_PATH = 250;
                        final int SWIPE_THRESHOLD_VELOCITY = 200;
                        try {
                            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                                return false;
                            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                changeMonth("next");
                            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
                                    && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                                changeMonth("prev");
                            }
                        } catch (Exception e) {
                            // nothing
                        }
                        return super.onFling(e1, e2, velocityX, velocityY);
                    }
                });

        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gesture.onTouchEvent(event);
            }
        });


        return root;
    }


    private View.OnClickListener myOnclickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivAnteriorMes:
                    changeMonth("prev");
                    break;
                case R.id.ivProximoMes:
                    changeMonth("next");
                    break;
                case R.id.btnMarcarPresenca:
                    if(presencas.presencaMarcadaHoje())
                        showAlertDialogPresencaMarcada(v, R.string.presenca_ja_marcada);
                    else{
                        if(presencas.marcarPresenca())
                            showAlertDialogPresencaMarcada(v, R.string.presenca_marcada);
                        else
                            showAlertDialogNaoPossivelMarcarPresenca(v, R.string.presenca_final_de_semana);
                    }
                    fillCalendarTable();
                    break;
            }
        }
    };

    private View.OnClickListener disabledButton = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnMarcarPresenca:
                    showAlertDialogNaoPossivelMarcarPresenca(v, R.string.presenca_mes_errado);
                    break;
            }
        }
    };


    public void showAlertDialogPresencaMarcada(View view, int stringToShow) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_marcar_presenca);

        Button button = (Button) dialog.findViewById(R.id.btnCloseDialog);
        TextView tv = (TextView) dialog.findViewById(R.id.TextView01);
        tv.setTextColor(getResources().getColor(R.color.green));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv.setText(stringToShow);
        dialog.show();
    }

    public void showAlertDialogNaoPossivelMarcarPresenca(View view, int stringToShow) {
        final Dialog dialog = new Dialog(this.getContext());
        dialog.setContentView(R.layout.dialog_marcar_presenca);

        Button button = (Button) dialog.findViewById(R.id.btnCloseDialog);
        ImageView iv = (ImageView) dialog.findViewById(R.id.ImageView01);
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_close_24));
        TextView tv = (TextView) dialog.findViewById(R.id.TextView01);
        tv.setTextColor(getResources().getColor(R.color.colorPrimary));

        tv.setText(stringToShow);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkDay(){
        if(calendar.get(Calendar.MONTH) == currentMonth) {
            View cDay = root.findViewWithTag("currentDay");
            if (cDay != null) {
                cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
            }
            clearTags();
        }
    }

    private void clearTags(){
        View dummy = root.findViewWithTag("currentDay");
        while(dummy != null){
            dummy.setTag("");
            dummy = root.findViewWithTag("currentDay");
        };
    }

    private void changeMonth(final String command){
        long animationtime = 125;
        AnimationSet animations = new AnimationSet(false);
        Animation translateAnimation;
        Animation translateAnimation2;
        Animation translateAnimation3;

        if(command.equals("prev")){
            calendar.add(Calendar.MONTH,-1);

            translateAnimation = new TranslateAnimation(0f, 1200f, 0f, 0f);
            translateAnimation.setDuration(animationtime);
            animations.addAnimation(translateAnimation);

            translateAnimation2 = new TranslateAnimation(0f, -2400f, 0f, 0f);
            translateAnimation2.setStartOffset(animationtime);
            translateAnimation2.setDuration(1);
            animations.addAnimation(translateAnimation2);

            translateAnimation3 = new TranslateAnimation(0f, 1200f, 0f, 0f);
            translateAnimation3.setStartOffset(animationtime + 1);
            translateAnimation3.setDuration(animationtime);
            animations.addAnimation(translateAnimation3);

            root.findViewById(R.id.tableLayout).startAnimation(animations);
        }
        else if(command.equals("next")){
            calendar.add(Calendar.MONTH,1);

            translateAnimation = new TranslateAnimation(0f, -1200f, 0f, 0f);
            translateAnimation.setDuration(animationtime);
            animations.addAnimation(translateAnimation);

            translateAnimation2 = new TranslateAnimation(0f, 2400f, 0f, 0f);
            translateAnimation2.setStartOffset(animationtime);
            translateAnimation2.setDuration(1);
            animations.addAnimation(translateAnimation2);

            translateAnimation3 = new TranslateAnimation(0f, -1200f, 0f, 0f);
            translateAnimation3.setStartOffset(animationtime + 1);
            translateAnimation3.setDuration(animationtime);
            animations.addAnimation(translateAnimation3);

            root.findViewById(R.id.tableLayout).startAnimation(animations);
        }

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Locale myLocale = new Locale("pt", "BR");
                month = "";
                month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, myLocale);
                String textToTvMonth = month.substring(0, 1).toUpperCase() + month.substring(1).toLowerCase() + "/" + calendar.get(Calendar.YEAR);
                tvMonth.setText(textToTvMonth);

                if(!command.equals("current")) {
                    clearCalendarTable();
                    if (currentMonth == calendar.get(Calendar.MONTH)){
                        btnMarcarPresenca.setOnClickListener(myOnclickListener);
                        fillCalendarTable();
                    }
                    else {
                        btnMarcarPresenca.setOnClickListener(disabledButton);
                        updateCalendarTable();
                    }
                }
            }
        }, animationtime);
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
                if(!isWeekend) {
                    View cDay = ((FrameLayout) child).getChildAt(1);
                    if (currentDay == dayOfMonth) {
                        child.setBackground(getResources().getDrawable(R.drawable.ic_border_squared_yellow));
                        cDay.setTag("currentDay");
                    }
                    if(presencas.getPresenca(dayOfMonth))
                        cDay.setBackground(getResources().getDrawable(R.drawable.ic_check));
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
