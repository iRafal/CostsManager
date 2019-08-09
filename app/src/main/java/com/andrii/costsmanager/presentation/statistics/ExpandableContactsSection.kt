package com.andrii.costsmanager.presentation.statistics

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.andrii.costsmanager.R
import com.andrii.costsmanager.presentation.model.CategoryModel
import com.jakewharton.rxbinding3.view.clicks
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection
import kotlinx.android.synthetic.main.list_item_section_header.view.imgArrow
import kotlinx.android.synthetic.main.list_item_section_header.view.tvTitle
import timber.log.Timber


/**
 * Created by Andrii Medvid on 8/9/2019.
 */
class ExpandableContactsSection(
    private val title: String,
    private val list: List<CategoryModel>,
    private var expanded: Boolean = true,
    private val onItemClick: () -> Unit
) : StatelessSection(
    SectionParameters.builder()
        .itemResourceId(R.layout.list_item_section)
        .headerResourceId(R.layout.list_item_section_header)
        .build()
) {
    override fun getContentItemsTotal() = if (expanded) list.size else 0

    override fun getItemViewHolder(view: View): ViewHolder = ItemViewHolder(view)

    override fun onBindItemViewHolder(holder: ViewHolder, position: Int) {
        (holder as ItemViewHolder?)?.let { itemHolder ->

            list[position].also { item ->
                val date = DateFormat.format("yyyy-MM-dd hh:mm:ss a", item.date)
                val label = "Name=${item.name}, Price=${item.price}, Date=$date"
                itemHolder.tvItem.text = label
            }

            itemHolder.tvItem.clicks().subscribe {
                Timber.d("CLICK, position = $position")
            }
        }
    }

    override fun getHeaderViewHolder(view: View): ViewHolder = HeaderViewHolder(view)

    override fun onBindHeaderViewHolder(holder: ViewHolder?) {
        (holder as HeaderViewHolder?)?.let { headerHolder ->
            val label = "$title (${list.size})"
            headerHolder.tvTitle.text = label

            headerHolder.rootView.clicks().subscribe {
                expanded = !expanded
                headerHolder.imgArrow.setImageResource(
                    if (expanded) R.drawable.ic_arrow_up_black_24dp
                    else R.drawable.ic_arrow_down_black_24dp
                )
                onItemClick()
            }
        }
    }

    private inner class HeaderViewHolder(val rootView: View) : ViewHolder(rootView) {
        val tvTitle: TextView = rootView.tvTitle
        val imgArrow: ImageView = rootView.imgArrow
    }

    private inner class ItemViewHolder(rootView: View) : ViewHolder(rootView) {
        val tvItem: TextView = rootView as TextView
    }
}