package com.tkartas.speedcubingtimer;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Analyzer {
    private List<Double> timesList=new LinkedList<>();

    public void addTime(Double time){
        this.timesList.add(time);
    }

    public String printTimes(){
        StringBuilder sb=new StringBuilder();
        for(Double time:timesList){
            sb.append(time);
            sb.append(", ");
        }
        return sb.toString();
    }

    public Double getTime(int index){
        return timesList.get(index);
    }

    public int getSize(){
        return timesList.size();
    }

    public String minTime(){
        return String.format("%.2f",Collections.min(timesList));
    }
    public String maxTime(){
        return String.format("%.2f",Collections.max(timesList));
    }

    public String bestAvg5(){
        List<Double> fiveListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-5;i++){
            fiveListAvg.add(countAvg5(i));
        }
        return String.format("%.2f",Collections.min(fiveListAvg));
    }

    public String currentAvg5(){
        List<Double> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-4;i<=this.timesList.size();i=i+1){
            fiveList.add(timesList.get(i-1));
        }
        double sum=0;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        return String.format("%.2f",sum/3);
    }

    public String bestAvg12(){
        List<Double> twelveListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-12;i++){
            twelveListAvg.add(countAvg12(i));
        }
        return String.format("%.2f",Collections.min(twelveListAvg));
    }

    public String currentAvg12(){
        List<Double> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-11;i<=this.timesList.size();i=i+1){
            fiveList.add(timesList.get(i-1));
        }
        double sum=0;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        return String.format("%.2f",sum/10);
    }

    public String bestAvg100(){
        List<Double> hundredListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-100;i++){
            hundredListAvg.add(countAvg100(i));
        }
       return String.format("%.2f",Collections.min(hundredListAvg));
    }

    public String currentAvg100(){
        List<Double> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-99;i<=this.timesList.size();i=i+1){
            fiveList.add(timesList.get(i-1));
        }
        double sum=0;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        return String.format("%.2f",sum/98);
    }


   //    public Analyzer(String name, String path) {
//        this.sciezka = FileSystems.getDefault().getPath(path, name);
//    }
//
//    public Analyzer(String name) {
//        this(name, "D:\\");
//    }
//
//    public List<Double> createList(String name, String path) {
//         Path sciezka=FileSystems.getDefault().getPath(path, name);
//        String plik="";
//        try {BufferedReader br = Files.newBufferedReader(sciezka, StandardCharsets.UTF_8);
//            String line;
//            while ((line = br.readLine()) != null) {
//                //process line
//                plik = line;
//            }
//        } catch (IOException e) {
//            //Handle exception
//            System.err.println("IOException: %s%n" + e);
//        }
//        String[] czasy = plik.split(", ");
//        for (String s : czasy) {
//            if(s.contains("+")){
//                s=s.substring(0,Math.min(s.length(),s.length()-1));
//            }
////            System.out.println(s);
//            this.listaCzasow.add(Double.parseDouble(s));
//        }
//        System.out.println("List with times has been created");
//        return this.listaCzasow;
//    }
//
//    public void printList(){
//        for(double d:listaCzasow){
//            System.out.println(d);
//        }
//    }
//
    public void analyzeList(){
        System.out.println("---------------------------------");
        System.out.println("Number of times: "+this.timesList.size());
        System.out.println("Best time: "+ String.format("%.2f",Collections.min(this.timesList)));
        System.out.println("Worst time: "+ String.format("%.2f",Collections.max(this.timesList)));
        System.out.println("---------------------------------");
        List<Double> fiveListAvg=new ArrayList<>();
        for(int i=0;i<=this.timesList.size()-5;i++){
            fiveListAvg.add(countAvg5(i));
        }
        System.out.println("Best average of 5: "+String.format("%.2f",Collections.min(fiveListAvg)));
        System.out.println("Worst average of 5: "+String.format("%.2f",Collections.max(fiveListAvg)));
        System.out.println("---------------------------------");
        List<Double> twelveListAvg=new ArrayList<>();
        for(int i=0;i<=this.timesList.size()-12;i++){
            twelveListAvg.add(countAvg12(i));
        }
        System.out.println("Best average of 100: "+String.format("%.2f",Collections.min(twelveListAvg)));
        System.out.println("Worst average of 100: "+String.format("%.2f",Collections.max(twelveListAvg)));
        System.out.println("---------------------------------");
        List<Double> hundredListAvg=new ArrayList<>();
        for(int i=0;i<=this.timesList.size()-100;i++){
            hundredListAvg.add(countAvg100(i));
        }
        System.out.println("Best average of 100: "+String.format("%.2f",Collections.min(hundredListAvg)));
        System.out.println("Worst average of 100: "+String.format("%.2f",Collections.max(hundredListAvg)));
        System.out.println("---------------------------------");
        System.out.println("Number of sub9 times: "+ String.format("%.0f",numberOfTimes(9.00))+" ("+String.format("%.2f",(numberOfTimes(9.00)/this.timesList.size())*100)+"%)");
        System.out.println("Number of sub10 times: "+ String.format("%.0f",numberOfTimes(10.00))+" ("+String.format("%.2f",(numberOfTimes(10.00)/this.timesList.size())*100)+"%)");
        System.out.println("Number of sub11 times: "+ String.format("%.0f",numberOfTimes(11.00))+" ("+String.format("%.2f",(numberOfTimes(11.00)/this.timesList.size())*100)+"%)");
        System.out.println("Number of sub12 times: "+ String.format("%.0f",numberOfTimes(12.00))+" ("+String.format("%.2f",(numberOfTimes(12.00)/this.timesList.size())*100)+"%)");
        System.out.println("Number of sub13 times: "+ String.format("%.0f",numberOfTimes(13.00))+" ("+String.format("%.2f",(numberOfTimes(13.00)/this.timesList.size())*100)+"%)");
        System.out.println("---------------------------------");

    }

    private double countAvg5(Integer indexNumber){
        List<Double> fiveList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+4;i++){
            fiveList.add(this.timesList.get(i));
        }
        double sum=0;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        return sum/3;
    }

    private double countAvg12(Integer indexNumber){
        List<Double> twelveList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+11;i++){
            twelveList.add(this.timesList.get(i));
        }
        double sum=0;
        twelveList.remove(Collections.min(twelveList));
        twelveList.remove(Collections.max(twelveList));
        for(int i=0;i<twelveList.size();i++){
            sum+=twelveList.get(i);
        }
        return sum/10;
    }

    private double countAvg100(Integer indexNumber){
        List<Double> hundredList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+99;i++){
            hundredList.add(this.timesList.get(i));
        }
        double sum=0;
        hundredList.remove(Collections.min(hundredList));
        hundredList.remove(Collections.max(hundredList));
        for(int i=0;i<hundredList.size();i++){
            sum+=hundredList.get(i);
        }
        return sum/98;
    }

    private double numberOfTimes(Double time){
        int licznik=0;
        for(double czas:this.timesList){
            if(czas<=time){
                licznik++;
            }
        }
        return licznik;
    }

}
