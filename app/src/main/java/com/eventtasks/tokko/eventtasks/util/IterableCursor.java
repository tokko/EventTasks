package com.eventtasks.tokko.eventtasks.util;

import android.database.Cursor;

import java.util.Iterator;

import static com.eventtasks.tokko.eventtasks.util.CursorIterator.iterableCursor;

public class IterableCursor implements Iterable<Cursor> {
    private Cursor cursor;

    public IterableCursor(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public Iterator<Cursor> iterator() {
        return iterableCursor(cursor);
    }
}
