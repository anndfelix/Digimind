package felix.andrea.mydigimind

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Registrarse : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        auth = Firebase.auth
    
        val btn_Registrar: Button = findViewById(R.id.btn_registrarse)
        val btn_IniciarSesion: TextView = findViewById(R.id.tv_iniciarSesion)

        btn_Registrar.setOnClickListener {
            validaRegistro()
        }

        btn_IniciarSesion.setOnClickListener {
            val intent: Intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }

}


    private fun validaRegistro() {

        val et_correo: EditText = findViewById(R.id.correoElectronico)
        val et_contra: EditText = findViewById(R.id.contraseña)
        val et_contra2: EditText = findViewById(R.id.contraseña2)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()
        var contra2: String = et_contra2.text.toString()

        if (!correo.isNullOrBlank() && !contra.isNullOrBlank() && !contra2.isNullOrBlank()) {

            if(contra == contra2){
                registrarFireBase(correo, contra)
            } else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun registrarFireBase(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Se creo correctamente el usuario")
                    val user = auth.currentUser
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

}