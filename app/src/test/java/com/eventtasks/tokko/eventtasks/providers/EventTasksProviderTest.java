package com.eventtasks.tokko.eventtasks.providers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.eventtasks.tokko.eventtasks.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import java.util.ArrayList;
import java.util.List;

import static com.eventtasks.tokko.eventtasks.util.CursorIterator.iterableCursor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void events_Insert() {
        ContentValues c = new ContentValues();
        String title = "title";
        c.put(EventTasksProvider.TITLE, title);
        Uri insert = mContentResolver.insert(EventTasksProvider.URI_EVENTS, c);
        assertEquals(11, ContentUris.parseId(insert));
    }

    @Test
    public void events_Query() {
        Cursor c = mContentResolver.query(EventTasksProvider.URI_EVENTS, null, null, null, null);
        int prevsize = c.getCount();
        c.close();
        ContentValues cv = new ContentValues();
        String title1 = "title1";
        String title2 = "title2";
        ArrayList<String> titles = new ArrayList<String>() {{
            add(title1);
            add(title2);
        }};
        cv.put(EventTasksProvider.TITLE, title1);
        mContentResolver.insert(EventTasksProvider.URI_EVENTS, cv);
        cv.clear();
        cv.put(EventTasksProvider.TITLE, title2);
        mContentResolver.insert(EventTasksProvider.URI_EVENTS, cv);

        c = mContentResolver.query(EventTasksProvider.URI_EVENTS, null, null, null, null);
        assertNotNull(c);
        assertEquals(prevsize + titles.size(), c.getCount());
        assertEquals(2, c.getColumnCount());
        assertTrue(c.getColumnIndex(EventTasksProvider.ID) > -1);
        assertTrue(c.getColumnIndex(EventTasksProvider.TITLE) > -1);

        List<String> actual = Stream.of(iterableCursor(c)).map(c1 -> c1.getString(c1.getColumnIndex(EventTasksProvider.TITLE))).collect(Collectors.toList());
        assertTrue(actual.containsAll(titles));
        c.close();
    }
}