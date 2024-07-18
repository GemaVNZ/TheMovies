package com.example.themovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.themovies.data.Movies
import com.example.themovies.data.MoviesAPIService
import com.example.themovies.databinding.ActivityDetailBinding
import com.example.themovies.utils.RetrofitProvider
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIES_ID = "MOVIES_ID"
    }

    private lateinit var movies : Movies
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imdbID = intent.getStringExtra(MOVIES_ID) ?: return

        findMoviesById(imdbID)

    }

    private fun findMoviesById(imdbID: String) {
        val service: MoviesAPIService = RetrofitProvider.getRetrofit()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val movie = service.findMoviesById(imdbID)
                withContext(Dispatchers.Main) {
                    movies = movie
                    loadData(movie)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@DetailActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun loadData(movies : Movies) {
        try {
            binding.apply {
                moviestitleTextView.text = movies.title
                moviesgenreTextViewe.text = movies.genre
                moviesyeartextView.text = movies.year
                moviesruntimetextView.text = movies.runtime
                moviesdirectortextView.text = movies.director
                moviescountrytextView.text = movies.country
                moviesplottextView.text = movies.plot

                Picasso.get().load(movies.imageURL).into(avatarImageView) }

        } catch (e: NullPointerException) {
                Toast.makeText(this, "Error loading movies data", Toast.LENGTH_SHORT).show()
                finish()
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
}