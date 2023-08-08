package nsu.leorita.exchanges.data.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import nsu.leorita.exchanges.domain.model.Currency

@Entity(
        tableName = "currencies",
        indices = [
                Index("name", unique = true)
        ]
)
data class CurrencyDbEntity (
    @PrimaryKey(autoGenerate = false) val code: String,
    val name: String,
    val denomination: Int,
    val value: Float,
    val previousValue: Float,
        ){
        fun toCurrency(): Currency = Currency(code, name, denomination, value, previousValue)
}