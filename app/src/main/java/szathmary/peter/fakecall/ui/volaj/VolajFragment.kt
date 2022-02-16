package szathmary.peter.fakecall.ui.volaj

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import szathmary.peter.fakecall.MainActivity
import szathmary.peter.fakecall.PrichadzajuciHovorActivity
import szathmary.peter.fakecall.R
import szathmary.peter.fakecall.databinding.FragmentVolajBinding


class VolajFragment : Fragment() {

    private var delayInMilliseconds: Long = 2000

    private lateinit var meno: String
    private var cislo = "Zle zadane cislo!"
    private lateinit var callActionButton: FloatingActionButton
    private lateinit var cisloEdit: EditText
    private lateinit var cisloTextView: TextView
    private lateinit var menoTextEdit: EditText
    private lateinit var timePicker: TimePicker
    private var hodiny: Int = 0
    private var minuty: Int = 1


    private var _binding: FragmentVolajBinding? = null

    private var timeListener: TimePicker.OnTimeChangedListener =
        TimePicker.OnTimeChangedListener { _, _, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hodiny = timePicker.hour * 3_600_000
                minuty = timePicker.minute * 60_000
            }
            delayInMilliseconds = (hodiny + minuty).toLong()
        }

    private var textWatcherMeno: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun afterTextChanged(p0: Editable?) {
            meno = menoTextEdit.text.toString()
        }

    }
    private var textWatcherCislo: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            return
        }

        override fun afterTextChanged(p0: Editable?) {
            cislo = PhoneNumberUtils.formatNumber(cisloEdit.text.toString())
            callActionButton.isClickable = true
        }

    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentVolajBinding.inflate(inflater, container, false)

        return binding.root
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AK MAS FRAGMENT, PRACUJ S UI AZ V TEJTO METODE
        super.onViewCreated(view, savedInstanceState)

        timePicker = view.findViewById(R.id.timePicker)
        timePicker.setIs24HourView(true)
        timePicker.hour = 0
        timePicker.minute = 0
        timePicker.setOnTimeChangedListener(timeListener)
        cisloEdit = view.findViewById(R.id.zadavanieCisla)
        cisloEdit.addTextChangedListener(textWatcherCislo)
        cisloTextView = view.findViewById(R.id.cisloText)
        callActionButton = view.findViewById(R.id.callActionButton)
        menoTextEdit = view.findViewById(R.id.menoTextEdit)
        menoTextEdit.addTextChangedListener(textWatcherMeno)




        checkForPreferences()
        //TAKTO POUZIVAJ LISTENERY
        callActionButton.setOnClickListener {
            //Skontrolujem ci je cislo spravne, ak ano, zobrazim do textEdit na ake cislo volam
            //pockam 3 sekundy a prepinam aktivitu na hovor
            //inac do textEdit napisem ze je zadane zle cislo
            if (!isValidPhone(cislo)) {
                cisloTextView.text = getString(R.string.zle_zadane_cislo)
            } else if (menoTextEdit.text.isEmpty() || menoTextEdit.text.isBlank()) {
                cisloTextView.text = getString(R.string.zadaj_meno_volajuceho)
            } else {
                cisloTextView.text = getString(R.string.waitingInMinutes, delayInMilliseconds / 60_000)
                switchToPrichadzajuciHovorActivityWithDelay(delayInMilliseconds)
            }
        }

    }

    private fun checkForPreferences() {
        val pref = requireActivity().applicationContext.getSharedPreferences(
            "MyPref",
            0
        ) // 0 - for private mode
        if (pref.contains("meno") && pref.contains("cislo")) {
            cislo = pref.getString("cislo", null).toString()
            cisloEdit.setText(cislo)
            meno = pref.getString("meno", null).toString()
            menoTextEdit.setText(meno)
        }
    }

    private fun switchToPrichadzajuciHovorActivityWithDelay(milliseconds: Long) {
        val mainActivity: MainActivity = activity as MainActivity

//        Handler(Looper.getMainLooper()).postDelayed(
//            {
//                val switchActivityIntent =
//                    Intent(mainActivity, PrichadzajuciHovorActivity::class.java)
//                switchActivityIntent.putExtra("cislo", cislo)
//                switchActivityIntent.putExtra("meno", meno)
//                startActivity(switchActivityIntent)
//            },
//            milliseconds // value in milliseconds
//        )

        GlobalScope.launch { // launch new coroutine in background and continue
            delay(milliseconds) // non-blocking delay for 1 second (default time unit is ms)
            val switchActivityIntent =
                Intent(mainActivity, PrichadzajuciHovorActivity::class.java)
            switchActivityIntent.putExtra("cislo", cislo)
            switchActivityIntent.putExtra("meno", meno)
            startActivity(switchActivityIntent)
        }
    }

    private fun isValidPhone(phone: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(phone) || phone.toString().length < 10) {
            false
        } else {
            PhoneNumberUtils.isGlobalPhoneNumber(phone.toString())
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}