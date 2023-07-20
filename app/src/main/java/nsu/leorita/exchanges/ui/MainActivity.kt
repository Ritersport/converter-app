package nsu.leorita.exchanges.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import nsu.leorita.exchanges.adapters.CurrencyAdapter
import nsu.leorita.exchanges.data.room.entities.CurrencyDbEntity
import nsu.leorita.exchanges.data.services.RangeServiceImpl
import nsu.leorita.exchanges.databinding.ActivityMainBinding
import nsu.leorita.exchanges.domain.model.Currency
import nsu.leorita.exchanges.domain.room.AppDatabase


class MainActivity : AppCompatActivity() {
    private val rangeService = RangeServiceImpl()
    private val adapter = CurrencyAdapter { data ->
        onCurrencyClicked(data)
    }
    private var disposableDb: Disposable? = null
    private var disposableWeb: Disposable? = null
    private var db: AppDatabase? = null

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = requireNotNull(_binding)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app-db"
        ).build()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = layoutManager
        binding.swiperefresh.setOnRefreshListener {
            getRangesFromWeb()
            binding.swiperefresh.isRefreshing = false
        }
        getRangesFromDb()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableWeb?.dispose()
        disposableDb?.dispose()
    }

    private fun onCurrencyClicked(data: Currency) {
        val recycleItemIntent = Intent(this, ConvertActivity::class.java)
        recycleItemIntent.putExtra("currencyName", data.name)
        recycleItemIntent.putExtra("currencyValue", data.getRange())
        startActivity(recycleItemIntent)
    }

    private fun getRangesFromWeb() {
        disposableWeb = rangeService.getRanges()
            .map { it.ranges.values }
            .map { currencyCollection ->
                currencyCollection.map {
                    CurrencyDbEntity(
                        it.code,
                        it.name,
                        it.denomination,
                        it.value
                    )
                }
            }
            .flatMapCompletable { dbEntities -> db?.getCurrenciesDao()?.insertAll(dbEntities) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                },
                {
                    error -> Log.e("ranges", error.message + ": web service error")
                }
            )
    }

    private fun getRangesFromDb() {
        val currenciesDao = db?.getCurrenciesDao()
        val currencies: ArrayList<Currency> = ArrayList()
        val currencyEntities: ArrayList<CurrencyDbEntity> = ArrayList()
        disposableDb = currenciesDao?.getAll()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({t ->
                currencyEntities.addAll(t)
                currencyEntities.forEach {
                    currencies.add(Currency(it.code, it.name, it.denomination, it.value))
                    adapter.data = currencies
                }
            },
                { t -> Log.e("ranges", t.message ?: "database error")})
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

}