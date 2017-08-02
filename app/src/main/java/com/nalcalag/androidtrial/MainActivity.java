package com.nalcalag.androidtrial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nalcalag.androidtrial.realm.adapter.MyRecyclerViewAdapter;
import com.nalcalag.androidtrial.realm.model.PlayerRealm;
import com.nalcalag.androidtrial.rest.adapter.APIAdapter;
import com.nalcalag.androidtrial.rest.model.DataResult;
import com.nalcalag.androidtrial.rest.model.Player;
import com.nalcalag.androidtrial.rest.model.Team;
import com.nalcalag.androidtrial.ui.adaper.PlayerAdapter;
import com.nalcalag.androidtrial.ui.adaper.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String typeSearch = null;
    private String offset = null;
    private String order = null;
    List<Player> playersList;
    List<Team> teamsList;

    //Realm
    public Realm realm;

    //Views
    private View content;
    private View favContent;
    private ProgressBar progressBar;
    private EditText searchEditText;
    private Button searchBtn;
    private RecyclerView rvPlayers;
    private RecyclerView rvTeams;
    private RecyclerView rvFavPlayers;
    private TextView tvNoResults;
    private TextView tvError;

    //REST
    private Call<DataResult> callResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Realm
        realm = Realm.getDefaultInstance();

        initViews();
        //Search button clicked
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getResultInformation(searchEditText.getText().toString());
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (callResults != null) {
            callResults.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    private void initViews() {
        content = findViewById(R.id.content);
        favContent = findViewById(R.id.fav_players_content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        searchEditText = (EditText) findViewById(R.id.search_et);
        searchBtn = (Button) findViewById(R.id.search_btn);
        rvPlayers = (RecyclerView) findViewById(R.id.players_recyclerView);
        rvTeams = (RecyclerView) findViewById(R.id.teams_recyclerView);
        rvFavPlayers = (RecyclerView) findViewById(R.id.fav_players_recyclerView);
        tvError = (TextView) findViewById(R.id.search_error_text);
        tvNoResults = (TextView) findViewById(R.id.no_results);
    }

    private void getResultInformation (final String userSearch) {
        showLoading();
        playersList = new ArrayList<>();
        teamsList = new ArrayList<>();
        callResults = new APIAdapter().getResults(userSearch, typeSearch, offset, order);
        callResults.enqueue(new Callback<DataResult>() {
            @Override
            public void onResponse(Call<DataResult> call, Response<DataResult> response) {
                List<Player> players;
                List<Team> teams;

                //Get players
                players = response.body().getPlayers();
                if (players == null)
                    rvPlayers.setVisibility(View.GONE);
                else {
                    rvPlayers.setVisibility(View.VISIBLE);
                    setRecyclerPlayersList(players);
                    PlayerAdapter playerAdapter = new PlayerAdapter(playersList, MainActivity.this, userSearch, 10);
                    rvPlayers.setAdapter(playerAdapter);
                    showResultContent();
                }

                //Get teams
                teams = response.body().getTeams();
                if (teams == null) {
                    rvTeams.setVisibility(View.GONE);
                    if (players == null)
                        showNoResults();
                } else {
                    rvTeams.setVisibility(View.VISIBLE);
                    setRecyclerTeamsList(teams);
                    TeamAdapter teamAdapter = new TeamAdapter(teamsList, getBaseContext(), userSearch, 10);
                    rvTeams.setAdapter(teamAdapter);
                    showResultContent();
                }
            }

            @Override
            public void onFailure(Call<DataResult> call, Throwable t) {
                showError();
            }
        });
    }

    public void setRecyclerPlayersList (List<Player> players) {
        Player header = new Player();
        header.setHeader(true);
        Player footer = new Player();
        footer.setFooter(true);
        //Add header
        playersList.add(header);
        //Add content
        playersList.addAll(players);
        //Add footer
        playersList.add(footer);
    }

    public void setRecyclerTeamsList (List<Team> teams) {
        Team header = new Team();
        header.setHeader(true);
        Team footer = new Team();
        footer.setFooter(true);
        //Add header
        teamsList.add(header);
        //Add content
        teamsList.addAll(teams);
        //Add footer
        teamsList.add(footer);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        tvNoResults.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        favContent.setVisibility(View.GONE);
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        tvNoResults.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        favContent.setVisibility(View.GONE);
    }

    private void showResultContent() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        favContent.setVisibility(View.GONE);
        tvError.setVisibility(View.GONE);
    }

    private void showNoResults() {
        progressBar.setVisibility(View.GONE);
        tvNoResults.setVisibility(View.VISIBLE);
        content.setVisibility(View.VISIBLE);
        favContent.setVisibility(View.GONE);
    }

    //Top Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.star) {
            showFavoritePlayers();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showFavoritePlayers() {
        content.setVisibility(View.GONE);
        favContent.setVisibility(View.VISIBLE);

        MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(realm.where(PlayerRealm.class).findAll(), getBaseContext(), MainActivity.this);
        rvFavPlayers.setAdapter(adapter);

    }
}
