package com.example.kreedaankana

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnCalendar).setOnClickListener {
            startActivity(Intent(this, GroundCalendarActivity::class.java))
        }
        findViewById<Button>(R.id.btnChallenge).setOnClickListener {
            startActivity(Intent(this, ChallengeActivity::class.java))
        }
        findViewById<Button>(R.id.btnScore).setOnClickListener {
            startActivity(Intent(this, ScoreWallActivity::class.java))
        }
    }
}