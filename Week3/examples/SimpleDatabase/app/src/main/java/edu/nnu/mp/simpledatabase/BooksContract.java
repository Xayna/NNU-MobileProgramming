package edu.nnu.mp.simpledatabase;

import android.provider.BaseColumns;

/**
 * Created by User on 6/8/2016.
 */
public final class BooksContract  {

    public BooksContract () {}

    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER" ;
    protected static final String COMMA_SEP = ",";

    public static abstract  class BooksEntry  implements BaseColumns {
        public static final String TABLE_NAME = "Books";
        public static final String COLUMN_NAME_BOOK_ISBN = "isbn";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUB_TITLE = "subtitle";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_PUBLISH_YEAR = "publishyear";

    }

    public static abstract class AuthorEntry implements  BaseColumns
    {
        public static final String AUTH_NAME = "NAME";
    }


}
