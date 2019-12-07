package com.example.countingarticles.articles


import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.countingarticles.R


/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel
    //private lateinit var binding: ArticleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        Log.i("GameFragment", "Called ViewModelProviders.of")
        viewModel = ViewModelProviders.of(this).get(ArticleViewModel::class.java)

        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

}
