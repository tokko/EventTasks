package com.eventtasks.tokko.eventtasks.util;

import android.database.Cursor;

import java.util.Iterator;

public class CursorIterator implements Iterator<Cursor> {
    private Cursor cursor;

    public CursorIterator(Cursor cursor) {
        this.cursor = cursor;
        cursor.moveToPosition(-1);
    }

    public static CursorIterator iterableCursor(Cursor cursor1) {
        return new CursorIterator(cursor1);
    }

    @Override
    public boolean hasNext() {
        boolean hasNext = cursor.moveToNext();
        cursor.moveToPrevious();
        return hasNext;
    }

    @Override
    public Cursor next() {
        cursor.moveToNext();
        return cursor;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Can't remove from cursor");
    }
}
