package com.tkartas.speedcubingtimer.datamodel;

import java.util.*;

public class Scrambles {
    private List<String> scrambles;

    public Scrambles() {
        scrambles=new LinkedList<>();
    }

    public void addScramble(String scramble){
        scrambles.add(scramble);
    }

    public List<String> getScrambles() {
        return scrambles;
    }

    public void setScrambles(List<String> scrambles) {
        this.scrambles = scrambles;
    }


    public int getSize(){
        return scrambles.size();
    }

    public String getScramble(int position){
            return scrambles.get(position);
    }

    public int getPositionForScramble(String scramble) {
        return scramble.indexOf(scramble);
    }
}
