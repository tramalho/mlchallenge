package br.com.tramalho.mlchallenge.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.infra.Constants
import kotlinx.android.synthetic.main.activity_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRV()

        setupViewModel()
    }

    private fun setupRV() {

        val layoutManager = GridLayoutManager(this,Constants.COLUMNS)

        listItem.layoutManager = layoutManager

        val clickAction = { itemSearch: ItemSearch ->
            val intent = Intent(this, ItemDetailsActivity::class.java)
            intent.putExtra(Constants.EXTRA_DATA, itemSearch)
            startActivity(intent)
        }

        listItem.adapter = ItemAdapter(clickAction)

       listItem.addItemDecoration(ItemOffsetDecoration(this, R.dimen.card_horizontal_margin))

        viewModel.dataReceived.observe(this, Observer {
            val adapter = listItem.adapter as ItemAdapter
            adapter.submitList(it)
        })

        listItem.loadMore { viewModel.find() }
    }

    private fun setupViewModel() {

        val itemResult = intent.getParcelableExtra<ItemResult>(Constants.EXTRA_DATA)

        viewModel.addValue(itemResult)

        supportActionBar?.title = viewModel.title
    }
}
