package it.diego.foxcoupon.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import it.diego.foxcoupon.database.model.Coupon

@Dao
interface CouponDAO {
    @Insert
    fun insert(note: Coupon)

    @Update
    fun update(note: Coupon)

    @Delete
    fun delete(note: Coupon)

    @Query("delete from coupon")
    fun deleteAllNotes()

    @Query("select * from coupon order by priority asc")
    fun getAllNotes(): LiveData<MutableList<Coupon>>
}