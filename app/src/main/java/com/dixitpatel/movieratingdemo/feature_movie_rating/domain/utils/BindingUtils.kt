package com.dixitpatel.movieratingdemo.feature_movie_rating.domain.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.dixitpatel.movieratingdemo.R
import com.dixitpatel.movieratingdemo.feature_movie_rating.data.constant.*
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.moviedetails.MovieDetailsActivity
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.persondetails.PersonDetailsActivity
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.seeall.SeeAllActivity
import com.dixitpatel.movieratingdemo.feature_movie_rating.presentation.ui.tvdetails.TvDetailsActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import jp.wasabeef.glide.transformations.CropTransformation
import net.cachapa.expandablelayout.ExpandableLayout

@BindingAdapter("isVisible")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("android:layout_width")
fun View.setWidth(width: Float) {
    layoutParams.width = width.toInt()
}

@BindingAdapter("android:layout_height")
fun View.setHeight(height: Float) {
    layoutParams.height = height.toInt()
}

@BindingAdapter("detailMediaType", "detailId", "seasonNumber", requireAll = false)
fun View.setDetailsIntent(mediaType: SelectedMediaType, id: Int, seasonNumber: Int?) {
     setOnClickListener {
            val intentClass = when (mediaType) {
                SelectedMediaType.MOVIE -> MovieDetailsActivity::class.java
                SelectedMediaType.TV -> TvDetailsActivity::class.java
                SelectedMediaType.PERSON -> PersonDetailsActivity::class.java
            }
                Intent(context, intentClass).apply {
                    putExtra(DETAIL_ID, id)
                    putExtra(MEDIA_TYPE, mediaType as Parcelable)
                    if (seasonNumber != null) putExtra(SEASON_NUMBER, seasonNumber)

                    context.startActivity(this)
                }
        }
}

@BindingAdapter("intentType", "mediaType", "intId", "stringId", "title", "list", requireAll = false)
fun View.setSeeAllIntent(
    intentType: SelectedIntentType,
    mediaType: SelectedMediaType?,
    detailId: Int?,
    listId: String?,
    title: String,
    list: List<Any>?
) {
    setOnClickListener {
        Intent(context, SeeAllActivity::class.java).apply {
            putExtra(INTENT_TYPE, intentType as Parcelable)
            putExtra(TITLE, title)
            if (mediaType != null) putExtra(MEDIA_TYPE, mediaType as Parcelable)
            if (detailId != null) putExtra(DETAIL_ID, detailId)
            if (listId != null) putExtra(LIST_ID, listId)
            if (list != null) putExtra(LIST, ArrayList(list))

            context.startActivity(this)
        }
    }
}

@BindingAdapter("android:layout_marginBottom", requireAll = false)
fun View.setLayoutMarginBottom(isGrid: Boolean) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = resources.getDimension(
        if (isGrid) {
            R.dimen.bottom_margin
        } else R.dimen.zero_dp
    ).toInt()

    layoutParams = params
}

@BindingAdapter("android:background")
fun View.setBackground(color: Int) {
    setBackgroundColor(if (color != 0) color else ContextCompat.getColor(context, R.color.day_night_inverse))
}

@BindingAdapter("transparentBackground")
fun View.setTransparentBackground(backgroundColor: Int) {
    setBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, 220))
}

@BindingAdapter("isNested")
fun ViewPager2.handleNestedScroll(isNested: Boolean) {
    if (isNested) {
        val recyclerViewField = ViewPager2::class.java.getDeclaredField("mRecyclerView")
        recyclerViewField.isAccessible = true
        val recyclerView = recyclerViewField.get(this) as RecyclerView
        recyclerView.interceptTouch()
    }
}

@BindingAdapter("isNested")
fun RecyclerView.handleNestedScroll(isNested: Boolean) {
    if (isNested) interceptTouch()
}

@BindingAdapter("type", "isGrid", "loadMore", "shouldLoadMore", requireAll = false)
fun RecyclerView.addInfiniteScrollListener(type: Any?, isGrid: Boolean, infiniteScroll: InfiniteScrollListener, shouldLoadMore: Boolean) {
    if (shouldLoadMore) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private val layoutManagerType = if (isGrid) layoutManager as GridLayoutManager else layoutManager as LinearLayoutManager
            private val visibleThreshold = 10
            private var loading = true
            private var previousTotal = 0

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val visibleItemCount = layoutManagerType.childCount
                val totalItemCount = layoutManagerType.itemCount
                val firstVisibleItem = layoutManagerType.findFirstVisibleItemPosition()

                if (totalItemCount < previousTotal) previousTotal = 0

                if (loading && totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }

                if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                    infiniteScroll.onLoadMore(type)
                    loading = true
                }
            }
        })
    }
}

@BindingAdapter("fixedSize")
fun RecyclerView.setFixedSize(hasFixedSize: Boolean) {
    setHasFixedSize(hasFixedSize)
}

@SuppressLint("CheckResult")
@BindingAdapter("imageUrl", "imageMediaType", "imageQuality", "centerCrop", "fitTop", "isThumbnail", requireAll = false)
fun ImageView.loadImage(posterPath: String?, mediaType: SelectedMediaType?, quality: ImageQuality?, centerCrop: Boolean?, fitTop: Boolean, isThumbnail: Boolean) {
    val imageUrl = if (isThumbnail) "https://img.youtube.com/vi/$posterPath/0.jpg" else quality?.imageBaseUrl + posterPath

    val glide = Glide.with(context)
        .load(imageUrl)
        .transition(DrawableTransitionOptions.withCrossFade())
        .error(geErrorImageDrawable(context, mediaType))
        .skipMemoryCache(false)

    if (centerCrop == true) glide.centerCrop()
    if (fitTop) glide.apply(RequestOptions.bitmapTransform(CropTransformation(0, 1235, CropTransformation.CropType.TOP)))

    glide.into(this)
}

@BindingAdapter("iconTint")
fun ImageView.setIconTint(color: Int?) {
    color?.let { setColorFilter(setTintColor(this.context)) }
}

@BindingAdapter("rating")
fun TextView.setRating(rating: Double) {
    text = rating.toString()
}

@BindingAdapter("activity", "backArrowTint", "seeAllTitle", "titleTextColor", requireAll = false)
fun Toolbar.setupToolbar(activity: AppCompatActivity, backArrowTint: Int, seeAllTitle: String?, titleTextColor: Int?) {
    activity.apply {
        setSupportActionBar(this@setupToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (seeAllTitle != null) supportActionBar?.title = seeAllTitle
    }

    navigationIcon?.setTint(if (backArrowTint != 0) setTintColor(activity) else ContextCompat.getColor(context, R.color.day_night))
    if (titleTextColor != null) setTitleTextColor(if (titleTextColor != 0) setTintColor(activity) else ContextCompat.getColor(context, R.color.day_night))

    setNavigationOnClickListener {
        activity.finish()
    }
}

@BindingAdapter("collapsingToolbar", "frameLayout", "toolbarTitle", requireAll = false)
fun AppBarLayout.setToolbarCollapseListener(collapsingToolbar: CollapsingToolbarLayout, frameLayout: FrameLayout, toolbarTitle: String) {
    var isShow = true
    var scrollRange = -1
    this.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (scrollRange == -1) {
            scrollRange = appBarLayout?.totalScrollRange ?: -1
        }

        if (scrollRange + verticalOffset == 0) {
            frameLayout.isVisible = false
            collapsingToolbar.setCollapsedTitleTextColor(setTintColor(this.context))
            collapsingToolbar.title = toolbarTitle
            isShow = true
        } else if (isShow) {
            frameLayout.isVisible = isShow
            collapsingToolbar.title = " "
            isShow = false
        }
    }
}

@BindingAdapter("expand", "expandIcon")
fun ConstraintLayout.setExpandableLayout(expandableLayout: ExpandableLayout, expandIcon: ImageView) {
    setOnClickListener {
        expandableLayout.toggle()
        expandIcon.animate().rotationBy(-180f)
        isClickable = false
        Handler(Looper.getMainLooper()).postDelayed({ isClickable = true }, 600)
    }
}