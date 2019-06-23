package br.com.tramalho.mlchallenge.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.tramalho.mlchallenge.R
import kotlinx.android.synthetic.main.activity_item_details.*

class ItemDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)

        setSupportActionBar(toolbar)
    }
}
