package com.example.dcamo.vermontmongosch;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class Group implements Serializable {
    // provides id field to uniquely identify an object inside an ObjectRepository
    private ArrayList evenWeek;
    private ArrayList oddWeek;
    private ArrayList<ArrayList> preMade;
    private JSONObject dataJsonObj = null;
    private String groupName;

    Group(String rawJSON){
        preMade = createNormalizedWeek(rawJSON);
        setEvenWeek(preMade.get(0));
        setOddWeek(preMade.get(1));
    }

    public ArrayList<ArrayList> getEvenWeek() {
        return evenWeek;
    }

    public ArrayList<ArrayList> getOddWeek() {
        return oddWeek;
    }

    public ArrayList<ArrayList> createNormalizedWeek(String rawJSON) {
        ArrayList<ArrayList> result = new ArrayList();


        try {
            dataJsonObj = new JSONObject(rawJSON);
            String groupName = dataJsonObj.getString("groupName");

            setGroupName(groupName);

            //целая неделяя
            JSONObject evenWeek = dataJsonObj.getJSONObject("evenWeek");
            //инфо недели
            String evenWeekStatus = evenWeek.getJSONObject("info").getString("status");
            //массив дней
            JSONArray evenWeekDays = evenWeek.getJSONArray("week");

            ArrayList<ArrayList> week = new ArrayList<>();
            //каждый день отдельно
            for (int i = 0; i < evenWeekDays.length(); i++) {
                JSONObject evenWeekSingleDay = evenWeekDays.getJSONObject(i);
                ArrayList<ArrayList> day = new ArrayList<>();

                //каждый предмет отдельно
                for (int x = 0; x < evenWeekSingleDay.getJSONArray("lessons").length(); x++){
                    JSONArray evenWeekSingleDayLessons = evenWeekSingleDay.getJSONArray("lessons");
                    //предмет отдельно
                    JSONObject evenWeekSingleDayLessonsActuallyLessons = evenWeekSingleDayLessons.getJSONObject(x);
                    //информация предмета
                    ArrayList<String> lesson = new ArrayList<>();
                    lesson.add(evenWeekSingleDayLessonsActuallyLessons.getString("name"));
                    lesson.add(evenWeekSingleDayLessonsActuallyLessons.getString("teacher"));
                    lesson.add(evenWeekSingleDayLessonsActuallyLessons.getString("auditory"));
                    lesson.add(evenWeekSingleDayLessonsActuallyLessons.getString("notes"));

                    day.add(lesson);
//
//                    Log.e("LessonName" + i +"_"+ x, evenWeekSingleDayLessonsActuallyLessons.getString("name"));
//                    Log.e("LessonTeacher" + i +"_"+ x, evenWeekSingleDayLessonsActuallyLessons.getString("teacher"));
//                    Log.e("LessonAuditory" + i +"_"+ x, evenWeekSingleDayLessonsActuallyLessons.getString("auditory"));
//                    Log.e("LessonNote" + i +"_"+ x, evenWeekSingleDayLessonsActuallyLessons.getString("notes"));
                }
                week.add(day);
            }
            result.add(week);


            JSONObject oddWeek = dataJsonObj.getJSONObject("oddWeek");
            Log.d("oddWeek", String.valueOf(oddWeek));

            String oddWeekStatus = oddWeek.getJSONObject("info").getString("status");
            Log.d("evenWeekStatus", oddWeekStatus);

            JSONArray oddWeekDays = oddWeek.getJSONArray("week");

            week = new ArrayList<>();
            for (int i = 0; i < oddWeekDays.length(); i++) {
                JSONObject oddWeekSingleDay = oddWeekDays.getJSONObject(i);
                ArrayList<ArrayList> day = new ArrayList<>();

                for (int x = 0; x < oddWeekSingleDay.getJSONArray("lessons").length(); x++){
                    JSONArray oddWeekSingleDayLessons = oddWeekSingleDay.getJSONArray("lessons");

                    ArrayList<String> lesson = new ArrayList<>();
                    JSONObject oddWeekSingleDayLessonsActuallyLessons = oddWeekSingleDayLessons.getJSONObject(x);
                    lesson.add(oddWeekSingleDayLessonsActuallyLessons.getString("name"));
                    lesson.add(oddWeekSingleDayLessonsActuallyLessons.getString("teacher"));
                    lesson.add(oddWeekSingleDayLessonsActuallyLessons.getString("auditory"));
                    lesson.add(oddWeekSingleDayLessonsActuallyLessons.getString("notes"));
//                    Log.e("LessonName" + i +"_"+ x, oddWeekSingleDayLessonsActuallyLessons.getString("name"));
//                    Log.e("LessonTeacher" + i +"_"+ x, oddWeekSingleDayLessonsActuallyLessons.getString("teacher"));
//                    Log.e("LessonAuditory" + i +"_"+ x, oddWeekSingleDayLessonsActuallyLessons.getString("auditory"));
//                    Log.e("LessonNote" + i +"_"+ x, oddWeekSingleDayLessonsActuallyLessons.getString("notes"));

                    day.add(lesson);
                }
                week.add(day);
            }
            result.add(week);

//            Log.d("result_even", result.get(0).toString());
//            Log.d("result_odd", result.get(1).toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void setEvenWeek(ArrayList evenWeek) {
        this.evenWeek = evenWeek;
    }

    public void setOddWeek(ArrayList oddWeek) {
        this.oddWeek = oddWeek;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
