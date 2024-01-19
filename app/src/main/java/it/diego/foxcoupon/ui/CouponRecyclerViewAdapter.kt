package it.diego.foxcoupon.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult.NO_POSITION
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.diego.foxcoupon.R
import it.diego.foxcoupon.database.model.Coupon

//class CouponRecyclerViewAdapter(private val onItemClickListener: OnCouponSyncButtonClickedListener)
 // : ListAdapter<Coupon, CouponRecyclerViewAdapter.CouponHolder>(diffCallback)  {

class CouponRecyclerViewAdapter(private val context: Context,
                                private var listaCoupon: MutableList<Coupon>,
                                private val onItemClickListener: OnCouponSyncButtonClickedListener
) : RecyclerView.Adapter<CouponRecyclerViewAdapter.CouponHolder>() {

val VISIBLE = 0
    val INVISIBLE = 4
   // lateinit var listaCoupon : MutableList<Coupon>

    fun getCouponAt(position: Int) = listaCoupon.get(position)

    fun aggiornaCoupons(coupons: MutableList<Coupon>) {
        listaCoupon = coupons
        notifyDataSetChanged()
    }


    class CouponHolder(iv: View) : RecyclerView.ViewHolder(iv) {

        val tvTitle: TextView = itemView.findViewById(R.id.textViewNumeroCoupon)
        val button_use: ImageButton = itemView.findViewById(R.id.infoButton)
        val dataUtilizzo: TextView = itemView.findViewById(R.id.textViewDataOraSincornizzazione)




    }

    override fun getItemCount(): Int {
        return listaCoupon.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.coupn_row, parent,
                false)
        return CouponHolder(itemView)
    }

    override fun onBindViewHolder(holder: CouponHolder, position: Int) {
        var coupon : Coupon = listaCoupon.get(position)
       // with(getItem(position)) {
            holder.tvTitle.text = coupon.numeroCoupon
            holder.dataUtilizzo.text = coupon.dataUtilizzo
            if(coupon.statoCoupon == 1) {
                holder.dataUtilizzo.visibility = INVISIBLE
                holder.button_use.visibility = VISIBLE
            }else{
                holder.dataUtilizzo.visibility = VISIBLE
                holder.button_use.visibility = INVISIBLE
            }
            holder.button_use.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    //onItemClickListener(getItem(position))
                      onItemClickListener.onCouponSyncButtonClicked(coupon)
                }
            })

        }
    }






interface OnCouponSyncButtonClickedListener {
    fun onCouponSyncButtonClicked(coupon: Coupon?)
}

private val diffCallback = object : DiffUtil.ItemCallback<Coupon>() {
    override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon) =
        oldItem.numeroCoupon == newItem.numeroCoupon
                && oldItem.statoCoupon == newItem.statoCoupon
                && oldItem.priority == newItem.priority
}

