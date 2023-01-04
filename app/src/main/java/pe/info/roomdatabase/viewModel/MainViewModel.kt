package pe.info.roomdatabase.viewModel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pe.info.roomdatabase.RoomDatabase.Producto
import pe.info.roomdatabase.RoomDatabase.ProductosRoomDatabase
import pe.info.roomdatabase.repositorio.ProductosRepositorio

class MainViewModel(application: Application):ViewModel() {

  val listaProductos:LiveData<List<Producto>>
  private val repositorio:ProductosRepositorio
  val resultadosBusqueda: MutableLiveData<List<Producto>>

  init{
    val productoDb = ProductosRoomDatabase.getInstance(application)
    val productoDao = productoDb.productoDao()

    repositorio = ProductosRepositorio(productoDao)
    listaProductos = repositorio.listaProductos
    resultadosBusqueda = repositorio.resultadosBusqueda
  }

  fun insertarProducto(producto: Producto){
    repositorio.insertarProducto(producto)
  }

  fun buscarProducto(nombreProducto: String){
    repositorio.buscarProducto(nombreProducto)
  }

  fun borrarProducto(id: Int){
    repositorio.borrarProducto(id)
  }
}