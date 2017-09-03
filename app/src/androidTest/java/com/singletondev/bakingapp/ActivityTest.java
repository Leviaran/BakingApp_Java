package com.singletondev.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.action.ViewActions.click;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Randy Arba on 8/28/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ActivityTest {
    @Rule
    public ActivityTestRule<Besa> mActivityTest = new ActivityTestRule<Besa>(Besa.class);

    private IdlingResource mIdlingResources;

    @Before
    public void registeriDlingReources(){
        mIdlingResources = mActivityTest.getActivity().getIdleRes();

        //Check kesalahan
        Espresso.registerIdlingResources(mIdlingResources);
    }

    @Test
    public void checkText(){
        onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.scrollToPosition(1));
        onView(withText("Brownies")).check(matches(isDisplayed()));
    }

    @Test
    public void checkSimplePayerIsVisible(){
        onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.recipe_detail_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(withId(R.id.simpleExoPlayer)).check(matches(isDisplayed()));
    }

    @After
    public void unregIdlingRes(){
        if (mIdlingResources != null){
            Espresso.unregisterIdlingResources(mIdlingResources);
        }
    }
}
