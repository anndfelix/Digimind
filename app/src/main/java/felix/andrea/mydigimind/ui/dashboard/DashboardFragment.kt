package felix.andrea.mydigimind.ui.dashboard

import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import felix.andrea.mydigimind.R
import felix.andrea.mydigimind.Task
import felix.andrea.mydigimind.databinding.FragmentDashboardBinding
import felix.andrea.mydigimind.ui.home.HomeFragment
import java.text.SimpleDateFormat
import java.util.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null

    val fs = Firebase.firestore

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)

        val root: View = binding.root
        val btnSetTime: Button = root.findViewById(R.id.btn_time) as Button
        val btnAdd = root.findViewById(R.id.btn_add) as Button
        val toDo = root.findViewById(R.id.to_do) as EditText
        val check_monday = root.findViewById(R.id.check_monday) as CheckBox
        val check_tuesday = root.findViewById(R.id.check_tuesday) as CheckBox
        val check_wednesday = root.findViewById(R.id.check_wednesday) as CheckBox
        val check_thursday = root.findViewById(R.id.check_thursday) as CheckBox
        val check_friday = root.findViewById(R.id.check_friday) as CheckBox
        val check_saturday = root.findViewById(R.id.check_saturday) as CheckBox
        val check_sunday = root.findViewById(R.id.check_sunday) as CheckBox

        btnSetTime.setOnClickListener(){
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)

                btnSetTime.text= SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(root.context,timeSetListener,cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),true).show()
        }

        btnAdd.setOnClickListener{
            var descriptionToDo = toDo.text.toString()
            var time = btnSetTime.text.toString()
            var days = ArrayList<String>()

            if(check_monday.isChecked){
                days.add("Monday")
            }
            if(check_tuesday.isChecked){
                days.add("Tuesday")
            }
            if(check_wednesday.isChecked){
                days.add("Wednesday")
            }
            if(check_thursday.isChecked){
                days.add("Thursday")
            }
            if(check_friday.isChecked){
                days.add("Friday")
            }
            if(check_saturday.isChecked){
                days.add("Saturday")
            }
            if(check_sunday.isChecked){
                days.add("Sunday")
            }

            if (!time.isBlank() && !time.isEmpty() && time != "Set Time" && time != "Set time") {

                var recordatorio = Task(days,time,descriptionToDo)

                fs.collection("actividades").add(recordatorio)
                    .addOnSuccessListener { documentReference ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG,  "Error adding document", e)
                    }

                Toast.makeText(root.context, "New Task Added", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(root.context, "Select time", Toast.LENGTH_SHORT).show()
            }

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}