package edu.nnu.mp.simpledatabase;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity   implements LoaderManager.LoaderCallbacks<Cursor> {

    private  DatabaseHelper helper ;
    private SimpleCursorAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new DatabaseHelper
         helper = new DatabaseHelper(getBaseContext(),DatabaseHelper.DATABASE_NAME,DatabaseHelper.DATABASE_VERSION);

        // Empty database at the start
        clearAll();

        // Insert records
        insertBooks();

        //set the cursor
        Cursor c = readBooks();
        //initialize adapter
        adapter = new SimpleCursorAdapter(this, R.layout.list_layout, c,
                new String [] {BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN, BooksContract.BooksEntry.COLUMN_NAME_TITLE }, new int[] { R.id._id, R.id.name },
                0);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);

      //  getLoaderManager().initLoader(0, null, this);


        Button fixButton = (Button) findViewById(R.id.fix_button);
        fixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // execute database operations
                fix();

                // Redisplay data
                /*
                 Actually, you should refresh your cursor by running the code again to get
                 the Cursor, and do so on a background thread. You can then refresh your
                 ListView by calling changeCursor() or swapCursor() on the CursorAdapter.
                 We use the deprecated requery() method here for simplicity, but on a
                 large DB this could block the UI.
                 */
                //getLoaderManager().initLoader(0, null, MainActivity.this);
                adapter.getCursor().requery();
                adapter.notifyDataSetChanged();
            }
        });
    }

    // Insert several artist records
    private void insertBooks() {

        ContentValues values = new ContentValues();

        SQLiteDatabase db = helper.getWritableDatabase();

        //book Android Programming for Beginners
        values.put(BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN, "");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_TITLE, "Android Programming");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_AUTHOR, "J horton");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_PUBLISH_YEAR, "2015");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_SUB_TITLE, "");

        // Insert the new row, returning the primary key value of the new row
        long newRowId;
        newRowId = db.insert(
                BooksContract.BooksEntry.TABLE_NAME,
                null,
                values);

        Log.d("Database", "insertBooks: new record id : " + newRowId);

        values.clear();

        //book Android Programming: The Big Nerd Ranch Guide (2nd Edition)
        values.put(BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN, "ISBN-10: 0134171454");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_TITLE, "Android Programming: The Big Nerd Ranch Guide (2nd Edition)");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_AUTHOR, "Phillips , stewart & hardy");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_PUBLISH_YEAR, "2015");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_SUB_TITLE, "https://www.bignerdranch.com/we-write/android-programming/");

        newRowId = db.insert(
                BooksContract.BooksEntry.TABLE_NAME,
                null,
                values);

        Log.d("Database", "insertBooks: new record id : " + newRowId);

        //book Advanced Android Application Development (4th Edition)
        values.put(BooksContract.BooksEntry.COLUMN_NAME_BOOK_ISBN, "ISBN-10: 0133892387");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_TITLE, "Advanced Android Application Development (4th Edition)");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_AUTHOR, "Joseph Annuzzi, Lauren Darcey, Shane Conder");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_PUBLISH_YEAR, "2014");
        values.put(BooksContract.BooksEntry.COLUMN_NAME_SUB_TITLE, "http://www.informit.com/store/advanced-android-application-development-9780133892383");

        newRowId = db.insert(
                BooksContract.BooksEntry.TABLE_NAME,
                null,
                values);

        Log.d("Database", "insertBooks: new record id : " + newRowId);


    }

    // Returns all artist records in the database
    private Cursor readBooks() {
        return helper.getReadableDatabase().query(BooksContract.BooksEntry.TABLE_NAME,
                BooksSQL.COLUMNS, null, new String[] {}, null, null,
                null);
    }

    // Modify the contents of the database
    private void fix() {


        // Fix the name
        ContentValues values = new ContentValues();
        values.put(BooksContract.BooksEntry.COLUMN_NAME_TITLE, "Android Programming for Beginners");

        helper.getWritableDatabase().update(BooksContract.BooksEntry.TABLE_NAME, values,
                BooksContract.BooksEntry.COLUMN_NAME_AUTHOR  + "=?",
                new String[] { "J horton" });
    }

    // Delete all records
    private void clearAll() {
        helper.getWritableDatabase().delete(BooksContract.BooksEntry.TABLE_NAME, null,null);
    }

    // Close database
    @Override
    protected void onDestroy() {
        if (helper.getReadableDatabase().isOpen() )
        {
            helper.getWritableDatabase().close();
        }
        helper.deleteDatabase();

        super.onDestroy();
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (id == 0)
            return (Loader<Cursor>) readBooks();
        else
            return  null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
