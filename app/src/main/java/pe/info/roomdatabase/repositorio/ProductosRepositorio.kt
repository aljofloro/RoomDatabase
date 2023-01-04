package pe.info.roomdatabase.repositorio

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import pe.info.roomdatabase.RoomDatabase.Producto
import pe.info.roomdatabase.RoomDatabase.ProductoDao

class ProductosRepositorio(private val productoDao: ProductoDao) {

  val resultadosBusqueda = MutableLiveData<List<Producto>>()
  private val coroutineScope = CoroutineScope(Dispatchers.Main)
  val listaProductos:LiveData<List<Producto>>
  = productoDao.listarProductos()

  fun insertarProducto(nuevoProducto: Producto){
    coroutineScope.launch(Dispatchers.IO) {
      productoDao.insertar(nuevoProducto)
    }
  }
  fun borrarProducto(idProducto: Int){
    coroutineScope.launch(Dispatchers.IO) {
      productoDao.borrarProducto(idProducto)
    }
  }

  fun buscarProducto(nombreProducto: String){
    coroutineScope.launch(Dispatchers.IO) {
      productoDao.buscarProductoPorNombre(nombreProducto)
    }
  }

  private fun asyncBuscarProducto(nombreProducto: String):
      Deferred<List<Producto>?> =
    coroutineScope.async(Dispatchers.IO) {
      return@async productoDao.buscarProductoPorNombre(nombreProducto)
    }

}