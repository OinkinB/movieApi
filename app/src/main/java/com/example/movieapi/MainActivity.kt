package com.example.movieapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val movieService = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build())
            .build()
            .create(MovieService::class.java)

        GlobalScope.launch(Dispatchers.Main) {
            try {
                val response = movieService.getNowPlayingMoviesCoroutine(
                    "a07e22bc18f5cb106bfe4cc1f83ad8ed",
                    "en-US",
                    1
                )
                if (response.isSuccessful) {
                    val movies: List<Movie> = response.body()?.results ?: emptyList()
                    recyclerView.adapter = MovieAdapter(movies)
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch movies", Toast.LENGTH_SHORT)
                        .show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Failed to fetch movies", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    interface MovieService {
        @GET("movie/now_playing")
        suspend fun getNowPlayingMoviesCoroutine(
            @Query("api_key") apiKey: String,
            @Query("language") language: String,
            @Query("page") page: Int
        ): Response<MovieResponse>
    }
}
data class NowPlayingResponse(val results: List<Movie>)
