package com.dixitpatel.movieratingdemo.utils

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import org.junit.After
import org.junit.Before

abstract class BaseTest<T> {

    var classUnderTest: T? = null

    protected abstract fun getTestObject(): T

    @Before
    open fun setUp(){
        classUnderTest = getTestObject()
    }

    @After
    open fun tearDown() {
        if (classUnderTest != null) {
            if (classUnderTest is Activity) {
                if ((classUnderTest as Activity).baseContext != null && !(classUnderTest as Activity).isFinishing) {
                    (classUnderTest as Activity).finish()
                }
            } else if (classUnderTest is Fragment) {
                if ((classUnderTest as Fragment).activity != null) {
                    val manager = (classUnderTest as Fragment).activity?.supportFragmentManager
                    manager?.beginTransaction()?.remove(classUnderTest as Fragment)?.commit()
                    (classUnderTest as Fragment).activity?.finish()
                }
            }
        }
        classUnderTest = null
    }

    open fun fragmentFactory(): FragmentFactory? {
        return object : FragmentFactory() {
            override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
                return classUnderTest as Fragment
            }
        }
    }

}