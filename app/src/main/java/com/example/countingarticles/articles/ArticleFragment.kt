package com.example.countingarticles.articles


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countingarticles.R
import com.example.countingarticles.database.ArticleDatabase
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

        val application = requireNotNull(this.activity).application

        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao

        val viewModelFactory = ArticleViewModelFactory(dataSource,application)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")
        // Get a reference to the ViewModel associated with this fragment.
        val viewModel = ViewModelProviders.of(
            this,viewModelFactory).get(ArticleViewModel::class.java)
        binding.articleViewModel = viewModel

        val adapter = ArticleAdapter(ArticleListener { articleId ->
            Toast.makeText(context, "${articleId}", Toast.LENGTH_LONG).show()
            viewModel.onArticleClicked(articleId)
        })
        binding.articleList.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
//        viewModel.articles.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                adapter.submitList(it)
//            }
//        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

}
