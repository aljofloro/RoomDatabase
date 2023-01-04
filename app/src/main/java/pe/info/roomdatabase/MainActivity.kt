package pe.info.roomdatabase

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import pe.info.roomdatabase.RoomDatabase.Producto
import pe.info.roomdatabase.ui.theme.RoomDatabaseTheme
import pe.info.roomdatabase.viewModel.MainViewModel
import pe.info.roomdatabase.viewModel.MainViewModelFactory

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RoomDatabaseTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
          val owner = LocalViewModelStoreOwner.current
          owner?.let{
            val viewModel:MainViewModel = viewModel(it
              , "MainViewModel"
              , MainViewModelFactory(LocalContext.current.applicationContext as Application))
            ScreenSetup(viewModel = viewModel)
          }
        }
      }
    }
  }
}

@Composable
fun CustomTextField(
  title: String,
  textState: String,
  onTextChange: (String)->Unit,
  keyboardType:KeyboardType
){
  OutlinedTextField(value = textState
    , onValueChange = {onTextChange(it)}
    , keyboardOptions = KeyboardOptions(
      keyboardType = keyboardType)
    , singleLine = true
    , label = { Text(title)}
    , modifier = Modifier.padding(10.dp)
    , textStyle = TextStyle(fontWeight = FontWeight.Bold
      , fontSize = 30.sp)
  )
}

@Composable
fun ProductRow(id:Int, nombre:String,cantidad:Int){
  Row(modifier = Modifier
    .padding(5.dp)
    .fillMaxWidth()) {
    Text(text = id.toString()
      , modifier = Modifier.weight(0.1f))
    Text(text = nombre
      , modifier = Modifier.weight(0.2f))
    Text(text = cantidad.toString()
      , modifier = Modifier.weight(0.2f))
  }
}

@Composable
fun TitleRow(head1:String,head2:String,head3:String){
  Row(modifier = Modifier
    .padding(5.dp)
    .fillMaxWidth()
    .background(MaterialTheme.colors.primary)) {
    Text(text = head1
      , modifier = Modifier.weight(0.1f)
      , color = Color.White)
    Text(text = head2
      , modifier = Modifier.weight(0.2f)
      , color = Color.White)
    Text(text = head3
      , modifier = Modifier.weight(0.2f)
      , color = Color.White)
  }
}

@Composable
fun ScreenSetup(viewModel: MainViewModel){
  val listaProductos by viewModel.listaProductos.observeAsState(listOf())
  val buscarProductos by viewModel.resultadosBusqueda.observeAsState(listOf())

  MainScreen(listaProductos = listaProductos
    , buscarProducto = buscarProductos
    , viewModel = viewModel)
}

@Composable
fun MainScreen(listaProductos:List<Producto>,
buscarProducto:List<Producto>,
viewModel:MainViewModel){

  var nombreProducto by remember{ mutableStateOf("") }
  var cantidadProducto by remember { mutableStateOf("")}
  var buscando by remember{mutableStateOf(false)}

  val onProductoTextChange={text:String->nombreProducto = text}
  val onProductoCantidadChange = {text:String->cantidadProducto = text}

  Column(horizontalAlignment = Alignment.CenterHorizontally
    , modifier = Modifier.fillMaxWidth()) {

    CustomTextField(
      title = "Nombre del Producto",
      textState = nombreProducto,
      onTextChange = onProductoTextChange,
      keyboardType = KeyboardType.Text
    )

    CustomTextField(title = "Cantidad"
      , textState = cantidadProducto
      , onTextChange = onProductoCantidadChange,
      keyboardType = KeyboardType.Number )

    Row(horizontalArrangement = Arrangement.SpaceEvenly
      , modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {

      Button(onClick = {
        if(cantidadProducto.isNotEmpty()){
          viewModel
            .insertarProducto(Producto(nombreProducto
              ,cantidadProducto.toInt()))
          buscando = false
        }
      }) {
        Text("Agregar")
      }

      Button(onClick = {
        if(nombreProducto.isNotEmpty()){
          buscando = true
          viewModel.buscarProducto(nombreProducto)
        }
      }) {
        Text(text = "Buscar")
      }

      Button(onClick = {
        if(nombreProducto.isNotEmpty()){
          buscando = false
          viewModel.borrarProducto(nombreProducto.toInt())
        }
      }) {
        Text("Eliminar")
      }

      Button(onClick = {
        buscando = false
        nombreProducto = ""
        cantidadProducto = ""
      }) {
        Text(text = "Limpiar")
      }
    }

    LazyColumn(
      Modifier
        .fillMaxWidth()
        .padding(10.dp)){
      val lista = if(buscando)buscarProducto else listaProductos

      item {
        TitleRow(head1 = "ID"
          , head2 = "Producto"
          , head3 = "Cantidad" )
      }

      items(lista){ producto->
        ProductRow(id = producto.idProducto
          , nombre = producto.nombreProducto
          , cantidad = producto.cantidadProducto)
      }
    }
  }
}