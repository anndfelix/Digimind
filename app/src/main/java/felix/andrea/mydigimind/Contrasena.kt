package felix.andrea.mydigimind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Contrasena : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contrasena)

        val btn_restablecer: Button = findViewById(R.id.btn_restablecer)

        btn_restablecer.setOnClickListener {
            validaCorreo()
        }

    }


    private fun validaCorreo() {

        val et_correo: EditText = findViewById(R.id.correoElectronico)
        var correo: String = et_correo.text.toString()

        if(!correo.isNullOrBlank()){

        }else{
            Toast.makeText(this,"Ingresar datos", Toast.LENGTH_SHORT).show()
        }
    }

}