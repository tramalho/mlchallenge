package br.com.tramalho.mlchallenge.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.infra.Constants
import br.com.tramalho.mlchallenge.databinding.ActivityItemDetailsBinding
import kotlinx.android.synthetic.main.activity_item_details.*
import kotlinx.android.synthetic.main.content_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ItemDetailsActivity : AppCompatActivity() {

    private val viewModel: ItemDetaislViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityItemDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_item_details)

        setSupportActionBar(toolbar)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        configRecyclerView()

        configViewModel()
    }

    private fun configViewModel() {

        viewModel.pictures.observe(this, Observer {
            val adapter = carouselImgs.adapter as PictureAdapter
            adapter.submitList(it)
        })

        viewModel.tittle.observe(this, Observer {
            supportActionBar?.title = it
        })

        val itemSearch = intent.getParcelableExtra<ItemSearch>(Constants.EXTRA_DATA)
        viewModel.findDetails(itemSearch)
    }

    private fun configRecyclerView() {

        carouselImgs.adapter = PictureAdapter()

        carouselImgs.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

}
