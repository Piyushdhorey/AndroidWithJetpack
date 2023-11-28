package com.example.arpokemonmenu

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arpokemonmenu.ui.theme.ARPokemonMenuTheme
import com.example.arpokemonmenu.ui.theme.red
import com.example.arpokemonmenu.ui.theme.translucent
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.localRotation
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.ModelNode
import io.github.sceneview.node.Node

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ARPokemonMenuTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Box(modifier = Modifier.fillMaxSize()){
                        val currentModel = remember {
                            mutableStateOf("Pikachu")
                        }
                        ARScreen(currentModel.value)
                        Menu(modifier = Modifier.align(Alignment.BottomCenter)) {
                            currentModel.value = it
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun Menu(modifier: Modifier,
         onClick: (String) -> Unit
         ) {
    var currentIndex by remember {
        mutableStateOf(0)
    }

    val itemsList = listOf(
        Pokemon("Pikachu", R.drawable.pikachu, "Pikachu by Tipatat Chennavasin [CC-BY] via Poly Pizza"),
        Pokemon("Low Poly Bulbasaur", R.drawable.pokemonbulbasaur, "Low Poly Bulbasaur by Tipatat Chennavasin [CC-BY] via Poly Pizza"),
        Pokemon("Low Poly Charmander", R.drawable.charmender, "Low Poly Charmander by Tipatat Chennavasin [CC-BY] via Poly Pizza"),
        Pokemon("Mew", R.drawable.mew, "Mew by Tipatat Chennavasin [CC-BY] via Poly Pizza"),
        Pokemon("Bellsprout", R.drawable.bellsprout, "")
    )

    fun updateIndex(offset: Int) {
        currentIndex = (currentIndex + offset + itemsList.size) % itemsList.size
        onClick(itemsList[currentIndex].name)
    }

    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
        )
    {
        IconButton(onClick = {
            updateIndex(-1)
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_navigate_before_24), contentDescription = "previous")
        }

        CircularImage(imageId = itemsList[currentIndex].imageId)

        IconButton(onClick = {
            updateIndex(1)
        }) {
            Icon(painter = painterResource(id = R.drawable.baseline_navigate_next_24), contentDescription = "next")
        }
    }
}

@Composable
fun CircularImage(
    modifier: Modifier = Modifier,
    imageId: Int,
//    imageName: String
) {
    Box(modifier = modifier
        .size(140.dp)
        .clip(CircleShape)
        .border(width = 3.dp, red, CircleShape)
    ) {
        Image(painter = painterResource(id = imageId),
            contentDescription = null,
            modifier = modifier.size(140.dp),
            contentScale = ContentScale.FillBounds)
    }
}


@Composable
fun ARScreen(model : String) {

    val nodes = remember {
        mutableListOf<ArNode>()
    }

    val modelNode = remember {
        mutableStateOf<ArModelNode?>(null)
    }

    val placeModelButton = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        ARScene(
            modifier = Modifier.fillMaxSize(),
            planeRenderer = true,
            nodes = nodes,
            onCreate = { arSceneView ->
                arSceneView.lightEstimationMode = Config.LightEstimationMode.DISABLED
                arSceneView.planeRenderer.isShadowReceiver = false
                modelNode.value = ArModelNode(
                    arSceneView.engine,
                    PlacementMode.INSTANT
                ).apply {
                    loadModelGlbAsync(
                        "models/${model}.glb",
                    ) {
                        isEditable = true
                    }
                    onAnchorChanged = {
                        placeModelButton.value = !isAnchored
                    }
                    onHitResult = { node, hitResult ->
                        placeModelButton.value = node.isTracking
                    }
                }
                nodes.add(modelNode.value!!)
            },
            onSessionCreate = {
                planeRenderer.isVisible = false
            }
        )

        if (placeModelButton.value) {
            Button(modifier = Modifier.align(Alignment.TopCenter),
                onClick = {
                modelNode.value?.anchor
            }) {
                Text(text = "Place It")
            }
        }
    }

    LaunchedEffect(key1 = model){
        modelNode.value?.loadModelGlbAsync(
            glbFileLocation = "models/${model}.glb",
        )
        Log.e("errorloading","ERROR LOADING MODEL")
    }

}



data class Pokemon(var name: String, var imageId: Int, var imageName: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARPokemonMenuTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun CircularImagePreview() {
    ARPokemonMenuTheme {
        CircularImage(imageId = R.drawable.bellsprout)
    }
}