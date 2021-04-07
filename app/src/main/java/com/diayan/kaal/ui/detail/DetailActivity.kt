package com.diayan.kaal.ui.detail

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import com.diayan.kaal.MainActivity
import com.diayan.kaal.R
import com.diayan.kaal.di.injectViewModel
import com.diayan.kaal.ui.home.RegionsViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class DetailActivity : AppCompatActivity(), HasAndroidInjector {
    companion object {
        // View name of the header image. Used for activity scene transitions
        val VIEW_NAME_HEADER_IMAGE = "detail:header:image"
    }

    private lateinit var itemDetailImageView: ImageView
    private lateinit var detailToolbar: Toolbar

    @set:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>
    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    @set:Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        detailViewModel     = injectViewModel(viewModelFactory)

        detailToolbar       = findViewById(R.id.detailToolbar)
        setSupportActionBar(detailToolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = ""

        itemDetailImageView = findViewById(R.id.detailImageView)
        ViewCompat.setTransitionName(itemDetailImageView, VIEW_NAME_HEADER_IMAGE)
        loadItem()
    }

    private fun setupRecyclerView() {

    }

    /**
     * Load the item's thumbnail image into our [ImageView].
     */
    private fun loadThumbnail() {
   /*     Picasso.get()
        Picasso.get()
            .load(mItem.getThumbnailUrl())
            .noFade()
            .into(itemDetailImageView)*/
    }

    /**
     * Load the item's full-size image into our [ImageView].
     */
    private fun loadFullSizeImage() {
 /*       Picasso.get()
            .load(mItem.getPhotoUrl())
            .noFade()
            .noPlaceholder()
            .into(itemDetailImageView)*/
    }
    private fun loadItem() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && addTransitionListener()) {
            //loadThumbnail()
        } else {
            //loadFullSizeImage()
        }
    }

    @RequiresApi(21)
    private fun addTransitionListener(): Boolean {
        val transition = window.sharedElementEnterTransition
        if (transition != null) {
            // There is an entering shared element transition so add a listener to it
            transition.addListener(object : Transition.TransitionListener {
                override fun onTransitionEnd(transition: Transition) {
                    // As the transition has ended, we can now load the full-size image
                    loadFullSizeImage()
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionStart(transition: Transition) {
                    // No-op
                }

                override fun onTransitionCancel(transition: Transition) {
                    // Make sure we remove ourselves as a listener
                    transition.removeListener(this)
                }

                override fun onTransitionPause(transition: Transition) {
                    // No-op
                }

                override fun onTransitionResume(transition: Transition) {
                    // No-op
                }
            })
            return true
        }
        // If we reach here then we have not added a listener
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
