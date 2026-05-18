package com.example.kreedaankana

import android.os.Bundle
import android.widget.Button
import android.widget.GridLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class GroundCalendarActivity : AppCompatActivity() {

    val bookedSlots = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ground_calendar)

        val grid = findViewById<GridLayout>(R.id.slotGrid)
        val slots = listOf("6AM-8AM", "8AM-10AM", "10AM-12PM", "12PM-2PM", "2PM-4PM", "4PM-6PM", "6PM-8PM", "8PM-10PM")

        for (slot in slots) {
            val btn = Button(this)
            btn.text = slot
            btn.setBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
            btn.setTextColor(resources.getColor(android.R.color.white))
            val params = GridLayout.LayoutParams()
            params.width = 0
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            params.setMargins(8, 8, 8, 8)
            btn.layoutParams = params

            btn.setOnClickListener {
                if (bookedSlots.contains(slot)) {
                    Toast.makeText(this, "$slot already booked!", Toast.LENGTH_SHORT).show()
                } else {
                    bookedSlots.add(slot)
                    btn.setBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
                    Toast.makeText(this, "$slot booked successfully!", Toast.LENGTH_SHORT).show()
                }
            }
            grid.addView(btn)
        }
    }
}