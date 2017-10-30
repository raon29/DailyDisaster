package com.dailydisaster;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class DBAdapter extends CursorAdapter{
    public DBAdapter(Context context, Cursor c){
        super(context, c, 0);
    }
    @Override
    public void bindView(View view, Context context, Cursor cursor){
        final TextView _schedule = (TextView)view.findViewById(R.id.schedule);
        double DateTime = cursor.getInt(cursor.getColumnIndex("datetime"));
        String print = (int)(DateTime/100000000+2000)+"/"+(int)(DateTime%100000000)/1000000+"/"+(int)(DateTime%1000000)/10000+" "+(int)(DateTime%10000)/100+":"+(int)(DateTime%100);
        _schedule.setText(print+cursor.getString(cursor.getColumnIndex("name")));
    }
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.activity_dblist, parent, false);
        return v;
    }
}
