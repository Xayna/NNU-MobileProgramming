package edu.nnu.mp.simpledatabase;

/**
 * Created by User on 6/8/2016.
 */
public class BooksSQL {

    protected  static final String [] COLUMNS = {
            BooksContract. BooksEntry._ID ,
                    BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN ,
                    BooksContract.BooksEntry.COLUMN_NAME_TITLE,
                    BooksContract.BooksEntry.COLUMN_NAME_SUB_TITLE ,
                    BooksContract.BooksEntry.COLUMN_NAME_AUTHOR ,
                    BooksContract.BooksEntry.COLUMN_NAME_PUBLISH_YEAR
    };

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BooksContract.BooksEntry.TABLE_NAME + " (" +
                    BooksContract. BooksEntry._ID + BooksContract.INTEGER_TYPE +" PRIMARY KEY AUTOINCREMENT," +
                    BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN + BooksContract.TEXT_TYPE + BooksContract.COMMA_SEP +
                    BooksContract.BooksEntry.COLUMN_NAME_TITLE + BooksContract.TEXT_TYPE + BooksContract.COMMA_SEP +
                    BooksContract.BooksEntry.COLUMN_NAME_SUB_TITLE + BooksContract.TEXT_TYPE + BooksContract.COMMA_SEP +
                    BooksContract.BooksEntry.COLUMN_NAME_AUTHOR + BooksContract.TEXT_TYPE + BooksContract.COMMA_SEP +
                    BooksContract.BooksEntry.COLUMN_NAME_PUBLISH_YEAR +  BooksContract.INTEGER_TYPE +
                    " )";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BooksContract.BooksEntry.TABLE_NAME;


}
