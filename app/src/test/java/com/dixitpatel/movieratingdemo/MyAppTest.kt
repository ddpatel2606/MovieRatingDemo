package com.dixitpatel.movieratingdemo

import org.robolectric.TestLifecycle
import java.lang.reflect.Method

class MyAppTest : MyApp(), TestLifecycle<MyApp> {
    override fun beforeTest(method: Method?) {

    }

    override fun prepareTest(test: Any?) {
    }

    override fun afterTest(method: Method?) {
    }

}