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
import kotlinx.android.synthetic.main.fragment_article.*


/**
 * A simple [Fragment] subclass.
 */
class ArticleFragment : Fragment(),OnChangeValuesListener {

    private lateinit var viewModel: ArticleViewModel
    private lateinit var binding: FragmentArticleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        // Get a reference to the binding object and inflate the fragment views.
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_article, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao
        val viewModelFactory = ArticleViewModelFactory(dataSource,application)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")
        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProviders.of(
            this,viewModelFactory).get(ArticleViewModel::class.java)
        binding.articleViewModel = viewModel

        val adapter = ArticleAdapter(this)
//        val adapter = ArticleAdapter(ArticleListener { articleId ->
//            //Toast.makeText(context, "${articleId}", Toast.LENGTH_LONG).show()
//            viewModel.onArticleClicked(articleId)
//        })
        binding.articleList.adapter = adapter

        viewModel.listArticles.observe(viewLifecycleOwner, Observer {
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
                        activity, textToShare,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }
            R.id.btnResetArticle ->{
                viewModel.removeAllArticles()
                Toast.makeText(
                    activity, "Removed All Articles",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            R.id.addArticle ->{
                viewModel.addNewArticle()
                Toast.makeText(
                    activity, "Added New Row",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onOnChangeValuesListener(value:String, articlePosition: Int, typeField:String) {
        val s = viewModel.getTotalPriceArticle(value,articlePosition,typeField) + "$"
        s?.let {
            binding.labelPrice.text = it
        }
    }
}
