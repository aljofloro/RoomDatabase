package pe.info.roomdatabase.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [(Producto::class)], version = 1)
abstract class ProductosRoomDatabase:RoomDatabase() {

  abstract fun productoDao():ProductoDao

  companion object{
    private var INSTANCE: ProductosRoomDatabase? = null

    fun getInstance(context: Context): ProductosRoomDatabase{
      var instance = INSTANCE
      if(instance == null){
        instance = Room.databaseBuilder(context.applicationContext
          ,ProductosRoomDatabase::class.java
          ,"productos_database").fallbackToDestructiveMigration()
          .build()
        INSTANCE = instance
      }
      return instance
    }
  }
}