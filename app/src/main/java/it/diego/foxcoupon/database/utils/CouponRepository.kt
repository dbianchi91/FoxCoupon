package it.diego.foxcoupon.database.utils

import android.app.Application
import androidx.lifecycle.LiveData
import it.diego.foxcoupon.database.AppDatabase
import it.diego.foxcoupon.database.dao.CouponDAO
import it.diego.foxcoupon.database.model.Coupon

class CouponRepository(application: Application) {

    private var couponDao: CouponDAO
    private var allNotes: LiveData<MutableList<Coupon>>

    private val database = AppDatabase.getInstance(application)

    init {
        couponDao = database.couponDao()
        allNotes = couponDao.getAllNotes()
    }

    fun insert(note: Coupon) {
//        Single.just(noteDao.insert(note))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
        subscribeOnBackground {
            couponDao.insert(note)
        }
    }

    fun update(note: Coupon) {
        subscribeOnBackground {
            couponDao.update(note)
        }
    }

    fun delete(note: Coupon) {
        subscribeOnBackground {
            couponDao.delete(note)
        }
    }

    fun deleteAllNotes() {
        subscribeOnBackground {
            couponDao.deleteAllNotes()
        }
    }

    fun getAllNotes(): LiveData<MutableList<Coupon>> {
        return allNotes
    }

}