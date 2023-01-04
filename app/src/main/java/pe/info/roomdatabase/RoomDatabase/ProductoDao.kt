package pe.info.roomdatabase.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductoDao {

  @Insert
  fun insertar(producto: Producto)

  @Query("SELECT * FROM productos WHERE productoNombre = :nombre")
  fun buscarProductoPorNombre(nombre: String):List<Producto>

  @Query("DELETE FROM productos WHERE productoId = :id")
  fun borrarProducto(id: Int)

  @Query("SELECT * FROM productos")
  fun listarProductos():LiveData<List<Producto>>
}