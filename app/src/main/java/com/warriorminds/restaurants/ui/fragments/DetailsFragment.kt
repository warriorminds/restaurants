package com.warriorminds.restaurants.ui.fragments


import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

import com.warriorminds.restaurants.R
import com.warriorminds.restaurants.models.Details
import com.warriorminds.restaurants.utils.MAX_DETAIL_IMAGE_SIZE
import com.warriorminds.restaurants.utils.manageTextAndVisibilityForView
import com.warriorminds.restaurants.utils.openUri
import com.warriorminds.restaurants.viewmodels.DetailsViewModel
import com.warriorminds.restaurants.viewmodels.MapViewModel
import com.warriorminds.restaurants.viewmodels.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlinx.android.synthetic.main.fragment_map.*
import javax.inject.Inject

class DetailsFragment : Fragment() {

    companion object {
        const val VENUE_ID = "venue_id"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: DetailsViewModel
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        id = arguments!!.getString(VENUE_ID)!!
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(DetailsViewModel::class.java)
        viewModel.details.observe(this, Observer {
            initDetails(it)
        })
        viewModel.error.observe(this, Observer {
            Snackbar.make(detail_image, getString(R.string.load_error), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.retry_action)) {
                    viewModel.getDetails(id)
                }
                .show()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel.getDetails(id)
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    private fun initDetails(details: Details) {
        activity?.title = details.name
        detail_name.text = details.name
        details_description.manageTextAndVisibilityForView(details.description)

        val price = details.price?.currency?.repeat(details.price.tier)
        details_price.manageTextAndVisibilityForView(price)
        details_phone.manageTextAndVisibilityForView(details.contact?.formattedPhone ?: details.contact?.phone)
        details_rating.manageTextAndVisibilityForView("${details.rating}/10")
        details_categories.manageTextAndVisibilityForView(details.categories?.joinToString(", ") { it.name })
        details_url.manageTextAndVisibilityForView(details.url)
        details.url?.let {url ->
            details_url.setOnClickListener {
                Uri.parse(url).openUri(context!!)
            }
        }
        details_menu.manageTextAndVisibilityForView(details.menu?.label)
        details.menu?.let {menu ->
            details_menu.setOnClickListener {
                Uri.parse(menu.mobileUrl).openUri(context!!)
            }
        }
        details_address.manageTextAndVisibilityForView(details.location.formattedAddress.joinToString(" "))
        details_hours.manageTextAndVisibilityForView(details.hours?.timeframes?.joinToString(", ") { it.days + ": " + it.open.joinToString(", ") { it.renderedTime }})
        details.bestPhoto?.let {
            val height = if (it.height > MAX_DETAIL_IMAGE_SIZE) {
                MAX_DETAIL_IMAGE_SIZE
            } else {
                it.height
            }
            Picasso.get().load(it.getUrl()).centerCrop().resize(it.width, height).into(detail_image)
        }
    }
}
