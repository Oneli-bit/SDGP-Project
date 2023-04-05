package com.example.sdgp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class ProgressTrack : AppCompatActivity() {

    lateinit var barchart : BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_track)

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        navigation.selectedItemId = R.id.progress;

        navigation.setOnItemSelectedListener {
            when (it.itemId) {        // add correct activities
                 R.id.calBFP -> {
                     loadActivity(BFPMales1())
                     true
                 }
                 R.id.workout_plans -> {  // need to change activity
                     loadActivity(BFPFemales1())
                     true
                 }
                 R.id.progress -> {
                     loadActivity(ProgressTrack())
                     true
                 }
                else -> { loadActivity(ProgressTrack())
                    true}
            }
        }

        barchart = findViewById(R.id.bar_chart)
        //var i = 1
        val barEntries : ArrayList<BarEntry> = ArrayList()
        /*while (i<=10){
            val value : Float = (i.toFloat())
            val barEntry = BarEntry(i.toFloat(),value)
            barEntries.add(barEntry)
            i++
        }*/
        for (i in 1..10){
            val value : Float = (i.toFloat())
            val barEntry = BarEntry(i.toFloat(),value)
            barEntries.add(barEntry)
        }
        val barDataSet = BarDataSet(barEntries,"Fat Percentage")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toMutableList()
        barDataSet.setDrawValues(false)
        barchart.data = BarData(barDataSet)
        barchart.animateY(5000)
        barchart.description.isEnabled = false
    }

    private  fun loadActivity(activity: Activity){   // bottom nav bar
        val act = Intent(this, activity::class.java)
        startActivity(act)

    }
}

/*   val graphView = findViewById<GraphView>(R.id.idGraphView)

        val series: LineGraphSeries<DataPoint> = LineGraphSeries(
            arrayOf( // on below line we are adding
                // each point on our x(attempt) and y axis(bfp).

               *//* DataPoint(0.0, 1.0),
                DataPoint(1.0, 3.0),
                DataPoint(2.0, 4.0),
                DataPoint(3.0, 9.0),
                DataPoint(4.0, 6.0),
                DataPoint(5.0, 3.0),
                DataPoint(6.0, 6.0),
                DataPoint(7.0, 1.0),
                DataPoint(8.0, 2.0)*//*
                DataPoint(0.0, 20.0),
                DataPoint(20.0, 10.0),

            )
        )

        graphView.setTitle("Body Fat Percentage");

        // on below line we are setting
        // text color to our graph view.
        graphView.setTitleColor(R.color.purple_200);

        // on below line we are setting
        // our title text size.
        graphView.setTitleTextSize(50F);

        // on below line we are adding
        // data series to our graph view.
        graphView.addSeries(series);*/