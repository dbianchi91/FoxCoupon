package it.diego.foxcoupon.database.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import it.diego.foxcoupon.database.utils.CouponRepository

class CouponViewModels(app: Application) : AndroidViewModel(app) {

    private val repository = CouponRepository(app)
    private val allNotes = repository.getAllNotes()

    fun insert(note: Coupon) {
        repository.insert(note)
    }

    fun update(note: Coupon) {
        repository.update(note)
    }

    fun delete(note: Coupon) {
        repository.delete(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }

    fun getAllNotes(): LiveData<MutableList<Coupon>> {
        return allNotes
    }


}