package com.eventtasks.tokko.eventtasks;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.eventtasks.tokko.eventtasks.providers.EventTasksProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowContentResolver;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 19, manifest = "/app/src/main/AndroidManifest.xmls")
public class EventListTests {
    private EventListActivity eventListActivity;
    private MatrixCursor cursor;

    @Before
    public void setUp() throws Exception {
        cursor = new MatrixCursor(new String[]{EventTasksProvider.ID, EventTasksProvider.TITLE});
        for (int i = 0; i < 10; i++) {
            cursor.addRow(new Object[]{i, "EVENT" + i});
        }
        EventTasksProvider real = new EventTasksProvider() {
            @Override
            public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
                return cursor;
            }
        };
        //given(cp.query(eq(EventTasksProvider.URI_EVENTS), eq(null), eq(null), eq(null), eq(null))).willReturn(cursor);
        ShadowContentResolver.registerProvider(EventTasksProvider.AUTHORITY, real);
        eventListActivity = Robolectric.buildActivity(EventListActivity.class).create().start().resume().visible().get();
    }

    @Test
    public void testListContainsElements() throws Exception {
        ListView lv = (ListView) eventListActivity.findViewById(android.R.id.list);
        assertNotNull(lv);
        ListAdapter adapter = lv.getAdapter();
        assertNotNull(adapter);
        assertThat(adapter.getCount(), is(10));
        for (int i = 0; cursor.moveToPosition(i); i++) {
            assertThat(cursor.getInt(cursor.getColumnIndex(EventTasksProvider.ID)), is(i));
            assertThat(cursor.getString(cursor.getColumnIndex(EventTasksProvider.TITLE)), is("EVENT" + i));
        }
    }
}
