package com.tkartas.speedcubingtimer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {
    private List<String> timesList=new LinkedList<>();

    public void addTime(String time){
        timesList.add(time);
    }


    public String printTimes(){
        StringBuilder sb=new StringBuilder();
        for(String time:timesList){
            sb.append(time);
            sb.append(", ");
        }
        return sb.toString();
    }

    private Long convertStringToMillis(String time) {
        String regEx;
        Pattern pattern;
        Matcher matcher;
        long millis=0,seconds=0,minutes=0,hours=0,totalTime;
        if (time.length() <= 6) {
            regEx = "(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = Long.parseLong(matcher.group(2));
                seconds = Long.parseLong(matcher.group(1));
            }
        } else if (time.length() <= 9) {
            regEx = "(\\d+):(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = Long.parseLong(matcher.group(3));
                seconds = Long.parseLong(matcher.group(2));
                minutes = Long.parseLong(matcher.group(1));
            }
        } else if (time.length() <= 12) {
            regEx = "(\\d+):(\\d+):(\\d+)\\.(\\d+)";
            pattern = Pattern.compile(regEx);
            matcher = pattern.matcher(time);
            matcher.reset();
            while (matcher.find()) {
                millis = Long.parseLong(matcher.group(4));
                seconds = Long.parseLong(matcher.group(3));
                minutes = Long.parseLong(matcher.group(2));
                hours = Long.parseLong(matcher.group(1));
            }
        }
        totalTime=millis+1000*(seconds+60*minutes+3600*(hours-1));
        return totalTime;
    }

    private String convertToDateFormat(long timeInMillis){
        DateFormat df = new SimpleDateFormat("s.SS");
        long timeDev = timeInMillis / 1000;
        if (timeDev >= 10 && timeDev < 60) {
            df = new SimpleDateFormat("ss.SS");
            System.out.println(timeDev);
        } else if (timeDev >= 60 && timeDev < 600) {
            System.out.println(timeDev);
            df = new SimpleDateFormat("m:ss.SS");
        } else if (timeDev >= 600 && timeDev < 3600) {
            System.out.println(timeDev);
            df = new SimpleDateFormat("mm:ss.SS");
        } else if (timeDev > 3600) {
            System.out.println(timeDev);
            df = new SimpleDateFormat("HH:mm:ss.SS");
        }
        return df.format(timeInMillis);
    }

    public String getTime(int index){
        return timesList.get(index);
    }

    public int getSize(){
        return timesList.size();
    }

    public String minTime(){
        List<Long> newtimesList=new LinkedList<>();
        for(String time:timesList){
            newtimesList.add(convertStringToMillis(time));
        }
        Long result=Collections.min(newtimesList);
        return convertToDateFormat(result);
    }
    public String maxTime(){
        List<Long> newtimesList=new LinkedList<>();
        for(String time:timesList){
            newtimesList.add(convertStringToMillis(time));
        }
        Long result=Collections.max(newtimesList);
        return convertToDateFormat(result);
    }

    public String bestAvg5(){
        List<Long> fiveListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-5;i++){
            fiveListAvg.add(countAvg5(i));
        }
        return convertToDateFormat(Collections.min(fiveListAvg));
    }

    public String currentAvg5(){
        List<Long> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-4;i<=this.timesList.size();i=i+1){
            fiveList.add(convertStringToMillis(timesList.get(i-1)));
        }
        Long sum=0L;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        Long result=sum/3;
        return convertToDateFormat(result);
    }

    public String bestAvg12(){
        List<Long> twelveListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-12;i++){
            twelveListAvg.add(countAvg12(i));
        }
        return convertToDateFormat(Collections.min(twelveListAvg));
    }

    public String currentAvg12(){
        List<Long> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-11;i<=this.timesList.size();i=i+1){
            fiveList.add(convertStringToMillis(timesList.get(i-1)));
        }
        Long sum=0L;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        Long result=sum/10;
        return convertToDateFormat(result);
    }

    public String bestAvg100(){
        List<Long> hundredListAvg=new LinkedList<>();
        for(int i=0;i<=this.timesList.size()-100;i++){
            hundredListAvg.add(countAvg100(i));
        }
       return convertToDateFormat(Collections.min(hundredListAvg));
    }

    public String currentAvg100(){
        List<Long> fiveList=new LinkedList<>();
        for(int i=this.timesList.size()-99;i<=this.timesList.size();i=i+1){
            fiveList.add(convertStringToMillis(timesList.get(i-1)));
        }
        Long sum=0L;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        Long result=sum/98;
        return convertToDateFormat(result);
    }

    public List<String> deleteLastTime(){
        if(timesList.size()>0) {
            timesList.remove(timesList.size() - 1);
            return timesList;
        } else {
            return timesList;
        }
    }

//    public List<Long> plusTwo(){
//        double lastTime;
//        if(timesList.size()>0) {
//            lastTime=timesList.get(timesList.size() - 1);
//            timesList.remove(timesList.size() - 1);
//            lastTime+=0.2;
//            timesList.add(lastTime);
//            return timesList;
//        } else {
//            return timesList;
//        }
//
//    }

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
//    public void analyzeList(){
//        System.out.println("---------------------------------");
//        System.out.println("Number of times: "+this.timesList.size());
//        System.out.println("Best time: "+ String.format("%.2f",Collections.min(this.timesList)));
//        System.out.println("Worst time: "+ String.format("%.2f",Collections.max(this.timesList)));
//        System.out.println("---------------------------------");
//        List<Double> fiveListAvg=new ArrayList<>();
//        for(int i=0;i<=this.timesList.size()-5;i++){
//            fiveListAvg.add(countAvg5(i));
//        }
//        System.out.println("Best average of 5: "+String.format("%.2f",Collections.min(fiveListAvg)));
//        System.out.println("Worst average of 5: "+String.format("%.2f",Collections.max(fiveListAvg)));
//        System.out.println("---------------------------------");
//        List<Double> twelveListAvg=new ArrayList<>();
//        for(int i=0;i<=this.timesList.size()-12;i++){
//            twelveListAvg.add(countAvg12(i));
//        }
//        System.out.println("Best average of 100: "+String.format("%.2f",Collections.min(twelveListAvg)));
//        System.out.println("Worst average of 100: "+String.format("%.2f",Collections.max(twelveListAvg)));
//        System.out.println("---------------------------------");
//        List<Double> hundredListAvg=new ArrayList<>();
//        for(int i=0;i<=this.timesList.size()-100;i++){
//            hundredListAvg.add(countAvg100(i));
//        }
//        System.out.println("Best average of 100: "+String.format("%.2f",Collections.min(hundredListAvg)));
//        System.out.println("Worst average of 100: "+String.format("%.2f",Collections.max(hundredListAvg)));
//        System.out.println("---------------------------------");
//        System.out.println("Number of sub9 times: "+ String.format("%.0f",numberOfTimes(9.00))+" ("+String.format("%.2f",(numberOfTimes(9.00)/this.timesList.size())*100)+"%)");
//        System.out.println("Number of sub10 times: "+ String.format("%.0f",numberOfTimes(10.00))+" ("+String.format("%.2f",(numberOfTimes(10.00)/this.timesList.size())*100)+"%)");
//        System.out.println("Number of sub11 times: "+ String.format("%.0f",numberOfTimes(11.00))+" ("+String.format("%.2f",(numberOfTimes(11.00)/this.timesList.size())*100)+"%)");
//        System.out.println("Number of sub12 times: "+ String.format("%.0f",numberOfTimes(12.00))+" ("+String.format("%.2f",(numberOfTimes(12.00)/this.timesList.size())*100)+"%)");
//        System.out.println("Number of sub13 times: "+ String.format("%.0f",numberOfTimes(13.00))+" ("+String.format("%.2f",(numberOfTimes(13.00)/this.timesList.size())*100)+"%)");
//        System.out.println("---------------------------------");
//
//    }

    private Long countAvg5(int indexNumber){
        List<Long> fiveList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+4;i++){
            fiveList.add(convertStringToMillis(timesList.get(i)));
        }
        Long sum=0L;
        fiveList.remove(Collections.min(fiveList));
        fiveList.remove(Collections.max(fiveList));
        for(int i=0;i<fiveList.size();i++){
            sum+=fiveList.get(i);
        }
        return sum/3;
    }

    private Long countAvg12(int indexNumber){
        List<Long> twelveList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+11;i++){
            twelveList.add(convertStringToMillis(timesList.get(i)));
        }
        Long sum=0L;
        twelveList.remove(Collections.min(twelveList));
        twelveList.remove(Collections.max(twelveList));
        for(int i=0;i<twelveList.size();i++){
            sum+=twelveList.get(i);
        }
        return sum/10;
    }

    private Long countAvg100(Integer indexNumber){
        List<Long> hundredList=new LinkedList<>();
        for(int i=indexNumber;i<=indexNumber+99;i++){
            hundredList.add(convertStringToMillis(timesList.get(i)));
        }
        Long sum=0L;
        hundredList.remove(Collections.min(hundredList));
        hundredList.remove(Collections.max(hundredList));
        for(int i=0;i<hundredList.size();i++){
            sum+=hundredList.get(i);
        }
        return sum/98;
    }

}
