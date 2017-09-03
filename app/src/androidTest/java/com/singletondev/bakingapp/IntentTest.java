package com.singletondev.bakingapp;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.not;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.Espresso.onView;
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
public class IntentTest {

    @Rule
    public IntentsTestRule<Besa> mActivityTest = new IntentsTestRule<Besa>(Besa.class);
    private IdlingResource mIdlingResources;

    @Before
    public void registerIdlingRes(){
        mIdlingResources = mActivityTest.getActivity().getIdleRes();
        //Check with Espresso
        Espresso.registerIdlingResources(mIdlingResources);
    }

    @Before
    public void stubAllExIntent(){
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

    }

    @Test
    public void checkIntent(){
        onView(ViewMatchers.withId(R.id.recipe_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        intended(hasComponent(RecipeDetailACtivity.class.getName()));
    }

    @After
    public void unregIdlingRes(){
        if (mIdlingResources != null){
            Espresso.unregisterIdlingResources(mIdlingResources);
        }
    }

}
