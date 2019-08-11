package com.andrii.costsmanager.domain.model

import java.util.Date

/**
 * Created by Andrii Medvid on 8/4/2019.
 */
data class Category(val id: Long = 0L, val name: String, val price: Int, val date: Date = Date())