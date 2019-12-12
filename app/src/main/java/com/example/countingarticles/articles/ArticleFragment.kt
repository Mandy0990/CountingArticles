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

    private lateinit var viewModel: ArticleViewModel
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_article,
            container,
            false
        )


        Log.i("ArticleFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)
        binding.articleList.adapter = viewModel.articleViewAdapter
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

}
