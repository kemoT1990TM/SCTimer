package com.tkartas.speedcubingtimer.datamodel;

import java.util.*;

public class Scrambles {
    private int position=0;
    private String scramble;
    private Map<Integer,String> scrambles;

    public Scrambles() {
        scrambles=new LinkedHashMap<>();
    }

    public void addRecord(String scramble){
        scrambles.put(position,scramble);
        position++;
    }

    public TimesAnalyzer getAllTimes() {
        TimesAnalyzer times=new TimesAnalyzer();
        for(String time:scrambles.values()){
            times.addTime(time);
        }
        return times;
    }

    public String getScrambleForPosition(int positio){
            return scrambles.get(positio);
    }

    public int getPositionForScramble(String scramble) {
        for(Integer position:scrambles.keySet()){
            if(scrambles.get(position).equals(scramble)){
                return position;
            }
        }
        return -1;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }
}
