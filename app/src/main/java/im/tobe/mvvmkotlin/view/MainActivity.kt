package im.tobe.mvvmkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import im.tobe.mvvmkotlin.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}