package nsu.leorita.exchanges.ui.currenciesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nsu.leorita.exchanges.data.room.entities.CurrencyDbEntity
import nsu.leorita.exchanges.data.services.RangeServiceImpl
import nsu.leorita.exchanges.domain.model.Currency
import nsu.leorita.exchanges.domain.room.AppDatabase
import nsu.leorita.exchanges.domain.services.RangesService

class CurrenciesListViewModel(
    private val rangesService: RangesService,
    private val db: AppDatabase,
) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> = _currencies

    private val subscriptions: CompositeDisposable = CompositeDisposable()

    init {
        loadCurrenciesFromDb()
        if (_currencies.value?.isEmpty() == true) {
            loadCurrenciesFromWeb()
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscriptions.dispose()
    }


    fun loadCurrenciesFromWeb() {
         rangesService.getRanges()
            .map { it.ranges.values }
            .map { currencies ->
                currencies.map {
                    CurrencyDbEntity(
                        it.code,
                        it.name,
                        it.denomination,
                        it.value,
                        it.previousValue,
                    )
                }
            }
            .flatMapCompletable { dbEntities -> db.getCurrenciesDao().insertAll(dbEntities) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                { error ->
                    Log.e("ranges", error.message + ": web service error")
                }
            ).also { subscriptions.add(it) }
    }

    fun loadCurrenciesFromDb() {
        db.getCurrenciesDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ it ->
                db.getCurrenciesDao().insertAll(it)
                _currencies.value = it.map { Currency(it.code, it.name, it.denomination, it.value, it.previousValue) }
            },
        {
                t -> Log.e("ranges", t.message ?: "database error")
        }).also { subscriptions.add(it) }
    }
}