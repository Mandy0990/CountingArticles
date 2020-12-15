package com.example.countingarticles.addArticle


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.countingarticles.R
import com.example.countingarticles.database.Article
import com.example.countingarticles.database.ArticleDatabase
import com.example.countingarticles.databinding.FragmentAddArticleBinding
import kotlinx.android.synthetic.main.fragment_add_article.*


/**
 * A simple [Fragment] subclass.
 */
@Suppress("UNREACHABLE_CODE")
class AddArticle : Fragment() {

    private lateinit var viewModel: AddArticleViewModel
    private lateinit var binding: FragmentAddArticleBinding
    private var articleKey: Int = -1


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_add_article,
            container,
            false
        )

        //Preparando parametros para inicializar el view model, en arguments esta el Id del articulo
        //que viene del ArticleFragment
        val application = requireNotNull(this.activity).application
        val arguments = AddArticleArgs.fromBundle(arguments!!)
        articleKey = arguments.articleKey

        // Create an instance of the ViewModel Factory.
        val dataSource = ArticleDatabase.getInstance(application).articleDatabaseDao
        val viewModelFactory = AddArticleViewModelFactory(arguments.articleKey.toLong(), dataSource)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(AddArticleViewModel::class.java)

        Log.i("ArticleFragment", "Called ViewModelProviders.of")

        //Asigno el evento onClick del button salvar
        binding.fab.setOnClickListener { saveArticle(arguments.articleKey.toInt()) }

        //Creo observer a la variable article, en el view model que me notifica cuando se cargo ya
        //de la BD el article actual(clicked) para mostrar sus valores en los imput field
        viewModel.getArticle().observe(this, Observer {
            if (arguments.articleKey.toInt() != -1 && it != null) { // Observed state is true.
                saveOrUpdateArticle(it)
            }
        })


        return binding.root
    }

    private fun saveOrUpdateArticle(article: Article) {
        if (article.Id != -1.toLong()) {
            Toast.makeText(context, article.articleName, Toast.LENGTH_LONG).show()
            putValueArticle(article.articleName, article.articlePrice, article.articleCount)
        }
    }

    private fun putValueArticle(articleName: String, articlePrice: Double, articleCount: Int) {
        binding.articleNameEdittext.setText(articleName)
        binding.articleCountEdittext.setText(articleCount.toString())
        binding.articlePriceEdittext.setText(articlePrice.toString())
    }

    private fun saveArticle(articleId: Int) {
        if (article_name_edittext.text.isNullOrBlank()) {
            Toast.makeText(
                activity, "Please enter a name for articcle",
                Toast.LENGTH_SHORT
            ).show()
        }else if( !article_price_edittext.text.isNullOrBlank() &&
            !article_count_edittext.text.isNullOrBlank()){

            var price = article_price_edittext.text
            var count = article_count_edittext.text
            var priceIsNumber = price.matches("-?\\d+(\\.\\d+)?".toRegex())
            var countIsNumber = count.matches("-?\\d+(\\d+)?".toRegex())
            hideKeyboard()
            if(!priceIsNumber || !countIsNumber){
                Toast.makeText(
                    activity, "Revise los campos Price and Count",
                    Toast.LENGTH_SHORT
                ).show()
            }else {
                var name = article_name_edittext.text.toString()
                var price =
                    if (article_price_edittext.text.toString() == "") "0" else article_price_edittext.text.toString()
                var count =
                    if (article_count_edittext.text.toString() == "") "0" else article_count_edittext.text.toString()

                if (articleId == -1) {
                    viewModel.addArticle(name, price.toDouble(), count.toInt())
                } else {
                    viewModel.updateArticle(name, price.toDouble(), count.toInt())
                }
                navigateToListArticle()
                hideKeyboard()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_article, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_delete -> {
                if (articleKey == -1) {
                    Toast.makeText(
                        activity, "This article don't exit in DataBase",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    viewModel.removeArticle()
                    navigateToListArticle()
                    hideKeyboard()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun navigateToListArticle() {
        val action =
            AddArticleDirections.nextActionToListArticle()
        findNavController().navigate(action)
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


}


