package szathmary.peter.fakecall.ui.home

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import szathmary.peter.fakecall.MainActivity
import szathmary.peter.fakecall.R
import szathmary.peter.fakecall.databinding.FragmentVolajBinding
import java.util.*


class VolajFragment() : Fragment() {

    private lateinit var meno: String
    private var cislo = "Zle zadane cislo!"
    private lateinit var callActionButton: FloatingActionButton
    private lateinit var cisloEdit: EditText
    private lateinit var cisloTextView: TextView
    private lateinit var menoTextEdit: EditText


    private var _binding: FragmentVolajBinding? = null
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //AK MAS FRAGMENT, PRACUJ S UI AZ V TEJTO METODE
        super.onViewCreated(view, savedInstanceState)
        cisloEdit = view.findViewById(R.id.zadavanieCisla)
        cisloEdit.addTextChangedListener(textWatcherCislo)
        cisloTextView = view.findViewById(R.id.cisloText)
        callActionButton = view.findViewById(R.id.callActionButton)
        menoTextEdit = view.findViewById(R.id.menoTextEdit)
        menoTextEdit.addTextChangedListener(textWatcherMeno)

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
                cisloTextView.text = getString(R.string.volam_cislo, cislo)
                val mainActivity: MainActivity = activity as MainActivity

                Handler(Looper.getMainLooper()).postDelayed(
                    {
                        mainActivity.switchActivities(cislo, meno)
                    },
                    2000 // value in milliseconds
                )
            }
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