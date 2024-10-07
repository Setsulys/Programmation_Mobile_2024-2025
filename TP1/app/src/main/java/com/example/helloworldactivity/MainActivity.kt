package com.example.helloworldactivity

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import android.view.View;
import android.widget.ImageView
import android.widget.TextView
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    var click:Int = 0;
    var cities : List<City> = listOf();

    fun checkClick(){
        val textView = findViewById<TextView>(R.id.UpText);
        click++;
        textView.setTextColor(Color.WHITE);
        if (click >= 21) {
            textView.setBackgroundColor(Color.RED);
        } else if (click >= 11) {
            textView.setBackgroundColor(Color.BLUE);
        } else {
            textView.setBackgroundColor(Color.BLACK);
        }
        textView.setText(click.toString());
    }

    fun displayNearestCity(nearCity :City?) {
        val textView = findViewById<TextView>(R.id.UpText);
        if(nearCity!=null){
            textView.setText(nearCity.name);
        }

    }

    fun randomElementFromList(): City {
        val randomIndex = Random.nextInt(cities.size);
        return cities[randomIndex];
    }

    fun displayGame(randomCity: City, latitude:Float, longitude:Float) :Boolean{
        val textView = findViewById<TextView>(R.id.UpText);
        return if(City.findNearest(cities,latitude, longitude)==randomCity){
            textView.setText(getString(R.string.found_it_was)+ randomCity.name);
            true;
        } else{
            var latDiff = abs(latitude - randomCity.latitude);
            var lonDiff = abs(longitude - randomCity.longitude);
            var city =City.findNearest(cities,latitude,longitude);
            if(city!=null){
                var nearestCity = city.name;
                textView.setText(getString(R.string.nearest_city)+ nearestCity +" \n Latitude : "+ latDiff + " | longitude : "+ lonDiff );

            }
            false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(this.javaClass.kotlin.simpleName, getString(R.string.creation))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cities = City.loadFromAsset(this,R.raw.topcities);
        var found = false;

        var randomCity = randomElementFromList();
        val imageViewClick = findViewById<ImageView>(R.id.imageView);


        imageViewClick.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.i(this.javaClass.kotlin.simpleName,"action_down: ${event.x}, ${event.y}")
                    val latitude = 90-(event.y /imageViewClick.height)*180;
                    val longitude = (event.x/imageViewClick.width)*360-180;
                    found = displayGame(randomCity,latitude, longitude);
                    if(found){
                        randomCity = randomElementFromList();
                    }
                }
                else -> { /* do nothing */ }
            }
            true // important to return true to continue to receive the following events
        }

    }

    fun onQuitClicked(v :View){
        val toast = Toast.makeText(this,
            getString(R.string.let_s_quit_the_activity), Toast.LENGTH_SHORT);// in Activity
        toast.show();
        Log.i(this.javaClass.kotlin.simpleName, getString(R.string.leaving_application))
        finish();
    }

    fun imageClick(v:View){
        click+=1
    }

}