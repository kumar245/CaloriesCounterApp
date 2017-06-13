package com.kumar.user.caloriescounterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class MainActivity extends AppCompatActivity {
    private EditText foodName,foodCals;
    private Button submitButton;
    private DatabaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dba=new DatabaseHandler(MainActivity.this);

        foodName= (EditText) findViewById(R.id.foodEditText);
        foodCals= (EditText) findViewById(R.id.caloriesEditText);
        submitButton= (Button) findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToDB();
            }
        });

    }

    private void saveToDB() {
        Food food=new Food();
        String name=foodName.getText().toString().trim();
        String calsString=foodCals.getText().toString().trim();
        int cals=Integer.parseInt(calsString);
        if (name.equals("")|| calsString.equals("")){
            Toast.makeText(this, "No Empty Fields are Allowed", Toast.LENGTH_SHORT).show();
        }
        else {
            food.setFoodName(name);
            food.setCalories(cals);
            dba.addFood(food);
            dba.close();

            //clear the form
            foodName.setText("");
            foodCals.setText("");

            //take users to the next screen (display all entered items)
            startActivity(new Intent(MainActivity.this,Displays_food.class));

        }
    }
}
