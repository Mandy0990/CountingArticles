package com.example.countingarticles.articles


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.countingarticles.R
import com.example.countingarticles.databinding.FragmentAddArticleBinding

/**
 * A simple [Fragment] subclass.
 */
class AddArticle : Fragment() {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var binding: FragmentAddArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_article,
            container,
            false
        )
        return binding.root
    }
}
