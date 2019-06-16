package br.com.tramalho.mlchallenge.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemSearch
import br.com.tramalho.mlchallenge.data.infra.ViewResult
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    val viewModel : SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        configObservers()

        viewModel.find()
    }

    private fun configObservers() {
        viewModel.dataStatus.observe(this, Observer {
            when(it) {
                is ViewResult.Success -> verifySuccessScenario(it.data)
                else -> showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Ops, parece que algo deu errado, tente novamente", Toast.LENGTH_SHORT).show()
    }

    private fun verifySuccessScenario(data: List<ItemSearch>) {
        Toast.makeText(this, data.toString(), Toast.LENGTH_SHORT).show()
    }
}
