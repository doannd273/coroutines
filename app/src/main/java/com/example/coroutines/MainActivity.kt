package com.example.coroutines

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines.ui.user.UserFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.container, UserFragment())
            .commit()
    }


}
