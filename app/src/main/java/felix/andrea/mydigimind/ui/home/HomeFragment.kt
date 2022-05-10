package felix.andrea.mydigimind.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ktx.Firebase
import felix.andrea.mydigimind.R
import felix.andrea.mydigimind.Task
import felix.andrea.mydigimind.databinding.FragmentHomeBinding
import com.google.firebase.firestore.ktx.firestore

class HomeFragment : Fragment() {

    companion object {
        var tasks = ArrayList<Task>()
        var first = true
    }

    val fs = Firebase.firestore

    var adaptador: TaskAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if(first){
            first= false
        }

        fs.collection("actividades").get().addOnSuccessListener { documents ->
            for(document in documents){
                if(!tasks.contains(document.toObject(Task::class.java))){
                    tasks.add(document.toObject(Task::class.java))
                }
            }
        }

        adaptador = TaskAdapter(root.context, tasks)
        val table : GridView = root.findViewById(R.id.gridView)
        table.adapter = adaptador
        return root
    }

    }

    class TaskAdapter : BaseAdapter{
        var tasks = ArrayList<Task>()
        var context: Context?= null

        constructor(context: Context, tasks: ArrayList<Task>){
            this.context = context
            this.tasks = tasks
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
           var recordatorio = tasks[p0]
           var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
           var vista = inflator.inflate(R.layout.task_view,null)

           var title: TextView= vista.findViewById(R.id.tv_title)
           var time: TextView= vista.findViewById(R.id.tv_time)
           var days: TextView= vista.findViewById(R.id.tv_days)

            title.setText(recordatorio.nombre)
            time.setText(recordatorio.tiempo)
            days.setText(recordatorio.dias.toString())

            return vista
        }

        override fun getItem(position: Int): Any {
            return tasks[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return tasks.size
        }

    }

