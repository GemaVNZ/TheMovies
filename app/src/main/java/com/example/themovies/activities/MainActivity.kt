package com.example.themovies.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.themovies.adapter.MoviesAdapter
import com.example.themovies.data.Movies
import com.example.themovies.data.MoviesAPIService
import com.example.themovies.databinding.ActivityMainBinding
import com.example.themovies.utils.RetrofitProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast
import com.example.themovies.R
import com.example.themovies.data.MoviesResponse
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MoviesAdapter
    private var moviesList: List<Movies> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = MoviesAdapter(moviesList) { position ->
            navigateToDetail(moviesList[position])
        }

        binding.recyclerViewMain.adapter = adapter
        binding.recyclerViewMain.layoutManager = GridLayoutManager(this, 2)

        findMoviesById("tt3896198")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        initSearchView(menu?.findItem(R.id.menu_search))

        return true
    }


    //Menú para hacer la búsqueda
    private fun initSearchView(searchItem: MenuItem?) {
        if (searchItem != null) {
            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let {
                        searchMoviesByTitle(it)
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToDetail(movies: Movies) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("MOVIES_ID", movies.imdbID)
            /*intent.putExtra("MOVIES_TITLE", movies.title)
            intent.putExtra("MOVIES_YEAR", movies.year)
            intent.putExtra("MOVIES_IMAGE", movies.imageURL)
            intent.putExtra("MOVIES_PLOT", movies.plot)
            intent.putExtra("MOVIES_RUNTIME", movies.runtime)
            intent.putExtra("MOVIES_DIRECTOR", movies.director)
            intent.putExtra("MOVIES_GENRE", movies.genre)
            intent.putExtra("MOVIES_COUNTRY", movies.country)*/
            startActivity(intent)

        }

    private fun findMoviesById(imdbID: String) {
        val service: MoviesAPIService = RetrofitProvider.getRetrofit()
        //Llamada al segundo hilo
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = service.findMoviesById(imdbID)
                withContext(Dispatchers.Main) {
                    moviesList = listOf(movie)
                    adapter.updateData(moviesList) }

            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun searchMoviesByTitle(title: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val service = RetrofitProvider.getRetrofit()
                val response: MoviesResponse = service.searchMoviesByTitle(title)
                withContext(Dispatchers.Main) {
                    if (response.response == "True") {
                        moviesList = response.movies
                        adapter.updateData(moviesList)
                    } else {
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

