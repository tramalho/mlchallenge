package br.com.tramalho.mlchallenge.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import br.com.tramalho.mlchallenge.R
import br.com.tramalho.mlchallenge.data.entity.ItemResult
import br.com.tramalho.mlchallenge.data.infra.Constants
import br.com.tramalho.mlchallenge.databinding.ActivitySearchBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val viewModel: SearchViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivitySearchBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_search)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        configObservers()
    }

    private fun configObservers() {
        viewModel.dataStatus.observe(this, Observer {
            when (it) {
                is ViewResult.InProgress -> closeKeyboard()
                is ViewResult.Success -> verifySuccessScenario(it.data)
                else -> showErrorMessage()
            }
        })
    }

    private fun showErrorMessage() {
        Snackbar.make(find, R.string.something_wrong, Snackbar.LENGTH_LONG)
            .setBackgroundTint(ContextCompat.getColor(this, android.R.color.holo_red_light))
            .show()
    }

    private fun verifySuccessScenario(data: ItemResult) {
        val intent = Intent(this, ListActivity::class.java)
        intent.putExtra(Constants.EXTRA_DATA, data)
        startActivity(intent)
    }
}
