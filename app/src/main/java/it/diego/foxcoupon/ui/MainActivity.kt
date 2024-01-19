package it.diego.foxcoupon.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import it.diego.foxcoupon.R


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 123


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))


           ActivityCompat.requestPermissions(
               this,
               arrayOf(Manifest.permission.SEND_SMS),
               PERMISSION_REQUEST_CODE
           )


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            openCouponList();
        }


    }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    openCouponList();
                    Toast.makeText(this,
                        "Result: OK", Toast.LENGTH_LONG).show()

                } else {
                    Toast.makeText(this,
                        "Result: FAILED", Toast.LENGTH_LONG).show()
                }
            }
        }
    }




    fun openCouponList() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent);
    }
}