package br.com.tramalho.mlchallenge.presentation

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tramalho.mlchallenge.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

/**
 * Binding criado para permitir que a insercao da url
 * via viewModel inicialize a busca das imagens via Glide
 */
@BindingAdapter(value = ["imageUrl"], requireAll = false)
fun loadImage(imageView: ImageView, url: String?) {

    if (!url.isNullOrBlank()) {

        Glide.with(imageView.context)
            .load(url)
            .placeholder(ContextCompat.getDrawable(imageView.context, R.drawable.placeholder))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }
}

/**
 * Permite a insercao de um observer do scroll da recycler
 * e posterior disparo da function enviada como parametro
 */
fun RecyclerView.loadMore(onLoadMore: () -> Unit) {
    this.addOnScrollListener(EndlessRecyclerOnScrollListener(onLoadMore))
}

fun Activity.closeKeyboard() {
    val view = this.currentFocus
    view?.let {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}