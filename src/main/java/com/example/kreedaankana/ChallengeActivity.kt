package com.example.kreedaankana

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChallengeActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().getReference("challenges")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge)

        val etTeamName = findViewById<EditText>(R.id.etTeamName)
        val etChallengeMessage = findViewById<EditText>(R.id.etChallengeMessage)
        val btnPost = findViewById<Button>(R.id.btnPostChallenge)
        val challengeList = findViewById<LinearLayout>(R.id.challengeList)

        btnPost.setOnClickListener {
            val team = etTeamName.text.toString()
            val message = etChallengeMessage.text.toString()
            if (team.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Fill in all fields!", Toast.LENGTH_SHORT).show()
            } else {
                val id = database.push().key!!
                val challenge = mapOf("team" to team, "message" to message, "reply" to "")
                database.child(id).setValue(challenge)
                etTeamName.text.clear()
                etChallengeMessage.text.clear()
            }
        }

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                challengeList.removeAllViews()
                for (child in snapshot.children) {
                    val challengeId = child.key!!
                    val team = child.child("team").value.toString()
                    val message = child.child("message").value.toString()
                    val reply = child.child("reply").value.toString()

                    val container = LinearLayout(this@ChallengeActivity)
                    container.orientation = LinearLayout.VERTICAL
                    container.setPadding(8, 16, 8, 8)

                    val tvChallenge = TextView(this@ChallengeActivity)
                    tvChallenge.text = "⚔️ $team: $message"
                    tvChallenge.textSize = 15f
                    tvChallenge.setTextColor(resources.getColor(android.R.color.white))

                    val etReply = EditText(this@ChallengeActivity)
                    etReply.hint = "Reply to this challenge..."

                    val btnReply = Button(this@ChallengeActivity)
                    btnReply.text = "Reply"
                    btnReply.setOnClickListener {
                        val replyText = etReply.text.toString()
                        if (replyText.isNotEmpty()) {
                            database.child(challengeId).child("reply").setValue(replyText)
                            Toast.makeText(this@ChallengeActivity, "Reply posted!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    container.addView(tvChallenge)

                    if (reply.isNotEmpty()) {
                        val tvReply = TextView(this@ChallengeActivity)
                        tvReply.text = "💬 Reply: $reply"
                        tvReply.textSize = 13f
                        tvReply.setTextColor(resources.getColor(android.R.color.holo_green_light))
                        container.addView(tvReply)
                    }

                    container.addView(etReply)
                    container.addView(btnReply)
                    challengeList.addView(container)
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}