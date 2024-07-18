package com.example.themovies.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.themovies.data.Movies
import com.example.themovies.databinding.ActivityDetailBinding
import com.squareup.picasso.Picasso

class DetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIES_ID = "MOVIES_ID"
        const val MOVIES_YEAR = "MOVIES_YEAR"
        const val MOVIES_POSTER = "MOVIES_IMAGE"
        const val MOVIES_PLOT = "MOVIES_PLOT"
        const val MOVIES_RUNTIME = "MOVIES_RUNTIME"
        const val MOVIES_DIRECTOR ="MOVIES_DIRECTOR"
        const val MOVIES_GENRE = "MOVIES_GENRE"
        const val MOVIES_COUNTRY = "MOVIES_COUNTRY"
    }

    private lateinit var movies : Movies
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movies = Movies (
        intent.getStringExtra("MOVIES_ID") ?: "",
        intent.getStringExtra("MOVIES_YEAR") ?: "",
        intent.getStringExtra("MOVIES_IMAGE")?: "",
        intent.getStringExtra("MOVIES_PLOT") ?: "",
        intent.getStringExtra("MOVIES_RUNTIME") ?: "",
        intent.getStringExtra("MOVIES_DIRECTOR") ?: "",
        intent.getStringExtra("MOVIES_GENRE") ?: "",
        intent.getStringExtra("MOVIES_COUNTRY") ?: "" )

        Picasso.get().load(movies.imageURL).into(binding.avatarImageView)
        loadData()
    }

    private fun loadData() {
        try {
            binding.apply {
                moviesidTextView.text = movies.id
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
}