package eu.laramartin.popularmovies.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.laramartin.popularmovies.R;
import eu.laramartin.popularmovies.api.FetchMoviesTask;
import eu.laramartin.popularmovies.data.FavoritesCursorLoader;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    private MoviesAdapter moviesAdapter;
    private static final String FILTER_TYPE_1 = "popular";
    private static final String FILTER_TYPE_2 = "top_rated";
    public static final int ID_FAVORITES_LOADER = 11;

    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, getSpan());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        moviesAdapter = new MoviesAdapter();
        FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
        moviesTask.execute(FILTER_TYPE_1);
        recyclerView.setAdapter(moviesAdapter);
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 4;
        }
        return 2;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_popular) {
            moviesAdapter = new MoviesAdapter();
            FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
            recyclerView.setAdapter(moviesAdapter);
            moviesTask.execute(FILTER_TYPE_1);
        }
        if (item.getItemId() == R.id.action_top_rated) {
            moviesAdapter = new MoviesAdapter();
            FetchMoviesTask moviesTask = new FetchMoviesTask(moviesAdapter);
            recyclerView.setAdapter(moviesAdapter);
            moviesTask.execute(FILTER_TYPE_2);
        }
        if (item.getItemId() == R.id.action_favorites) {
            // TODO create new FavoritesAdapter
            FavoritesAdapter favoritesAdapter = new FavoritesAdapter();
            recyclerView.setAdapter(favoritesAdapter);
            // TODO call CursorLoader
            getSupportLoaderManager().initLoader(
                    ID_FAVORITES_LOADER, null, new FavoritesCursorLoader(this, favoritesAdapter));
        }
        return super.onOptionsItemSelected(item);
    }
}