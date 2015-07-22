package com.eventtasks.tokko.eventtasks.providers;

import android.content.ContentResolver;

import com.eventtasks.tokko.eventtasks.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class EventTasksProviderTest {

    private EventTasksProvider mProvider;
    private ContentResolver mContentResolver;

    @Before
    public void setUp() throws Exception {
        mProvider = new EventTasksProvider();
        mContentResolver = RuntimeEnvironment.application.getContentResolver();
        mProvider.onCreate();
        ShadowContentResolver.registerProvider(EventTasksProvider.AUTHORITY, mProvider);
    }

    @Test
    public void preConditions() {
        assertNotNull(mProvider);
        assertNotNull(mContentResolver);
        assertNotNull(Shadows.shadowOf(mContentResolver).acquireContentProviderClient(EventTasksProvider.URI_EVENTS));
    }
}