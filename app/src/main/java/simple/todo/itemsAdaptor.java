package simple.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static android.R.*;

public class itemsAdaptor extends RecyclerView.Adapter<itemsAdaptor.ViewHolder> {
   public interface OnLongClickListener {
       void onItemLongClicked(int position);
   }
    List<String> items;
   OnLongClickListener longClickListener;
    public itemsAdaptor(List<String> items, OnLongClickListener longClickListener) {
        this.items = items;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //use layout inflator to inflate a view
        View toolView = LayoutInflater.from(parent.getContext()).inflate(layout.simple_list_item_1, parent, false);
        return new ViewHolder(toolView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    String item = items.get(position);
    holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //container to provide easy access to views tgat represent each row of the list
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(id.text1);

        }
//update the view inside of the view holder with this data
        public void bind(String item) {
            tvItem.setText(item);
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //notify long press
                    longClickListener.onItemLongClicked(getAdapterPosition());
                    return false;
                }
            });
        }

    }

}
