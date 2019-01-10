package com.straylense.geonotes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<NoteEntity> notes;
    private DataBase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupUI();

        database = DataBase.getInstance(this);
        queryNotes(new QueryCallback() {
            @Override
            public void consume(List notes) {
                MainActivity.this.notes = notes;
            }
        });

        GridFragment gridFragment = GridFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, gridFragment).commit();
    }

    private void setupUI() {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public List<NoteEntity> getNotes() {
        return notes;
    }

    public void queryNotes(QueryCallback callback) {
        new QueryTask(callback).execute();
    }

    public interface QueryCallback<T> {
        void consume(List<NoteEntity> notes);
    }


    public class QueryTask extends AsyncTask<Void, Void, List<NoteEntity>> {

        private QueryCallback callback;

        public QueryTask(QueryCallback callback) {
            this.callback = callback;
        }

        @Override
        protected List<NoteEntity> doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(List<NoteEntity> notes) {
            if (callback != null) {
                callback.consume(notes);
            }
        }
    }

}
