package com.example.kreedaankana

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class ScoreWallActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().getReference("scores")
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score_wall)

        db = AppDatabase.getDatabase(this)

        val etTeam1 = findViewById<EditText>(R.id.etTeam1)
        val etTeam2 = findViewById<EditText>(R.id.etTeam2)
        val etScore = findViewById<EditText>(R.id.etScore)
        val btnPost = findViewById<Button>(R.id.btnPostScore)
        val scoreList = findViewById<LinearLayout>(R.id.scoreList)

        btnPost.setOnClickListener {
            val team1 = etTeam1.text.toString()
            val team2 = etTeam2.text.toString()
            val score = etScore.text.toString()
            if (team1.isEmpty() || team2.isEmpty() || score.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val id = database.push().key!!
                val result = mapOf("team1" to team1, "team2" to team2, "score" to score)
                database.child(id).setValue(result)

                // Save to Room DB
                lifecycleScope.launch {
                    db.matchDao().insertMatch(MatchEntity(team1 = team1, team2 = team2, score = score))
                }

                Toast.makeText(this, "Score Posted!", Toast.LENGTH_SHORT).show()
                etTeam1.text.clear()
                etTeam2.text.clear()
                etScore.text.clear()
            }
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                scoreList.removeAllViews()
                for (child in snapshot.children) {
                    val team1 = child.child("team1").value.toString()
                    val team2 = child.child("team2").value.toString()
                    val score = child.child("score").value.toString()
                    val tv = TextView(this@ScoreWallActivity)
                    tv.text = "🏆 $team1 vs $team2 — $score"
                    tv.textSize = 14f
                    tv.setTextColor(resources.getColor(android.R.color.white))
                    tv.setPadding(8, 16, 8, 16)
                    scoreList.addView(tv)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}