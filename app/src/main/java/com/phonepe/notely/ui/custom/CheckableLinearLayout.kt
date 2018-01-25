/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.phonepe.notely.ui.custom

import android.annotation.TargetApi
import android.content.Context
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.View
import android.widget.Checkable
import android.widget.LinearLayout

/**
 * A LinearLayout which is checkable. This will set the checked state when
 * [.onCreateDrawableState] is called, and can be used with
 * `android:duplicateParentState` to propagate the drawable state to child views.
 */
class CheckableLinearLayout : LinearLayout, Checkable {

    private var mChecked = false

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    @TargetApi(VERSION_CODES.HONEYCOMB)
    constructor(
            context: Context,
            attrs: AttributeSet?,
            defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(
            context: Context,
            attrs: AttributeSet,
            defStyleAttr: Int,
            defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        if (mChecked) {
            val superStates = super.onCreateDrawableState(extraSpace + 1)
            val checked = intArrayOf(android.R.attr.state_checked)
            return View.mergeDrawableStates(superStates, checked)
        } else {
            return super.onCreateDrawableState(extraSpace)
        }
    }

    override fun setChecked(checked: Boolean) {
        mChecked = checked
        refreshDrawableState()
    }

    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun toggle() {
        isChecked = !isChecked
    }
}
