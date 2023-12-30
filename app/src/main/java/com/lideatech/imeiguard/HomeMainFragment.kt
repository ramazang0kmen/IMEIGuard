package com.lideatech.imeiguard

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.lideatech.imeiguard.databinding.FragmentHomeMainBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

class HomeMainFragment : Fragment() {
    private lateinit var binding: FragmentHomeMainBinding
    private var sim1StatusActivate: Boolean = false
    private var sim2StatusActivate: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivity.DatabaseManager.init(requireContext())
        val devicesDb = MainActivity.DatabaseManager.getDevicesDb()

        val manufacturer = Build.MANUFACTURER
        val model = Build.MODEL

        val (sim1Active, sim2Active) = isSimActive(requireContext())
        var sim1Status: Int? = null
        var sim2Status: Int? = null
        var sim1Date: String? = null
        var sim2Date: String? = null

        if (sim1Active == false ){
            sim1Status = 0
            sim1Date = getCurrentDateTime()
        }else{
            sim1Status = 1
            sim1Date = null
        }
        if (sim2Active == false ){
            sim2Status = 0
            sim2Date = getCurrentDateTime()
        }else{
            sim2Status = 1
            sim2Date = null
        }

        setSimStatus(binding.resultSIM1StatusText, sim1Active)
        setSimStatus(binding.resultSIM2StatusText, sim2Active)

        binding.resultBrandText.text = manufacturer
        binding.resultModelText.text = model

        val cursor = devicesDb?.rawQuery("SELECT * FROM devices", null)
        val idColumnIndex = cursor?.getColumnIndex("id")
        val manufacturerColumnIndex = cursor?.getColumnIndex("manufacturer")
        val modelColumnIndex = cursor?.getColumnIndex("model")
        val sim1statusColumnIndex = cursor?.getColumnIndex("sim1status")
        val sim2statusColumnIndex = cursor?.getColumnIndex("sim2status")



        val checkQuery = "SELECT COUNT(*) FROM devices"
        val checkCursor = devicesDb?.rawQuery(checkQuery, null)

        if (checkCursor != null && checkCursor.moveToFirst()) {
            val rowCount = checkCursor.getInt(0)
            if (rowCount == 0) {
                // Veritabanında hiç veri yok, yeni kayıt yapabilirsiniz.
                devicesDb?.execSQL("INSERT INTO devices (manufacturer, model, sim1status, sim2status, sim1date, sim2date) VALUES ('$manufacturer', '$model', $sim1Status, $sim2Status, '$sim1Date', '$sim2Date')")
            }else{
                cursor?.moveToFirst()
                val exstingSim1Status = cursor?.getInt(sim1statusColumnIndex ?: -1) ?: -1
                val exstingSim2Status = cursor?.getInt(sim2statusColumnIndex ?: -1) ?: -1

                if (exstingSim1Status != sim1Status || exstingSim2Status != sim2Status){
                    devicesDb?.execSQL("UPDATE devices SET sim1status = $sim1Status, sim2status = $sim2Status, sim1date = $sim1Date, sim2date = $sim2Date WHERE id = ${cursor?.getInt(idColumnIndex ?: -1) ?: -1}")
                }
            }
        }

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        try {
            val userDate = dateFormat.parse(sim1Date)
            val calendar = Calendar.getInstance()
            calendar.time = userDate
            calendar.add(Calendar.YEAR, 1)
            val newDate = calendar.time

            val formattedNewDate = dateFormat.format(newDate)

            binding.sim1historydate.text = "1 numaralı SIM yuvanız $formattedNewDate tarihinde devre dışı kalacaktır."
            println("deneme $formattedNewDate")
        }catch (e:Exception){}

        //println(sim2Date)
        cursor?.close()
        checkCursor?.close()


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setSimStatus(textView: TextView, isActive: Boolean) {
        textView.text = if (isActive) "Aktif" else "Pasif"
        if (textView == binding.resultSIM1StatusText) {
            sim1StatusActivate = isActive
        } else if (textView == binding.resultSIM2StatusText) {
            sim2StatusActivate = isActive
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun isSimActive(context: Context): Pair<Boolean, Boolean> {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val simState1 = telephonyManager.getSimState(0)
            val simState2 = telephonyManager.getSimState(1)

            Pair(simState1 == TelephonyManager.SIM_STATE_READY, simState2 == TelephonyManager.SIM_STATE_READY)
        } else {
            val simState = telephonyManager.simState

            Pair(simState == TelephonyManager.SIM_STATE_READY, false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentDateTime(): String? {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val dateString = currentDateTime.format(formatter)
        return dateString
    }
}