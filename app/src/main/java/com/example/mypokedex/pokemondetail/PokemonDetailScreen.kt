package com.example.mypokedex.pokemondetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import timber.log.Timber

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

    PokemonDetailTopSection(
        navController = navController,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.2f)
    )
    PokemonDetailStateWrapper(
        pokemonInfo = pokemonInfo,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = topPadding + pokemonImageSize / 2f,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .shadow(10.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.background)
            .padding(16.dp), loadingModifier = Modifier
            .padding(
                top = topPadding + pokemonImageSize / 2f,
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            )
            .size(100.dp)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
            if (pokemonInfo is Resource.Success) {
                pokemonInfo.data?.sprites?.front_default?.let {
                    GlideImage(
                        imageModel = it,
                        contentDescription = pokemonInfo.data.name,
                        circularReveal = CircularReveal(),
                        modifier = Modifier
                            .size(pokemonImageSize)

                    )
                }
            }
        }
    }
}

@Composable
fun PokemonDetailTopSection(navController : NavController, modifier : Modifier) {

    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier.background(
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
fun PokemonDetailStateWrapper(
    pokemonInfo : Resource<Pokemon>,
    modifier : Modifier = Modifier,
    loadingModifier : Modifier = Modifier
) {
    when (pokemonInfo) {
        is Resource.Success -> {
            Text(text = pokemonInfo.data?.name ?: "", modifier = modifier)
        }
        is Resource.Error -> {
            
            Text(text = "Error", modifier = modifier)
        }
        is Resource.Loading -> {

            CircularProgressIndicator(modifier = loadingModifier)
        }
    }
}