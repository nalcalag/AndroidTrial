/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nalcalag.androidtrial.realm.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nalcalag.androidtrial.MainActivity;
import com.nalcalag.androidtrial.R;
import com.nalcalag.androidtrial.realm.model.PlayerRealm;

import io.realm.Realm;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

public class MyRecyclerViewAdapter extends RealmRecyclerViewAdapter<PlayerRealm, MyRecyclerViewAdapter.MyViewHolder> {

    private Activity activity;
    RealmResults<PlayerRealm> players;

    public MyRecyclerViewAdapter(RealmResults<PlayerRealm> data, Context context, Activity activity) {
        super(context, data,true);
        setHasStableIds(true);
        this.activity = activity;
        this.players = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_player, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final PlayerRealm p = getItem(position);

        String txtName = p.getFirstName() + " " + p.getSecondName();
        holder.tvPlayerName.setText(txtName);
        String txtAge = "Age: " + p.getAge();
        holder.tvPlayerAge.setText(txtAge);
        String txtClub = "Club: " + p.getClub();
        holder.tvPlayerClub.setText(txtClub);

        holder.addPlayer.setVisibility(View.GONE);
        holder.deletePlayer.setVisibility(View.VISIBLE);
        holder.deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete from RealmDB
                final RealmResults<PlayerRealm> player = ((MainActivity)activity).realm.where(PlayerRealm.class)
                        .equalTo("firstName", p.getFirstName()).findAll();
                ((MainActivity)activity).realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        player.deleteFromRealm(0);
                    }
                });

                //Delete from RecyclerView
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public long getItemId(int index) {
        return getItem(index).getId();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvPlayerName;
        TextView tvPlayerAge;
        TextView tvPlayerClub;
        ImageView deletePlayer;
        ImageView addPlayer;

        MyViewHolder(View view) {
            super(view);
            tvPlayerName = (TextView) view.findViewById(R.id.item_player_name);
            tvPlayerAge = (TextView) view.findViewById(R.id.item_player_age);
            tvPlayerClub = (TextView) view.findViewById(R.id.item_player_club);
            deletePlayer = (ImageView) view.findViewById(R.id.delete_player);
            addPlayer = (ImageView) view.findViewById(R.id.add_player);
        }
    }
}
