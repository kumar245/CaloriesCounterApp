package com.kumar.user.caloriescounterapp;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.DatabaseHandler;
import model.Food;

public class food_items_details extends AppCompatActivity {
    private TextView foodName,calories,dateTaken;
    private Button shareButton;
    private int foodId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_items_details);
        foodName= (TextView) findViewById(R.id.detsFoodName);
        calories= (TextView) findViewById(R.id.detsCaloriesValue);
        dateTaken= (TextView) findViewById(R.id.detsDateText);
        shareButton= (Button) findViewById(R.id.detsShareButton);
        Food food= (Food) getIntent().getSerializableExtra("userObj");
        foodName.setText(food.getFoodName());
        calories.setText(String .valueOf(food.getCalories()));
        dateTaken.setText(food.getRecordDate());
        foodId=food.getFoodId();
        calories.setTextSize(34.9f);
        calories.setTextColor(Color.RED);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharecals();
            }
        });
    }
    public void sharecals(){
        StringBuilder dataString=new StringBuilder();

        String name=foodName.getText().toString();
        String cals=calories.getText().toString();
        String date=dateTaken.getText().toString();
        dataString.append("Food: " + name + "\n");
        dataString.append("Calories: " + cals + "\n");
        dataString.append("Eaten on: " + date + "\n");
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_SUBJECT,"My Caloric Intake");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"popurichaitanyakumar@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT,dataString.toString());
        try {
            startActivity(Intent.createChooser(intent,"send email..."));

        }
        catch (ActivityNotFoundException e){
            Toast.makeText(this, "Please install email client before sending", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_items_details,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.deleteItem){
            AlertDialog.Builder alert=new AlertDialog.Builder(food_items_details.this);
            alert.setTitle("Delete?");
            alert.setMessage("Are you sure you want to delete this item?");
            alert.setNegativeButton("No",null);
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    DatabaseHandler dba=new DatabaseHandler(getApplicationContext());
                    dba.deleteFood(foodId);
                    Toast.makeText(food_items_details.this, "Food Item was Deleted", Toast.LENGTH_LONG).show();
               startActivity(new Intent(food_items_details.this,Displays_food.class));
                    //remove this activity from stack

                    food_items_details.this.finish();
                }
            });
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }
}
