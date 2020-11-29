package com.hackyeah.nowaste.ui.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.hackyeah.nowaste.databinding.*
import com.hackyeah.nowaste.ui.product.adapter.items.*


class ProductAdapter() : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    enum class ViewType {
        IMAGE,
        TITLE,
        CATEGORY,
        TAGS,
        RATING,
        REVIEW,
        ACTION,
        NO_DATA,
        REUSE,
        COUPON
    }

    private val items = ArrayList<Any>()

    override fun getItemViewType(position: Int): Int {
        val item = items[position]
        val viewType = when (item) {
            is ImageItem -> ViewType.IMAGE
            is TitleItem -> ViewType.TITLE
            is CategoryItem -> ViewType.CATEGORY
            is TagsItem -> ViewType.TAGS
            is RatingItem -> ViewType.RATING
            is ReviewItem -> ViewType.REVIEW
            is ActionItem -> ViewType.ACTION
            is NoDataItem -> ViewType.NO_DATA
            is ReuseItem -> ViewType.REUSE
            is CouponItem -> ViewType.COUPON
            else -> null
        }
        return viewType?.ordinal ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder: ViewHolder = when (ViewType.values()[viewType]) {
            ViewType.IMAGE -> {
                val binding =
                    ImageItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ImageViewHolder(
                    binding
                )
            }
            ViewType.TITLE -> {
                val binding =
                    TitleItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TitleViewHolder(binding)
            }
            ViewType.CATEGORY -> {
                val binding =
                    CategoryItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                CategoryViewHolder(binding)
            }
            ViewType.TAGS -> {
                val binding =
                    TagsItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                TagsViewHolder(binding)
            }
            ViewType.RATING -> {
                val binding =
                    RatingItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                RatingViewHolder(binding)
            }
            ViewType.REVIEW -> {
                val binding =
                    ReviewItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ReviewViewHolder(binding)
            }
            ViewType.ACTION -> {
                val binding =
                    ActionItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ActionViewHolder(binding)
            }
            ViewType.NO_DATA -> {
                val binding =
                    NoDataItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                NoDataViewHolder(binding)
            }
            ViewType.REUSE -> {
                val binding =
                    ReuseItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                ReuseViewHolder(binding)
            }
            ViewType.COUPON -> {
                val binding =
                    CouponItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                CouponViewHolder(binding)
            }
            else -> {
                val binding =
                    EmptyItemViewBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                EmptyViewHolder(
                    binding
                )
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        when (item) {
            is ImageItem -> (holder as ImageViewHolder).bind(item)
            is TitleItem -> (holder as TitleViewHolder).bind(item)
            is CategoryItem -> (holder as CategoryViewHolder).bind(item)
            is TagsItem -> (holder as TagsViewHolder).bind(item)
            is RatingItem -> (holder as RatingViewHolder).bind(item)
            is ReviewItem -> (holder as ReviewViewHolder).bind(item)
            is ActionItem -> (holder as ActionViewHolder).bind(item)
            is NoDataItem -> (holder as NoDataViewHolder).bind(item)
            is ReuseItem -> (holder as ReuseViewHolder).bind(item)
            is CouponItem -> (holder as CouponViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setItems(items: ArrayList<Any>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun addItems(items: ArrayList<Any>) {
        this.items.addAll(items)
        notifyItemRangeChanged(
            this.items.size - items.size,
            items.size
        )
    }

    open class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    class EmptyViewHolder(private val binding: EmptyItemViewBinding) : ViewHolder(binding.root)

    class ImageViewHolder(private val binding: ImageItemViewBinding) : ViewHolder(binding.root) {
        fun bind(data: ImageItem) {
            data.url?.let {
                Glide.with(binding.root)
                    .load(it)
                    .into(binding.image)
            }
        }
    }

    class TitleViewHolder(private val binding: TitleItemViewBinding) : ViewHolder(binding.root) {
        fun bind(data: TitleItem) {
            binding.value.setText(data.value)
        }
    }

    class CategoryViewHolder(private val binding: CategoryItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(data: CategoryItem) {
            binding.value.setText(data.category.name)
        }
    }

    class TagsViewHolder(private val binding: TagsItemViewBinding) : ViewHolder(binding.root) {
        fun bind(data: TagsItem) {
            var tags = ""
            data.tags.forEach { tag ->
                tags += String.format("#%s ", tag.name)
            }
            binding.value.setText(tags)
        }
    }

    class RatingViewHolder(private val binding: RatingItemViewBinding) : ViewHolder(binding.root) {
        fun bind(data: RatingItem) {
            binding.ratingProductFromRecycling.rating =
                data.rating.productFromRecycling?.toFloat() ?: 0f
            binding.ratingProductRecycable.rating =
                data.rating.productRecycable?.toFloat() ?: 0f
            binding.ratingProductReusable.rating =
                data.rating.productReusable?.toFloat() ?: 0f
            binding.ratingRepairable.rating =
                data.rating.repairable?.toFloat() ?: 0f

            binding.ratingBoxRecycable.rating =
                data.rating.boxRecycable?.toFloat() ?: 0f
            binding.ratingBoxReusable.rating =
                data.rating.boxReusable?.toFloat() ?: 0f
            binding.ratingBoxFromRecycling.rating =
                data.rating.boxFromRecycling?.toFloat() ?: 0f
        }
    }

    class ReviewViewHolder(private val binding: ReviewItemViewBinding) : ViewHolder(binding.root) {
        fun bind(data: ReviewItem) {
            binding.author.setText(data.review.author)
            binding.date.setText(
                String.format(
                    "%d-%d-%d %d:%d:%d",
                    data.review.date[0],
                    data.review.date[1],
                    data.review.date[2],
                    data.review.date[3],
                    data.review.date[4],
                    data.review.date[5]
                )
            )

            binding.ratingProductFromRecycling.rating =
                data.review.rating.productFromRecycling?.toFloat() ?: 0f
            binding.ratingProductRecycable.rating =
                data.review.rating.productRecycable?.toFloat() ?: 0f
            binding.ratingProductReusable.rating =
                data.review.rating.productReusable?.toFloat() ?: 0f
            binding.ratingRepairable.rating =
                data.review.rating.repairable?.toFloat() ?: 0f

            binding.ratingBoxRecycable.rating =
                data.review.rating.boxRecycable?.toFloat() ?: 0f
            binding.ratingBoxReusable.rating =
                data.review.rating.boxReusable?.toFloat() ?: 0f
            binding.ratingBoxFromRecycling.rating =
                data.review.rating.boxFromRecycling?.toFloat() ?: 0f

            binding.comment.setText(data.review.comment)
        }
    }

    class ActionViewHolder(private val binding: ActionItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ActionItem) {
            binding.root.setText(data.label)
            binding.root.setOnClickListener { data.action() }
        }
    }

    class NoDataViewHolder(private val binding: NoDataItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(data: NoDataItem) {
            binding.root.setText(data.label)
        }
    }

    class CouponViewHolder(private val binding: CouponItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(data: CouponItem) {
            binding.code.setText(data.coupon.coupon)
            binding.description.setText(data.coupon.description)

            if (data.coupon.icon.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(data.coupon.icon)
                    .transform(CenterCrop())
                    .into(binding.image)
            }
        }
    }

    class ReuseViewHolder(private val binding: ReuseItemViewBinding) :
        ViewHolder(binding.root) {
        fun bind(data: ReuseItem) {
            binding.root.setOnClickListener { data.clickAction(data.reuse) }
            binding.title.setText(data.reuse.title)
            binding.description.setText(data.reuse.description)
            binding.author.setText(data.reuse.author)

            if (data.reuse.photosUrl.isNotEmpty()) {
                Glide.with(binding.root)
                    .load(data.reuse.photosUrl[0])
                    .into(binding.picturePreview1)
            }
        }
    }
}
