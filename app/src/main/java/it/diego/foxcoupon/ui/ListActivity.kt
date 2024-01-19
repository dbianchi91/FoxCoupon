package it.diego.foxcoupon.ui

import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import it.diego.foxcoupon.R
import it.diego.foxcoupon.database.model.Coupon
import it.diego.foxcoupon.database.model.CouponViewModels
import kotlinx.android.synthetic.main.list_activity.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


const val ADD_NOTE_REQUEST = 1
const val EDIT_NOTE_REQUEST = 2

class ListActivity : AppCompatActivity() , OnCouponSyncButtonClickedListener{

    private lateinit var vm: CouponViewModels
    private lateinit var couponRecyclerViewAdapter : CouponRecyclerViewAdapter
    private lateinit var rv: RecyclerView
    private var listaCoupon : MutableList<Coupon> =  mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_activity)


      try {

          recycler_view_coupon.layoutManager = LinearLayoutManager(this)
          recycler_view_coupon.setHasFixedSize(true)

          couponRecyclerViewAdapter = CouponRecyclerViewAdapter(this, listaCoupon, this)

          recycler_view_coupon.adapter = couponRecyclerViewAdapter

          vm = ViewModelProvider(this).get(CouponViewModels::class.java)

          vm.getAllNotes().observe(this, Observer {
              listaCoupon = it
              couponRecyclerViewAdapter.aggiornaCoupons(listaCoupon)

          })


      } catch (e: Exception) {
          Toast.makeText(this, e.message, Toast.LENGTH_LONG)
      }


    }


    override fun onCouponSyncButtonClicked(coupon: Coupon?) {
         val builder = AlertDialog.Builder(this)
         builder.setTitle(coupon?.numeroCoupon)
         builder.setMessage(coupon?.descrizione)

         builder.setPositiveButton("Utilizza") { dialog, which ->

             if (coupon != null) {
                 val today: LocalDate = LocalDate.now()
                 val formattedDate: String = today.format(DateTimeFormatter.ofPattern("dd-MMM-yy"))
                 coupon?.dataUtilizzo = "Utilizzato il $formattedDate"
                 coupon?.statoCoupon = 2
                 for(c in listaCoupon){
                     if(c.id == coupon.id){
                         listaCoupon[listaCoupon.indexOf(c)] =  coupon
                     }
                 }
                 vm.update(coupon)
                 couponRecyclerViewAdapter.aggiornaCoupons(listaCoupon)
                 sendSMS(listaCoupon, coupon)

             }
         }

         builder.setNegativeButton("Annulla") { dialog, which ->
             dialog.dismiss()
         }


         builder.show()
    }


    private fun sendSMS(listaCoupon : MutableList<Coupon>, coupon: Coupon){

        val number: String = "33XXXXXXX9"
        val msg: String = "E' stato scelto il coupon " + coupon.numeroCoupon
        try {
            val smsManager: SmsManager = SmsManager.getDefault()

            smsManager.sendTextMessage(number, null, msg, null, null)

            Toast.makeText(
                applicationContext,
                "Messagio inviato, hai utilizzato il coupon " + coupon.numeroCoupon,
                Toast.LENGTH_LONG
            ).show()
        } catch (e: java.lang.Exception) {

            for(c in listaCoupon){
                if(c.id == coupon.id){
                    listaCoupon[listaCoupon.indexOf(c)].statoCoupon = 1
                }
            }
            vm.update(coupon)
            couponRecyclerViewAdapter.aggiornaCoupons(listaCoupon)
            Toast.makeText(applicationContext, "Si è verificato un errore", Toast.LENGTH_LONG).show()
        }
    }
}

