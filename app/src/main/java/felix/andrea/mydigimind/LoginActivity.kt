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

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        val btn_Registrar: Button = findViewById(R.id.btn_registrarse)
        val contrasena: TextView = findViewById(R.id.tv_olvidasteContra)
        val btn_IniciarSesion: Button = findViewById(R.id.btn_iniciarSesion)

        btn_Registrar.setOnClickListener {
            val intent: Intent = Intent(this,Registrarse::class.java)
            startActivity(intent)
        }

        contrasena.setOnClickListener {
            val intent: Intent = Intent(this,Contrasena::class.java)
            startActivity(intent)
        }

        btn_IniciarSesion.setOnClickListener {
            validaSesion()
        }

    }

    private fun validaSesion() {

        val et_correo: EditText = findViewById(R.id.correoElectronico)
        val et_contra: EditText = findViewById(R.id.contraseña)

        var correo: String = et_correo.text.toString()
        var contra: String = et_contra.text.toString()

        if(!correo.isNullOrBlank() && !contra.isNullOrBlank()){
            ingresaFirebase(correo,contra)
        }else{
            Toast.makeText(this,"Ingresar datos",Toast.LENGTH_SHORT).show()
        }
    }

    private fun ingresaFirebase(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val intent: Intent = Intent(this,MainActivity::class.java)
                } else {
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }

    }

}