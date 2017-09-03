package com.singletondev.bakingapp.idlingResources;

import android.support.annotation.Nullable;
import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Randy Arba on 8/31/17.
 * This apps contains BakingApp
 *
 * @email randy.arba@gmail.com
 * @github https://github.com/Leviaran
 */

public class IdlingResources implements IdlingResource {
    @Nullable
    ResourceCallback mCallback;
    private AtomicBoolean mIdleControl = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return mIdleControl.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.mCallback = callback;
    }

    public void setIdleController(Boolean isIdle){
        mIdleControl.set(isIdle);
        if (isIdle && mCallback != null){
            mCallback.onTransitionToIdle();
        }
    }
}
