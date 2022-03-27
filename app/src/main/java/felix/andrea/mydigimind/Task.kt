package felix.andrea.mydigimind

import java.io.Serializable

data class Task (var dias: ArrayList<String>, var tiempo: String, var nombre: String) : Serializable {
}