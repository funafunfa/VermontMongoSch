package com.example.dcamo.vermontmongosch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;


import java.lang.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;


public class Main extends AppCompatActivity {

    public static String LOG_TAG = "my_log";
    public static String name = "ПС-1401";
    public Group def;
    ProgressDialog pDialog;
    private static final String MY_SETTINGS = "my_settings";
    public static final String STRING = "string";
    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        TextView mondayText = (TextView)findViewById(R.id.dayName1);
//        TextView tuesdayText = (TextView)findViewById(R.id.dayName2);
//        TextView wednesdayText = (TextView)findViewById(R.id.dayName3);
//        TextView thursdayText = (TextView)findViewById(R.id.dayName4);
//        TextView fridayText = (TextView)findViewById(R.id.dayName5);
//        TextView saturdayText = (TextView)findViewById(R.id.dayName6);
//
//
//        TableLayout mondayLayout = (TableLayout)findViewById(R.id.dayMonday);
//        TableLayout tuesdayLayout = (TableLayout)findViewById(R.id.dayTuesday);
//        TableLayout wednesdayLayout = (TableLayout)findViewById(R.id.dayWednesday);
//        TableLayout thursdayLayout = (TableLayout)findViewById(R.id.dayThursday);
//        TableLayout fridayLayout = (TableLayout)findViewById(R.id.dayFriday);
//        TableLayout saturdayLayout = (TableLayout)findViewById(R.id.daySaturday);
//
//        destroyDatPussy(mondayText, mondayLayout);
//        destroyDatPussy(tuesdayText, tuesdayLayout);
//        destroyDatPussy(wednesdayText, wednesdayLayout);
//        destroyDatPussy(thursdayText, thursdayLayout);
//        destroyDatPussy(fridayText, fridayLayout);
//        destroyDatPussy(saturdayText, saturdayLayout);
        mSettings = getSharedPreferences(STRING, Context.MODE_PRIVATE);
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);

        // проверяем, первый ли раз открывается программа
        boolean hasVisited = sp.getBoolean("hasVisited", false);

        if (!hasVisited) {
            new ParseTask().execute();
            SharedPreferences.Editor e = sp.edit();
            e.putBoolean("hasVisited", true);
            e.apply(); // не забудьте подтвердить изменения
        }else{
//            String strJson =
            if(mSettings.contains(STRING)) {
                Group def = new Group(mSettings.getString(STRING, ""));
                Calendar calender = Calendar.getInstance();
//        Log.e("Current Week:", String.valueOf((calender.get(Calendar.WEEK_OF_YEAR))));
                int week_num = calender.get(Calendar.WEEK_OF_YEAR);
                if (week_num % 2 == 0) {
                    createOnScreenGUI(def.getEvenWeek());
                }else if (week_num % 2 != 0){
                    createOnScreenGUI(def.getOddWeek());
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "I'm sorry, some Lapki occurred.", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }

        getSupportActionBar().setTitle("ПС-1401");


//
////android initialization
//        Group def = new Group(getIntent().getStringExtra("json_string"));
//        try {
//            getSupportActionBar().setTitle(def.getGroupName());
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }
//
//        Calendar calender = Calendar.getInstance();
////        Log.e("Current Week:", String.valueOf((calender.get(Calendar.WEEK_OF_YEAR))));
//        int week_num = calender.get(Calendar.WEEK_OF_YEAR);
//        if (week_num % 2 == 0) {
//            createOnScreenGUI(def.getEvenWeek());
//        }else if (week_num % 2 != 0){
//            createOnScreenGUI(def.getOddWeek());
//        }else{
//            Toast toast = Toast.makeText(getApplicationContext(),
//                    "I'm sorry, some Lapki occurred.", Toast.LENGTH_SHORT);
//            toast.show();
//        }

    }

    private class ParseTask extends AsyncTask<Void, Void, String> {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Loading. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(Void... params) {
            // получаем данные с внешнего ресурса
            try {
//                String query = URLEncoder.encode(groupName, "utf-8");

//                URL url = new URL("http://193.151.106.187:3000/api/group/" + query);
                URL url = new URL("http://193.151.106.187:3000/test");
//                URL url = new URL("http://10.0.2.2:3000/test");
//                URL url = new URL("http://193.151.106.187:3000/api/group/");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                resultJson = buffer.toString();



            } catch (Exception e) {
                e.printStackTrace();
            }
            return resultJson;
        }

        @Override
        protected void onPostExecute(String strJson) {
            super.onPostExecute(strJson);
            // выводим целиком полученную json-строку
            Log.d("str", strJson);
            if (strJson.isEmpty()){
                Log.d("ERROR", "STRJSON IS EMPTY");
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Lapki \n Try again", Toast.LENGTH_SHORT);
                toast.show();
            } else {

//                Intent intent = new Intent(FirstMainActivity.this, Main.class);
//                intent.putExtra("json_string", strJson);
//                startActivity(intent);
//
//
//                SharedPreferences.Editor editor = mSettings.edit();
//                editor.putString(APP_PREFERENCES_NAME, groupName);
//                editor.apply();
//
//                finish();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putString(STRING, strJson);
                editor.apply();

                Group def = new Group(strJson);
                Calendar calender = Calendar.getInstance();
//        Log.e("Current Week:", String.valueOf((calender.get(Calendar.WEEK_OF_YEAR))));
                int week_num = calender.get(Calendar.WEEK_OF_YEAR);
                if (week_num % 2 == 0) {
                    createOnScreenGUI(def.getEvenWeek());
                }else if (week_num % 2 != 0){
                    createOnScreenGUI(def.getOddWeek());
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "I'm sorry, some Lapki occurred.", Toast.LENGTH_SHORT);
                    toast.show();
                }

//                    Group def = new Group(strJson);
//                Calendar calender = Calendar.getInstance();
////        Log.e("Current Week:", String.valueOf((calender.get(Calendar.WEEK_OF_YEAR))));
//                int week_num = calender.get(Calendar.WEEK_OF_YEAR);
//                if (week_num % 2 == 0) {
//                    createOnScreenGUI(def.getEvenWeek());
//                }else if (week_num % 2 != 0){
//                    createOnScreenGUI(def.getOddWeek());
//                }else{
//                    Toast toast = Toast.makeText(getApplicationContext(),
//                            "I'm sorry, some Lapki occurred.", Toast.LENGTH_SHORT);
//                    toast.show();
//                }
                pDialog.dismiss();



            }

        }
    }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
//                getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    Intent intent = new Intent(Main.this, SettingsActivity.class);
                    startActivity(intent);
//                    finish();
                }
                return super.onOptionsItemSelected(item);
            }

    public void createOnScreenGUI(ArrayList<ArrayList> week){

        TableRow mondayOneRow = (TableRow)findViewById(R.id.dayMondayOne);
        TableRow mondayTwoRow = (TableRow)findViewById(R.id.dayMondayTwo);
        TableRow mondayThreeRow = (TableRow)findViewById(R.id.dayMondayThree);
        TableRow mondayFourRow = (TableRow)findViewById(R.id.dayMondayFour);
        TableRow mondayFiveRow = (TableRow)findViewById(R.id.dayMondayFive);
        TableRow mondaySixRow = (TableRow)findViewById(R.id.dayMondaySix);

        TableRow tuesdayOneRow = (TableRow)findViewById(R.id.dayTuesdayOne);
        TableRow tuesdayTwoRow = (TableRow)findViewById(R.id.dayTuesdayTwo);
        TableRow tuesdayThreeRow = (TableRow)findViewById(R.id.dayTuesdayThree);
        TableRow tuesdayFourRow = (TableRow)findViewById(R.id.dayTuesdayFour);
        TableRow tuesdayFiveRow = (TableRow)findViewById(R.id.dayTuesdayFive);
        TableRow tuesdaySixRow = (TableRow)findViewById(R.id.dayTuesdaySix);

        TableRow wednesdayOneRow = (TableRow)findViewById(R.id.dayWednesdayOne);
        TableRow wednesdayTwoRow = (TableRow)findViewById(R.id.dayWednesdayTwo);
        TableRow wednesdayThreeRow = (TableRow)findViewById(R.id.dayWednesdayThree);
        TableRow wednesdayFourRow = (TableRow)findViewById(R.id.dayWednesdayFour);
        TableRow wednesdayFiveRow = (TableRow)findViewById(R.id.dayWednesdayFive);
        TableRow wednesdaySixRow = (TableRow)findViewById(R.id.dayWednesdaySix);

        TableRow thursdayOneRow = (TableRow)findViewById(R.id.dayThursdayOne);
        TableRow thursdayTwoRow = (TableRow)findViewById(R.id.dayThursdayTwo);
        TableRow thursdayThreeRow = (TableRow)findViewById(R.id.dayThursdayThree);
        TableRow thursdayFourRow = (TableRow)findViewById(R.id.dayThursdayFour);
        TableRow thursdayFiveRow = (TableRow)findViewById(R.id.dayThursdayFive);
        TableRow thursdaySixRow = (TableRow)findViewById(R.id.dayThursdaySix);

        TableRow fridayOneRow = (TableRow)findViewById(R.id.dayFridayOne);
        TableRow fridayTwoRow = (TableRow)findViewById(R.id.dayFridayTwo);
        TableRow fridayThreeRow = (TableRow)findViewById(R.id.dayFridayThree);
        TableRow fridayFourRow = (TableRow)findViewById(R.id.dayFridayFour);
        TableRow fridayFiveRow = (TableRow)findViewById(R.id.dayFridayFive);
        TableRow fridaySixRow = (TableRow)findViewById(R.id.dayFridaySix);

        TableRow saturdayOneRow = (TableRow)findViewById(R.id.daySaturdayOne);
        TableRow saturdayTwoRow = (TableRow)findViewById(R.id.daySaturdayTwo);
        TableRow saturdayThreeRow = (TableRow)findViewById(R.id.daySaturdayThree);
        TableRow saturdayFourRow = (TableRow)findViewById(R.id.daySaturdayFour);
        TableRow saturdayFiveRow = (TableRow)findViewById(R.id.daySaturdayFive);
        TableRow saturdaySixRow = (TableRow)findViewById(R.id.daySaturdaySix);

        TableLayout mondayLayout = (TableLayout)findViewById(R.id.dayMonday);
        TableLayout tuesdayLayout = (TableLayout)findViewById(R.id.dayTuesday);
        TableLayout wednesdayLayout = (TableLayout)findViewById(R.id.dayWednesday);
        TableLayout thursdayLayout = (TableLayout)findViewById(R.id.dayThursday);
        TableLayout fridayLayout = (TableLayout)findViewById(R.id.dayFriday);
        TableLayout saturdayLayout = (TableLayout)findViewById(R.id.daySaturday);


        ArrayList monday = week.get(0);
        ArrayList mondayOne = (ArrayList) monday.get(0);
        ArrayList mondayTwo = (ArrayList) monday.get(1);
        ArrayList mondayThree = (ArrayList) monday.get(2);
        ArrayList mondayFour = (ArrayList) monday.get(3);
        ArrayList mondayFive = (ArrayList) monday.get(4);
        ArrayList mondaySix = (ArrayList) monday.get(5);

        ArrayList tuesday= week.get(1);
        ArrayList tuesdayOne = (ArrayList) tuesday.get(0);
        ArrayList tuesdayTwo = (ArrayList) tuesday.get(1);
        ArrayList tuesdayThree = (ArrayList) tuesday.get(2);
        ArrayList tuesdayFour = (ArrayList) tuesday.get(3);
        ArrayList tuesdayFive = (ArrayList) tuesday.get(4);
        ArrayList tuesdaySix = (ArrayList) tuesday.get(5);

        ArrayList wednesday = week.get(2);
        ArrayList wednesdayOne = (ArrayList) wednesday.get(0);
        ArrayList wednesdayTwo = (ArrayList) wednesday.get(1);
        ArrayList wednesdayThree = (ArrayList) wednesday.get(2);
        ArrayList wednesdayFour = (ArrayList) wednesday.get(3);
        ArrayList wednesdayFive = (ArrayList) wednesday.get(4);
        ArrayList wednesdaySix = (ArrayList) wednesday.get(5);

        ArrayList thursday= week.get(3);
        ArrayList thursdayOne = (ArrayList) thursday.get(0);
        ArrayList thursdayTwo = (ArrayList) thursday.get(1);
        ArrayList thursdayThree = (ArrayList) thursday.get(2);
        ArrayList thursdayFour = (ArrayList) thursday.get(3);
        ArrayList thursdayFive = (ArrayList) thursday.get(4);
        ArrayList thursdaySix = (ArrayList) thursday.get(5);

        ArrayList friday= week.get(4);
        ArrayList fridayOne = (ArrayList) friday.get(0);
        ArrayList fridayTwo = (ArrayList) friday.get(1);
        ArrayList fridayThree = (ArrayList) friday.get(2);
        ArrayList fridayFour = (ArrayList) friday.get(3);
        ArrayList fridayFive = (ArrayList) friday.get(4);
        ArrayList fridaySix = (ArrayList) friday.get(5);

        ArrayList saturday  = week.get(5);
        ArrayList saturdayOne = (ArrayList) saturday.get(0);
        ArrayList saturdayTwo = (ArrayList) saturday.get(1);
        ArrayList saturdayThree = (ArrayList) saturday.get(2);
        ArrayList saturdayFour = (ArrayList) saturday.get(3);
        ArrayList saturdayFive = (ArrayList) saturday.get(4);
        ArrayList saturdaySix = (ArrayList) saturday.get(5);

        TextView mondayText = (TextView)findViewById(R.id.dayName1);
        TextView tuesdayText = (TextView)findViewById(R.id.dayName2);
        TextView wednesdayText = (TextView)findViewById(R.id.dayName3);
        TextView thursdayText = (TextView)findViewById(R.id.dayName4);
        TextView fridayText = (TextView)findViewById(R.id.dayName5);
        TextView saturdayText = (TextView)findViewById(R.id.dayName6);



//        destroyDatPussy(mondayText, mondayLayout);
        Log.e("ddd", mondayOne.toString());
        if (checkDatPussy(monday)){
            destroyDatPussy(mondayText, mondayLayout);
        }if (checkDatPussy(tuesday)){
            destroyDatPussy(tuesdayText, tuesdayLayout);
        }if (checkDatPussy(wednesday)){
            destroyDatPussy(wednesdayText, wednesdayLayout);
        }if (checkDatPussy(thursday)){
            destroyDatPussy(thursdayText, thursdayLayout);
        }if (checkDatPussy(friday)){
            destroyDatPussy(fridayText, fridayLayout);
        }if (checkDatPussy(saturday)){
            destroyDatPussy(saturdayText, saturdayLayout);
        }

        checkDatPussy(mondayOne, mondayTwo, mondayThree, mondayFour, mondayFive, mondaySix, mondayOneRow, mondayTwoRow, mondayThreeRow, mondayFourRow, mondayFiveRow, mondaySixRow);
        checkDatPussy(tuesdayOne, tuesdayTwo, tuesdayThree, tuesdayFour, tuesdayFive, tuesdaySix, tuesdayOneRow, tuesdayTwoRow, tuesdayThreeRow, tuesdayFourRow, tuesdayFiveRow, tuesdaySixRow);
        checkDatPussy(wednesdayOne, wednesdayTwo, wednesdayThree, wednesdayFour, wednesdayFive, wednesdaySix, wednesdayOneRow, wednesdayTwoRow, wednesdayThreeRow, wednesdayFourRow, wednesdayFiveRow, wednesdaySixRow);
        checkDatPussy(thursdayOne, thursdayTwo, thursdayThree, thursdayFour, thursdayFive, thursdaySix, thursdayOneRow, thursdayTwoRow, thursdayThreeRow, thursdayFourRow, thursdayFiveRow, thursdaySixRow);
        checkDatPussy(fridayOne, fridayTwo, fridayThree, fridayFour, fridayFive, fridaySix, fridayOneRow, fridayTwoRow, fridayThreeRow, fridayFourRow, fridayFiveRow, fridaySixRow);
        checkDatPussy(saturdayOne, saturdayTwo, saturdayThree, saturdayFour, saturdayFive, saturdaySix, saturdayOneRow, saturdayTwoRow, saturdayThreeRow, saturdayFourRow, saturdayFiveRow, saturdaySixRow);

        createLesson(mondayOne, mondayOneRow);
        createLesson(mondayTwo, mondayTwoRow);
        createLesson(mondayThree, mondayThreeRow);
        createLesson(mondayFour, mondayFourRow);
        createLesson(mondayFive, mondayFiveRow);
        createLesson(mondaySix, mondaySixRow);

        createLesson(tuesdayOne, tuesdayOneRow);
        createLesson(tuesdayTwo, tuesdayTwoRow);
        createLesson(tuesdayThree, tuesdayThreeRow);
        createLesson(tuesdayFour, tuesdayFourRow);
        createLesson(tuesdayFive, tuesdayFiveRow);
        createLesson(tuesdaySix, tuesdaySixRow);

        createLesson(wednesdayOne, wednesdayOneRow);
        createLesson(wednesdayTwo, wednesdayTwoRow);
        createLesson(wednesdayThree, wednesdayThreeRow);
        createLesson(wednesdayFour, wednesdayFourRow);
        createLesson(wednesdayFive, wednesdayFiveRow);
        createLesson(wednesdaySix, wednesdaySixRow);

        createLesson(thursdayOne, thursdayOneRow);
        createLesson(thursdayTwo, thursdayTwoRow);
        createLesson(thursdayThree, thursdayThreeRow);
        createLesson(thursdayFour, thursdayFourRow);
        createLesson(thursdayFive, thursdayFiveRow);
        createLesson(thursdaySix, thursdaySixRow);

        createLesson(fridayOne, fridayOneRow);
        createLesson(fridayTwo, fridayTwoRow);
        createLesson(fridayThree, fridayThreeRow);
        createLesson(fridayFour, fridayFourRow);
        createLesson(fridayFive, fridayFiveRow);
        createLesson(fridaySix, fridaySixRow);

        createLesson(saturdayOne, saturdayOneRow);
        createLesson(saturdayTwo, saturdayTwoRow);
        createLesson(saturdayThree, saturdayThreeRow);
        createLesson(saturdayFour, saturdayFourRow);
        createLesson(saturdayFive, saturdayFiveRow);
        createLesson(saturdaySix, saturdaySixRow);
    }

    public void destroyDatPussy(TextView textView, TableLayout tableLayout){
        textView.setVisibility(View.GONE);
        textView.setEnabled(false);
        tableLayout.setVisibility(View.GONE);
        tableLayout.setEnabled(false);

    }
//    public void redestroyDatPussy(TextView textView, TableLayout tableLayout){
//        textView.setVisibility(View.VISIBLE);
//        textView.setEnabled(true);
//        tableLayout.setVisibility(View.VISIBLE);
//        tableLayout.setEnabled(true);
//
//    }
    public void destroyDatPussy(TableRow tableRow){
        tableRow.setVisibility(View.GONE);
        tableRow.setEnabled(false);
    }

    public boolean checkDatPussy(ArrayList arrayList){

            ArrayList arrayList0 = (ArrayList) arrayList.get(0);
            ArrayList arrayList1 = (ArrayList) arrayList.get(1);
            ArrayList arrayList2 = (ArrayList) arrayList.get(2);
            ArrayList arrayList3 = (ArrayList) arrayList.get(3);
            ArrayList arrayList4 = (ArrayList) arrayList.get(4);
            ArrayList arrayList5 = (ArrayList) arrayList.get(5);
            return (((arrayList0.get(0).toString().equals("")) && (arrayList0.get(1).toString().equals("")) && (arrayList0.get(2).toString().equals("")) && (arrayList0.get(3).toString().equals(""))) && ((arrayList1.get(0).toString().equals("")) && (arrayList1.get(1).toString().equals("")) && (arrayList1.get(2).toString().equals("")) && (arrayList1.get(3).toString().equals(""))) && ((arrayList2.get(0).toString().equals("")) && (arrayList2.get(1).toString().equals("")) && (arrayList2.get(2).toString().equals("")) && (arrayList2.get(3).toString().equals(""))) && ((arrayList3.get(0).toString().equals("")) && (arrayList3.get(1).toString().equals("")) && (arrayList3.get(2).toString().equals("")) && (arrayList3.get(3).toString().equals(""))) && ((arrayList4.get(0).toString().equals("")) && (arrayList4.get(1).toString().equals("")) && (arrayList4.get(2).toString().equals("")) && (arrayList4.get(3).toString().equals(""))) && ((arrayList5.get(0).toString().equals("")) && (arrayList5.get(1).toString().equals("")) && (arrayList5.get(2).toString().equals("")) && (arrayList5.get(3).toString().equals(""))));
    }
    public void checkDatPussy(ArrayList arrayList0, ArrayList arrayList1, ArrayList arrayList2, ArrayList arrayList3, ArrayList arrayList4, ArrayList arrayList5, TableRow table0, TableRow table1, TableRow table2, TableRow table3, TableRow table4, TableRow table5){
        if ((arrayList0.get(0).toString().equals("")) && (arrayList0.get(1).toString().equals("")) && (arrayList0.get(2).toString().equals("")) && (arrayList0.get(3).toString().equals(""))) destroyDatPussy(table0);
        if ((arrayList1.get(0).toString().equals("")) && (arrayList1.get(1).toString().equals("")) && (arrayList1.get(2).toString().equals("")) && (arrayList1.get(3).toString().equals(""))) destroyDatPussy(table1);
        if ((arrayList2.get(0).toString().equals("")) && (arrayList2.get(1).toString().equals("")) && (arrayList2.get(2).toString().equals("")) && (arrayList2.get(3).toString().equals(""))) destroyDatPussy(table2);
        if ((arrayList3.get(0).toString().equals("")) && (arrayList3.get(1).toString().equals("")) && (arrayList3.get(2).toString().equals("")) && (arrayList3.get(3).toString().equals(""))) destroyDatPussy(table3);
        if ((arrayList4.get(0).toString().equals("")) && (arrayList4.get(1).toString().equals("")) && (arrayList4.get(2).toString().equals("")) && (arrayList4.get(3).toString().equals(""))) destroyDatPussy(table4);
        if ((arrayList5.get(0).toString().equals("")) && (arrayList5.get(1).toString().equals("")) && (arrayList5.get(2).toString().equals("")) && (arrayList5.get(3).toString().equals(""))) destroyDatPussy(table5);
    }

    public void createLesson(ArrayList lesson, TableRow tableRow) {
        LinearLayout layout = (LinearLayout) tableRow.getChildAt(1);
        TextView lessonName = (TextView) layout.getChildAt(0);
        TextView lessonTeacher = (TextView) layout.getChildAt(1);
        TextView lessonAuditory = (TextView) layout.getChildAt(2);
        lessonName.setText(lesson.get(0).toString());
        lessonTeacher.setText(lesson.get(1).toString());
        lessonAuditory.setText(lesson.get(2).toString());
//        Log.e("zzz", lesson.get(0).toString());
    }
}


