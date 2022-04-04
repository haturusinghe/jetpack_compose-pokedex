package com.example.mypokedex.repository

import com.example.mypokedex.data.remote.PokeApi
import com.example.mypokedex.data.remote.responses.Pokemon
import com.example.mypokedex.data.remote.responses.PokemonList
import com.example.mypokedex.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api : PokeApi
){



}