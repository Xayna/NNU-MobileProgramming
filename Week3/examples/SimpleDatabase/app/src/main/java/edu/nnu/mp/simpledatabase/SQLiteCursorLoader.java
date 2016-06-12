package edu.nnu.mp.simpledatabase;

import android.content.Context;
import android.content.Loader;
import android.database.AbstractCursor;

/**
 * Created by User on 6/9/2016.
 */
public class SQLiteCursorLoader extends Loader {
    /**
     * Stores away the application context associated with context.
     * Since Loaders can be used across multiple activities it's dangerous to
     * store the context directly; always use {@link #getContext()} to retrieve
     * the Loader's Context, don't use the constructor argument directly.
     * The Context returned by {@link #getContext} is safe to use across
     * Activity instances.
     *
     * @param context used to retrieve the application context.
     */
    public SQLiteCursorLoader(Context context) {
        super(context);
    }
}
