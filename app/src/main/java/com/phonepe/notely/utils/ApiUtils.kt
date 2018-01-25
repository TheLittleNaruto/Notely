package com.phonepe.notely.utils

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity

/**
 * Created by Kumar Gaurav on 1/17/2018.
 */
//#BEGIN
//This can be use in any activity class by simply calling start<Activity>()
/**
 * @param bundle this is option parameter. this is use for passing objects between activity
 */

inline fun <reified T : Activity> Activity.start(bundle: Bundle? = null) {
    val starter = Intent(this@start, T::class.java)
    if (bundle != null) starter.putExtras(bundle)
    startActivity(starter)
}