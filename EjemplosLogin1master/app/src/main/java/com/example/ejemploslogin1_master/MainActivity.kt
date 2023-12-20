package ejemploslogin1_master

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.ejemploslogin1_master.PostLoginActivity
import com.example.ejemploslogin1_master.R
import com.example.ejemploslogin1_master.databinding.ActivityMainBinding
import com.example.ejemploslogin1_master.databinding.ActivityPostLoginBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {

    //ViewBinding
    private lateinit var binding: ActivityMainBinding

    //Fireabse auth
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializar firebase con auth
        auth = Firebase.auth


        //El login se activa cuando hace click al boton
        binding.btnLogin.setOnClickListener {

            //Recuperar el correo y contraseña
            val correo = binding.etEmail.text.toString()
            val contrasena = binding.etPassword.text.toString()

            if (correo.isEmpty()) {
                binding.etEmail.error = "Ingrese un correo"
                return@setOnClickListener
            }
            if (contrasena.isEmpty()) {
                binding.etPassword.error = "Ingrese una constraseña"
            }

            signIn(correo, contrasena)


        }

    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_LONG).show()
                    val intent = Intent(this, PostLoginActivity::class.java)
                    startActivity(intent)

                } else {
                    Toast.makeText(this, "ERROR", Toast.LENGTH_LONG).show()
                }
            }
    }

    //Menu barra izquierda superior
    lateinit var drawerLayout: DrawerLayout
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_login)
        drawerLayout = findViewById(R.id.drawer_layout)

        //Tolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, HomeFragment()).commit()

                R.id.nav_data -> supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, AboutFragment()).commit()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        )
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.nav_home)
        }

    }

    private fun AboutFragment(): Fragment {

        return TODO("Provide the return value")
    }

    class HomeFragment() : Fragment(), Parcelable {
        constructor(parcel: Parcel) : this() {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {

        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<HomeFragment> {
            override fun createFromParcel(parcel: Parcel): HomeFragment {
                return HomeFragment(parcel)
            }

            override fun newArray(size: Int): Array<HomeFragment?> {
                return arrayOfNulls(size)
            }
        }

    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }

}
