package com.example.tp_ihc_android;

import android.util.Log;

import com.example.tp_ihc_android.ui.history.HistoryViewModel;

import java.util.Calendar;
import java.util.Random;

public class PresencasSingleton {

    private static PresencasSingleton instance;
    private boolean mvetorPresencas[];

    private int currentMonth;
    private int currentDay;

    private Calendar calendar;

    private PresencasSingleton() {

        calendar = Calendar.getInstance();
        setCurrentDay(calendar.get(Calendar.DAY_OF_MONTH));
        setCurrentMonth(calendar.get(Calendar.MONTH));

        mvetorPresencas = new boolean[32];
        for (int i = 0; i <= 31; i++) {
            mvetorPresencas[i] = false;
        }
        randomizePresencas();
        /*
        mvetorPresencas[1] = true;
        mvetorPresencas[3] = true;
        mvetorPresencas[4] = true;
        mvetorPresencas[10] = true;
        mvetorPresencas[11] = true;
        mvetorPresencas[12] = true;
        mvetorPresencas[15] = true;
         */
    }

    public static synchronized PresencasSingleton getInstance() {
        if (instance == null) {
            instance = new PresencasSingleton();
        }
        return instance;
    }

    public boolean getPresenca(int i){
        return mvetorPresencas[i];
    }
    public void setCurrentDay(int day){
        currentDay = day;
    }
    public int getCurrentDay(){
        return currentDay;
    }
    public void setCurrentMonth(int month){
        currentMonth = month;
    }
    public int getCurrentMonth(){
        return currentMonth;
    }

    public boolean marcarPresenca(){
        boolean isWeekend = isWeekend();

        mvetorPresencas[currentDay] = !isWeekend;
        return !isWeekend;
    }

    public boolean presencaMarcadaHoje(){
        return mvetorPresencas[currentDay];
    }

    public String getPresencasSemana(){
        int presencasSemana = 0;
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        for(int i = (currentDay - dayOfWeek) + 1; i < (currentDay - dayOfWeek) + 7; i++){
            if(mvetorPresencas[i])
                presencasSemana++;
        }
        return String.valueOf(presencasSemana);
    }

    private boolean isWeekend(){
        return (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) || (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY);
     }

     private void randomizePresencas(){
        calendar.add(Calendar.DAY_OF_MONTH, -currentDay+1);
        Random rand = new Random();
        for(int i=1; i<currentDay; i++){
            if(calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)
                mvetorPresencas[i] = rand.nextBoolean();

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Log.wtf("aqui", Integer.toString(i));
            Log.wtf("bool", Boolean.toString(mvetorPresencas[i]));
        }
        Log.wtf("finalera", Integer.toString(calendar.get(Calendar.DAY_OF_MONTH)));
     }
}
