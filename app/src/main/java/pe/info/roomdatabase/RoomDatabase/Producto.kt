package pe.info.roomdatabase.RoomDatabase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "productos")
class Producto {

  @PrimaryKey(autoGenerate = true)
  @NonNull
  @ColumnInfo(name = "productoId")
  var idProducto = 0

  @ColumnInfo(name = "productoNombre")
  var nombreProducto = ""

  @ColumnInfo(name = "productoCantidad")
  var cantidadProducto = 0

  constructor(){}

  constructor(idProducto: Int
              , nombreProducto: String
              ,cantidadProducto: Int){
    this.idProducto = idProducto
    this.nombreProducto = nombreProducto
    this.cantidadProducto = cantidadProducto
  }
  constructor(nombreProducto:String
              ,cantidadProducto: Int){
    this.nombreProducto = nombreProducto
    this.cantidadProducto = cantidadProducto
  }
}