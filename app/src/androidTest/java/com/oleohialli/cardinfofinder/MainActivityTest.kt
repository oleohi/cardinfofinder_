package com.oleohialli.cardinfofinder

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @get: Rule
    val activityRule : ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.constraintLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isComponentsInView() {
        onView(withId(R.id.titleTextView)).check(matches(isDisplayed()))
        onView(withId(R.id.desc_textView)).check(matches(isDisplayed()))
        onView(withId(R.id.cardNumberLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.cardNumberField)).check(matches(isDisplayed()))
        onView(withId(R.id.progressLoader)).check(matches(not(isDisplayed())))
        onView(withId(R.id.findButton)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isTitleAndDescTextDisplayed() {
        onView(withId(R.id.titleTextView)).check(matches(withText(R.string.find_any_card)))
        onView(withId(R.id.desc_textView)).check(matches(withText(R.string.desc)))
    }

    @Test
    fun testInputField() {
        onView(withId(R.id.cardNumberField)).check(matches(withHint(R.string.card_number)))
        onView(withId(R.id.cardNumberField)).perform(typeText("543210"), closeSoftKeyboard())
        onView(withId(R.id.cardNumberField)).check(matches(withText("543210")))
    }

    @Test
    fun test_onFindButtonClick() {
        onView(withId(R.id.cardNumberField)).perform(typeText("543210"), closeSoftKeyboard())
        onView(withId(R.id.findButton)).perform(click())
    }
}