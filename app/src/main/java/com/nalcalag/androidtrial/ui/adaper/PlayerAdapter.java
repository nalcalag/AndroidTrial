package com.nalcalag.androidtrial.ui.adaper;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nalcalag.androidtrial.MainActivity;
import com.nalcalag.androidtrial.R;
import com.nalcalag.androidtrial.realm.model.PlayerRealm;
import com.nalcalag.androidtrial.rest.adapter.APIAdapter;
import com.nalcalag.androidtrial.rest.model.DataResultPlayers;
import com.nalcalag.androidtrial.rest.model.Player;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by nalcalag on 31/07/2017.
 */

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.ViewHolder> {

    private List<Player> playerList;
    private Activity activity;
    private String search;
    private int offset;
    private static String TYPE_SEARCH = "players";
    private String order = null;

    private class VIEWS_TYPES {
        public static final int Header=1;
        public static final int Normal=2;
        public static final int Footer=3;
    }
    boolean isFooter = false;
    boolean isHeader = false;

    public PlayerAdapter(List<Player> playerList, Activity activity, String search, int offset) {
        this.playerList = playerList;
        this.activity = activity;
        this.search = search;
        this.offset = offset;
    }

    @Override
    public PlayerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView;

        switch (viewType) {
            case VIEWS_TYPES.Normal:
                isHeader = false;
                isFooter = false;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
                break;
            case VIEWS_TYPES.Header:
                isHeader = true;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.title_list, parent, false);
                break;
            case VIEWS_TYPES.Footer:
                isFooter = true;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.footer_list, parent, false);
                break;
            default:
                isHeader = false;
                isFooter = false;
                rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_player, parent, false);
                break;
        }

        return  new ViewHolder(rowView);
    }

    @Override
    public void onBindViewHolder(final PlayerAdapter.ViewHolder holder, final int position) {

        if (isHeader) {
            holder.tvTitle.setText("Players");

        } else if (isFooter) {
            holder.tvFooter.setText("Load More Players ...");
            //Set OnClick for Load More..
            holder.tvFooter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.progressBar.setVisibility(View.VISIBLE);
                    Call<DataResultPlayers> callResults = new APIAdapter().getPlayers(search,TYPE_SEARCH,offset,order);
                    callResults.enqueue(new Callback<DataResultPlayers>() {
                        @Override
                        public void onResponse(Call<DataResultPlayers> call, Response<DataResultPlayers> response) {
                            List<Player> newPlayers = response.body().getPlayers();
                            if (newPlayers == null)
                                holder.tvFooter.setVisibility(View.GONE);
                            else {
                                //Add Items
                                for (int i = 0; i < newPlayers.size()-1; i++) {
                                    addItem(playerList.size()-1, newPlayers.get(i));
                                }
                                if (newPlayers.size() < 10)
                                    holder.tvFooter.setVisibility(View.GONE);
                                else
                                    offset += 10;
                            }
                            holder.progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Call<DataResultPlayers> call, Throwable t) {
                            holder.tvFooter.setVisibility(View.GONE);
                            holder.progressBar.setVisibility(View.GONE);
                        }
                    });
                }
            });

        } else {
            holder.tvPlayerName.setText(playerList.get(position).getFirstName() + " " + playerList.get(position).getSecondName());
            holder.tvPlayerAge.setText("Age: " + playerList.get(position).getAge().toString());
            holder.tvPlayerClub.setText("Club: " + playerList.get(position).getClub());

            //Add PlayerRealm to RealmDB
            holder.addImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity)activity).realm.executeTransactionAsync(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            PlayerRealm player = realm.createObject(PlayerRealm.class);
                            player.setId(playerList.get(position).getId());
                            player.setFirstName(playerList.get(position).getFirstName());
                            player.setSecondName(playerList.get(position).getSecondName());
                            player.setNationality(playerList.get(position).getNationality());
                            player.setAge(playerList.get(position).getAge());
                            player.setClub(playerList.get(position).getClub());
                        }
                    }, new Realm.Transaction.OnSuccess() {
                        @Override
                        public void onSuccess() {
                            Log.v("Success", "--------->OK<---------");
                            Toast.makeText(activity, "Player saved", Toast.LENGTH_SHORT).show();
                        }
                    }, new Realm.Transaction.OnError() {
                        @Override
                        public void onError(Throwable error) {
                            Log.e("Failed", error.getMessage());
                            Toast.makeText(activity, "Sorry, player not saved", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (playerList.get(position).isFooter())
            return VIEWS_TYPES.Footer;
        else if (playerList.get(position).isHeader())
            return VIEWS_TYPES.Header;
        else
            return VIEWS_TYPES.Normal;
    }

    public final void addItem(int position, Player player) {
        playerList.add(position, player);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvPlayerAge;
        TextView tvPlayerClub;
        TextView tvTitle;
        TextView tvFooter;
        ImageView addImage;
        ProgressBar progressBar;

        ViewHolder(View view) {
            super(view);

            tvPlayerName = (TextView) view.findViewById(R.id.item_player_name);
            tvPlayerAge = (TextView) view.findViewById(R.id.item_player_age);
            tvPlayerClub = (TextView) view.findViewById(R.id.item_player_club);
            tvTitle = (TextView) view.findViewById(R.id.tv_header);
            tvFooter = (TextView) view.findViewById(R.id.tv_footer);
            progressBar = (ProgressBar) view.findViewById(R.id.progress_bar_load);
            addImage = (ImageView) view.findViewById(R.id.add_player);
        }
    }
}
