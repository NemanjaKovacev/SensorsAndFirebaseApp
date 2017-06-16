package com.example.nemanja.sensorsandfirebaseapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nemanja.sensorsandfirebaseapp.R;
import com.example.nemanja.sensorsandfirebaseapp.models.FirebaseUser;

import java.util.List;

public class FirebaseListViewAdapter extends BaseAdapter {

    private final Activity activity;
    private final List<FirebaseUser> lstFirebaseUsers;
    private LayoutInflater inflater;

    public FirebaseListViewAdapter(Activity activity, List<FirebaseUser> lstFirebaseUsers) {
        this.activity = activity;
        this.lstFirebaseUsers = lstFirebaseUsers;
    }

    @Override
    public int getCount() {
        return lstFirebaseUsers.size();
    }

    @Override
    public Object getItem(int i) {
        return lstFirebaseUsers.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) activity.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.firebase_listview_item, null);

        TextView txtUser = (TextView) itemView.findViewById(R.id.list_name);
        TextView txtEmail = (TextView) itemView.findViewById(R.id.list_email);

        txtUser.setText(lstFirebaseUsers.get(i).getName());
        txtEmail.setText(lstFirebaseUsers.get(i).getEmail());

        return itemView;
    }
}
