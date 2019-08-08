package com.andrii.costsmanager.presentation

import java.util.Date

/**
 * Created by Andrii Medvid on 8/8/2019.
 */
data class CategoryModel(val id: Long = 0, val name: String, val price: Int, val date: Date)