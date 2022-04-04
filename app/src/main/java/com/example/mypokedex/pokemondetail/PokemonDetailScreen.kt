package com.example.mypokedex.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mypokedex.data.remote.responses.Pokemon
import com.example.mypokedex.util.Resource
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.palette.BitmapPalette

@Composable
fun PokemonDetailScreen(
    dominantColor : Color,
    pokemonName : String,
    navController : NavController,
    topPadding : Dp = 20.dp,
    pokemonImageSize : Dp = 200.dp,
    viewModel : PokemonDetailViewModel = hiltViewModel()
) {
    val pokemonInfo = produceState<Resource<Pokemon>>(initialValue = Resource.Loading()) {
        value = viewModel.getPokemonInfo(pokemonName)
    }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.sprites?.let {
                    GlideImage(
                        imageModel = it,
                        contentDescription = pokemonInfo.data.name,
                        circularReveal = CircularReveal(),
                        modifier = Modifier
                            .size(pokemonImageSize)
                            .offset(y = topPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(navController : NavController, modifier : Modifier) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.background(
            Brush.verticalGradient(
                listOf(
                    Color.Black,
                    Color.Transparent
                )
            )
        )
    ) {
        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .offset(16.dp, 16.dp)
                .size(36.dp)
                .clickable { navController.popBackStack() })
    }
}

@Composable
fun PokemonDetailStateWrapper() {

}