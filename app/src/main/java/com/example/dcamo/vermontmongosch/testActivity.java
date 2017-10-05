package com.example.dcamo.vermontmongosch;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.style.TextAppearanceSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


public class testActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        NestedScrollView nestedScrollView = (NestedScrollView)findViewById(R.id.NestedScrollView);

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        nestedScrollView.addView(linearLayout);

        TableLayout table = new TableLayout(this);
        table.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        table.setOrientation(LinearLayout.VERTICAL);
        table.setPadding(0,15,0,0);
        table.setColumnStretchable(0, true);
        linearLayout.addView(table);

        TableRow tableRow1 = new TableRow(this);
        tableRow1.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        tableRow1.setOrientation(LinearLayout.VERTICAL);
        table.addView(tableRow1);

        LinearLayout linearLayoutSub1 = new LinearLayout(this);
        linearLayoutSub1.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        linearLayoutSub1.setOrientation(LinearLayout.VERTICAL);
        linearLayoutSub1.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        tableRow1.addView(linearLayoutSub1);

        TextView textView1_1 = new TextView(this);
        textView1_1.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        textView1_1.setText("1");
        textView1_1.setPadding(10, 0, 10, 0);// in pixels (left, top, right, bottom)
        textView1_1.setGravity(Gravity.CENTER);
        linearLayoutSub1.addView(textView1_1);

        LinearLayout linearLayoutSub2 = new LinearLayout(this);
        linearLayoutSub2.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.MATCH_PARENT));
        linearLayoutSub2.setOrientation(LinearLayout.VERTICAL);
        tableRow1.addView(linearLayoutSub2);

        TextView textView2_1 = new TextView(this);
        textView2_1.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT));
        textView2_1.setText("Математика");
        textView2_1.setGravity(Gravity.CENTER);
        linearLayoutSub2.addView(textView2_1);









//        // Add textview 1
//        TextView textView1 = new TextView(this);
//        textView1.setLayoutParams(new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,
//                Toolbar.LayoutParams.WRAP_CONTENT));
//        textView1.setText("programmatically created TextView1");
//        textView1.setBackgroundColor(0xff66ff66); // hex color 0xAARRGGBB
//        textView1.setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
//        linearLayout.addView(textView1);
//
//        // Add textview 2
//        TextView textView2 = new TextView(this);
//        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,
//                Toolbar.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.RIGHT;
//        layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
//        textView2.setLayoutParams(layoutParams);
//        textView2.setText("programmatically created TextView2");
//        linearLayout.addView(textView2);
















//        TextView dayName= (TextView)findViewById(R.id.textView14);


//        setContentView(R.layout.activity_test);

        // NOTE: setContentView is below, not here

        // Create new LinearLayout









//        TextView textView2 = new TextView(this);
//        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,
//                Toolbar.LayoutParams.WRAP_CONTENT);
//        layoutParams.gravity = Gravity.RIGHT;
//        layoutParams.setMargins(10, 10, 10, 10); // (left, top, right, bottom)
//        textView2.setLayoutParams(layoutParams);
//        textView2.setText("my text2");
//        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
//        linearLayout.addView(textView2);

        // Set context view

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

    }

}
