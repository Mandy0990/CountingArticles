package com.example.countingarticles.articles


import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.countingarticles.R
import com.example.countingarticles.addArticle.AddArticleViewModel
import com.example.countingarticles.database.ArticleDatabase
import com.example.countingarticles.databinding.FragmentArticleBinding


/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment() {

    private lateinit var viewModel: ArticleViewModel
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
        viewModel = ViewModelProviders.of(
            this,viewModelFactory).get(ArticleViewModel::class.java)
        binding.articleViewModel = viewModel

        val adapter = ArticleAdapter(ArticleListener { articleId ->
            //Toast.makeText(context, "${articleId}", Toast.LENGTH_LONG).show()
            viewModel.onArticleClicked(articleId)
        })
        binding.articleList.adapter = adapter

        viewModel.articles.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
        // Add an Observer on the state variable for Navigating when and item is clicked.
        viewModel.articleIdToUpdate.observe(this, Observer { articleId ->
            articleId?.let {

                this.findNavController().navigate(
                    ArticleFragmentDirections
                        .nextActionToAddArticle(articleId.toInt()))
                viewModel.onAddArticleNavigated()
            }
        })

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_share -> {

                var textToShare = viewModel.getTextToShareWithWatsApp()
                val whatsappIntent = Intent(Intent.ACTION_SEND)
                whatsappIntent.type = "text/plain"
                whatsappIntent.setPackage("com.whatsapp")
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, textToShare)
                try {
                    activity!!.startActivity(whatsappIntent)
                } catch (ex: ActivityNotFoundException) {
                    Toast.makeText(
                        activity, "WhatsApp not installed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
