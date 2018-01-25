package com.phonepe.notely.ui.custom

/**
 * Created by Kumar Gaurav on 1/19/2018.
 */
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.widget.TextView

class LogoTextView : TextView {

    val ELEPHANT_FONT: String = "ELEPHNT.TTF"

    constructor(context: Context) : super(context) {
        val face = Typeface.createFromAsset(context.assets, ELEPHANT_FONT)
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val face = Typeface.createFromAsset(context.assets, ELEPHANT_FONT)
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val face = Typeface.createFromAsset(context.assets, ELEPHANT_FONT)
        this.typeface = face
    }

}