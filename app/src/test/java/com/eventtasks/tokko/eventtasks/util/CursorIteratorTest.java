package com.eventtasks.tokko.eventtasks.util;

import android.database.Cursor;
import android.database.MatrixCursor;

import com.eventtasks.tokko.eventtasks.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static com.eventtasks.tokko.eventtasks.util.CursorIterator.iterableCursor;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CursorIteratorTest {

    private CursorIterator iterableCursor;

    @Before
    public void setup() {
        MatrixCursor cursor = new MatrixCursor(new String[]{"first", "second"}, 3);
        cursor.addRow(new Object[]{1, "row1"});
        cursor.addRow(new Object[]{2, "row2"});
        cursor.addRow(new Object[]{3, "row3"});
        iterableCursor = iterableCursor(cursor);
    }

    @Test
    public void testHasNext() throws Exception {
        assertTrue(iterableCursor.hasNext());
        iterableCursor.next();
        assertTrue(iterableCursor.hasNext());
        iterableCursor.next();
        assertTrue(iterableCursor.hasNext());
        iterableCursor.next();
        assertFalse(iterableCursor.hasNext());
    }

    @Test
    public void testNext() throws Exception {
        for (int i = 1; i <= 3; i++) {
            Cursor c = iterableCursor.next();
            int first = c.getInt(c.getColumnIndex("first"));
            String second = c.getString(c.getColumnIndex("second"));
            assertEquals(i, first);
            assertEquals("row" + i, second);
        }
    }

    @Test
    public void testRemove() throws Exception {

    }
}