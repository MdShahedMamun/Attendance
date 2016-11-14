package app.com.example.shahed.attendance;

/**
 * Created by shahed on 16-Jun-16.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    ArrayList<String> stringArrayList;
    Activity context;
    boolean[] itemChecked;
    private final String LOG_TAG = CustomAdapter.class.getSimpleName();

    public CustomAdapter(Activity context, ArrayList<String> stringArrayList) {
        super();
        this.context = context;
        this.stringArrayList = stringArrayList;
        itemChecked = new boolean[stringArrayList.size()];
    }

    public boolean[] getItemCheckedArray() {
        return itemChecked;
    }

    private class ViewHolder {
        TextView textView;
        CheckBox checkBox;
    }

    public int getCount() {
        return stringArrayList.size();
    }

    public Object getItem(int position) {
        return stringArrayList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        LayoutInflater inflater = context.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();

            holder.textView = (TextView) convertView
                    .findViewById(R.id.textView1);
            holder.checkBox = (CheckBox) convertView
                    .findViewById(R.id.checkBox1);

            convertView.setTag(holder);
//            Log.v(LOG_TAG, "inside getView if holder: " + holder.toString());
        } else {
            holder = (ViewHolder) convertView.getTag();
//            Log.v(LOG_TAG, "inside getView if holder: " + holder.toString());
        }

        String string = (String) getItem(position);
        holder.textView.setText(string);
        holder.checkBox.setChecked(false);

//        Log.v(LOG_TAG, "inside getView  checkBox.isChecked: " + holder.checkBox.isChecked());
//        Log.v(LOG_TAG, "inside getView itemChecked[position]: " + itemChecked[position] + "  position:" + position);

        if (itemChecked[position])
            holder.checkBox.setChecked(true);
        else
            holder.checkBox.setChecked(false);
//        Log.v(LOG_TAG, "inside getView itemChecked[position]: " + itemChecked[position] + "  position:" + position);

        holder.checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                itemChecked[position] = holder.checkBox.isChecked();
//                Log.v(LOG_TAG, "position: " + position + "  itemChecked[position]: " + itemChecked[position]
//                        + "  holder.checkBox.isChecked: " + holder.checkBox.isChecked());
            }
        });

        return convertView;

    }

}