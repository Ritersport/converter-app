package nsu.leorita.exchanges.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import nsu.leorita.exchanges.data.room.entities.CurrencyDbEntity

@Dao
interface CurrenciesDao {

    @Query("SELECT * FROM currencies")
    fun getAll(): Observable<List<CurrencyDbEntity>>

    @Query("SELECT * FROM currencies WHERE code = :code")
    fun getByCode(code: String?): Observable<CurrencyDbEntity?>

    @Insert
    fun insertAll(currencies: List<CurrencyDbEntity>): Completable


}