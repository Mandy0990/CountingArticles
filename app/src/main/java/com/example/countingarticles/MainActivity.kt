package com.example.countingarticles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.countingarticles.addArticle.AddArticleDirections
import com.example.countingarticles.articles.ArticleFragmentDirections
import com.example.countingarticles.databinding.ActivityMainBinding
import com.example.countingarticles.model.ArticleModel
import com.example.countingarticles.model.ObjectBox
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration : AppBarConfiguration
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        setTitle("Counting Article")

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
<<<<<<< HEAD

        return when(item.itemId){
            R.id.addArticle->{
                // Have the NavigationUI look for an action or destination matching the menu
                // item id and navigate there if found.
                // Otherwise, bubble up to the parent.
                return item.onNavDestinationSelected(findNavController(R.id.my_nav_fragment))
                        || super.onOptionsItemSelected(item)
            }
            else -> super.onOptionsItemSelected(item)
        }
=======
//        // Have the NavigationUI look for an action or destination matching the menu
//        // item id and navigate there if found.
//        // Otherwise, bubble up to the parent.
        when(item.itemId){
            R.id.remove_article ->{
                Toast.makeText(this,"Click en remove article",
                    Toast.LENGTH_SHORT)
                    .show()
                val action = AddArticleDirections.nextActionToListArticle()
                findNavController(R.id.my_nav_fragment).navigate(action)
            }
            R.id.share ->{
                Toast.makeText(this,"Click en share article",
                    Toast.LENGTH_SHORT)
                    .show()
            }
            R.id.addArticle->{
                //Esta forma es usando el id del button que tiene q ser igual al del Fragment de destino
                //item.onNavDestinationSelected(findNavController(R.id.my_nav_fragment))
                val action = ArticleFragmentDirections.nextActionToAddArticle()
                findNavController(R.id.my_nav_fragment).navigate(action)

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun seleccioneRemove(){
        print("Dime q bola")
>>>>>>> 09fbff429fbe785b3827074349cf643608d43e69
    }
}
