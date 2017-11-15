package com.wj.kotlintest.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.wj.kotlintest.R

class MoviesDetailsActivity : AppCompatActivity() {

    companion object {
        fun actionStart(context: Context) {
            context.startActivity(Intent(context, MoviesDetailsActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)
    }
}
