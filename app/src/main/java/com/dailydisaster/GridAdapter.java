package com.dailydisaster;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class GridAdapter extends CursorAdapter {
    public GridAdapter(Context context, Cursor c){
        super(context, c, 0);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        final TextView _week = (TextView)view.findViewById(R.id.schedule);//checkWeek
        _week.setText(cursor.getString(cursor.getColumnIndex("name")));
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_dblist, parent, false);
        return v;
    }
}
