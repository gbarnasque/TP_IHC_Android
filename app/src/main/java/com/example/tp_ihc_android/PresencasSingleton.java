package com.example.tp_ihc_android;

import com.example.tp_ihc_android.ui.history.HistoryViewModel;

import java.util.Calendar;

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
        for (int i = 0; i < 32; i++) {
            mvetorPresencas[i] = false;
        }
        mvetorPresencas[2] = true;
        mvetorPresencas[3] = true;
        mvetorPresencas[4] = true;
        mvetorPresencas[10] = true;
        mvetorPresencas[11] = true;
        mvetorPresencas[12] = true;
        //mvetorPresencas[15] = true;
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

    public void marcarPresenca(){
        mvetorPresencas[currentDay] = true;
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

}
