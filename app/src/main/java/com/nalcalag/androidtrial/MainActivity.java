package com.nalcalag.androidtrial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nalcalag.androidtrial.rest.adapter.APIAdapter;
import com.nalcalag.androidtrial.rest.model.DataResult;
import com.nalcalag.androidtrial.rest.model.Player;
import com.nalcalag.androidtrial.rest.model.Team;
import com.nalcalag.androidtrial.ui.PlayerAdapter;
import com.nalcalag.androidtrial.ui.TeamAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String typeSearch = null;
    private String offset = null;
    private String order = null;
    List<Player> playersList = new ArrayList<>();
    List<Team> teamsList = new ArrayList<>();

    //Views
    private View content;
    private ProgressBar progressBar;
    private EditText searchEditText;
    private Button searchBtn;
    private RecyclerView rvPlayers;
    private RecyclerView rvTeams;
    private TextView tvNoResults;
    private TextView tvNoPlayers;
    private TextView tvNoTeams;
    private TextView tvError;

    //REST
    private Call<DataResult> callResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
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

    private void initViews() {
        content = findViewById(R.id.content);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        searchEditText = (EditText) findViewById(R.id.search_et);
        searchBtn = (Button) findViewById(R.id.search_btn);
        rvPlayers = (RecyclerView) findViewById(R.id.players_recyclerView);
        rvTeams = (RecyclerView) findViewById(R.id.teams_recyclerView);
        tvError = (TextView) findViewById(R.id.search_error_text);
        tvNoPlayers = (TextView) findViewById(R.id.no_players);
        tvNoTeams = (TextView) findViewById(R.id.no_teams);
        tvNoResults = (TextView) findViewById(R.id.no_results);
    }

    private void getResultInformation (final String userSearch) {
        showLoading();
        callResults = new APIAdapter().getResults(userSearch, typeSearch, offset, order);
        callResults.enqueue(new Callback<DataResult>() {
            @Override
            public void onResponse(Call<DataResult> call, Response<DataResult> response) {
                //Get players
                List<Player> players = response.body().getPlayers();

                setRecyclerPlayersList(players);
                PlayerAdapter playerAdapter = new PlayerAdapter(playersList);
                rvPlayers.setAdapter(playerAdapter);

                //Get teams
                List<Team> teams = response.body().getTeams();

                setRecyclerTeamsList(teams);
                TeamAdapter teamAdapter = new TeamAdapter(teamsList);
                rvTeams.setAdapter(teamAdapter);

                showResultContent();
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

        playersList.add(header);
        playersList.addAll(players);
        playersList.add(footer);
    }

    public void setRecyclerTeamsList (List<Team> teams) {
        Team header = new Team();
        header.setHeader(true);
        Team footer = new Team();
        footer.setFooter(true);

        teamsList.add(header);
        teamsList.addAll(teams);
        teamsList.add(footer);
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        content.setVisibility(View.GONE);
        tvNoResults.setVisibility(View.GONE);
    }

    private void showError() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.GONE);
        tvNoResults.setVisibility(View.VISIBLE);
    }

    private void showResultContent() {
        progressBar.setVisibility(View.GONE);
        content.setVisibility(View.VISIBLE);
        tvError.setVisibility(View.GONE);
    }
}
