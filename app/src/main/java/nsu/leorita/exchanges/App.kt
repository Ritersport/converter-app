package nsu.leorita.exchanges

import android.app.Application
import androidx.room.Room
import nsu.leorita.exchanges.data.services.RangeServiceImpl
import nsu.leorita.exchanges.domain.room.AppDatabase

class App : Application() {
    val rangesService = RangeServiceImpl()
    var db: AppDatabase? = null

    override fun onCreate() {
        super.onCreate()
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app-db"
        ).build()
    }
}