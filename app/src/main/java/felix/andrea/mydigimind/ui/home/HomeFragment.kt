package felix.andrea.mydigimind.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import felix.andrea.mydigimind.R
import felix.andrea.mydigimind.Recordatorio
import felix.andrea.mydigimind.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    var adaptador: RecordatorioAdapter? = null
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    companion object {
        var recordatorios = ArrayList<Recordatorio>()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        adaptador = RecordatorioAdapter(root.context, recordatorios)
        val table : GridView = root.findViewById(R.id.gridView)
        table.adapter = adaptador


        return root
    }

    class RecordatorioAdapter : BaseAdapter{
        var recordatorios = ArrayList<Recordatorio>()
        var context: Context?= null

        constructor(context: Context, recordatorios: ArrayList<Recordatorio>){
            this.context = context
            this.recordatorios = recordatorios
        }

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
           var recordatorio = recordatorios[p0]
           var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
           var vista = inflator.inflate(R.layout.dashboard_item,null)

           var to_do: TextView= vista.findViewById(R.id.to_do)
           var time: TextView= vista.findViewById(R.id.time)
           var days: TextView= vista.findViewById(R.id.days)

            to_do.setText(recordatorio.nombre)
            time.setText(recordatorio.tiempo)
            days.setText(recordatorio.dias.toString())

            return vista
        }

        override fun getItem(position: Int): Any {
            return recordatorios[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return recordatorios.size
        }

    }


}