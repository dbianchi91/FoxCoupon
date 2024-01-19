package it.diego.foxcoupon.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import it.diego.foxcoupon.database.dao.CouponDAO
import it.diego.foxcoupon.database.model.Coupon
import it.diego.foxcoupon.database.utils.subscribeOnBackground
import java.text.SimpleDateFormat
import java.util.*


@Database(entities = [Coupon::class], version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun couponDao(): CouponDAO

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(ctx: Context): AppDatabase {
            if (instance == null)
                instance = Room.databaseBuilder(ctx.applicationContext, AppDatabase::class.java,
                        "note_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build()

            return instance!!

        }

        private val roomCallback = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        private fun Date.dateToString(format: String): String {
            val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

            return dateFormatter.format(this)
        }



        private fun populateDatabase(db: AppDatabase) {
            val couponDao = db.couponDao()

            val timestamp = Date()
            val date = timestamp.dateToString("dd-MM-yyyy")

            subscribeOnBackground {
                couponDao.insert(Coupon("COUPON1", 1, 1, "Lorem ipsum dolor sit amet, consectetur adipiscing elit", date))
                couponDao.insert(Coupon("COUPON2", 1, 2, "Lorem ipsum dolor sit amet, consectetur adipiscing elit)", date))
                couponDao.insert(Coupon("COUPON3", 1, 3, "Lorem ipsum dolor sit amet, consectetur adipiscing elit", date))

            }
        }
    }

}