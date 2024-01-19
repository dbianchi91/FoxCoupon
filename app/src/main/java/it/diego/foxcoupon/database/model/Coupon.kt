package it.diego.foxcoupon.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import it.diego.foxcoupon.database.utils.DateConverter
import java.util.Date

@Entity(tableName = "coupon")
data class Coupon(
        var numeroCoupon: String,
        var statoCoupon: Int,
        var priority: Int,
        var descrizione: String,
        var dataUtilizzo: String,
                  @PrimaryKey(autoGenerate = false) val id: Int? = null)


