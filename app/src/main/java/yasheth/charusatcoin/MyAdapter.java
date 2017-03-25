package yasheth.charusatcoin;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Map<Integer, ArrayList<String>> map;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public TextView mTransactionId,mVendorName,mTransactionDate,mTransactionAmount;
        public MyViewHolder(View v) {
            super(v);

            mCardView = (CardView) v.findViewById(R.id.card_view);
            mTransactionId = (TextView) v.findViewById(R.id.tv_text);
            mVendorName = (TextView) v.findViewById(R.id.tv_shop);
            mTransactionDate = (TextView) v.findViewById(R.id.tv_date);
            mTransactionAmount = (TextView) v.findViewById(R.id.tv_amount);

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(Map<Integer, ArrayList<String>> myDataset) {
        map = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTransactionId.setText(map.get(position).get(0));
        holder.mTransactionAmount.setText(map.get(position).get(1));
        holder.mVendorName.setText(map.get(position).get(2));
        holder.mTransactionDate.setText(map.get(position).get(3));
    }

    @Override
    public int getItemCount() {
        return map.size();
    }
}