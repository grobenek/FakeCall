package szathmary.peter.fakecall

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TelefonatActivity : AppCompatActivity() {
    lateinit var cislo : String
    var resume = false
    var elapsedTime: Long = 0
    var TAG = "TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.telefonat_activity)
        retrieveMessage()
        val cancelButton : FloatingActionButton = findViewById(R.id.cancelActionButton)
        cancelButton.setOnClickListener(View.OnClickListener {
            switchActivities(cislo)
        })
        val volamText: TextView = findViewById(R.id.volamTextView)
        volamText.text = getString(R.string.volam_cislo, cislo)

        val counter : Chronometer = findViewById(R.id.timer)

        counter.onChronometerTickListener = OnChronometerTickListener {
            if (!resume) {
                val minutes: Long = (SystemClock.elapsedRealtime() - counter.base) / 1000 / 60
                val seconds: Long = (SystemClock.elapsedRealtime() - counter.base) / 1000 % 60
                elapsedTime = SystemClock.elapsedRealtime()
                Log.d(TAG, "onChronometerTick: $minutes : $seconds")
            } else {
                val minutes: Long = (elapsedTime - counter.base) / 1000 / 60
                val seconds: Long = (elapsedTime - counter.base) / 1000 % 60
                elapsedTime += 1000
                Log.d(TAG, "onChronometerTick: $minutes : $seconds")
            }
        }
        counter.start()

    }

   private fun retrieveMessage() {
        cislo = intent.getStringExtra("cislo").toString()
    }

    private fun switchActivities(cislo: String) {
        val switchActivityIntent = Intent(this, MainActivity::class.java)
        switchActivityIntent.putExtra("cislo", cislo)

        startActivity(switchActivityIntent)
    }
}