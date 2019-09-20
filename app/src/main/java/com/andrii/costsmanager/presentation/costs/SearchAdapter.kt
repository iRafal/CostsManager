package com.andrii.costsmanager.presentation.costs

import android.app.Activity
import android.widget.ArrayAdapter

class SearchAdapter(activity: Activity, val data: List<String>) :
    ArrayAdapter<String>(activity, android.R.layout.select_dialog_item, data)