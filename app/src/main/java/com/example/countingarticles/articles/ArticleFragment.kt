package com.example.countingarticles.articles


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countingarticles.R
import com.example.countingarticles.databinding.FragmentArticleBinding
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox
import io.objectbox.android.AndroidScheduler
import io.objectbox.query.Query
import io.objectbox.reactive.DataSubscription


/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentArticleBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article, container, false)


        Log.i("ArticleFragment", "Called ViewModelProviders.of")
        val viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        binding.articleViewModel = viewModel

        val adapter = ArticleAdapter()
        binding.articleList.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

}
