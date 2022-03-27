package presentation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventorysystem.R;

import objects.Item;

//ItemsAdapter
//Creates an adapter extending from RecyclerView, which allows stock items to be dynamically displayed on the screen
//This class specifies a custom ViewHolder class which gives us access to our custom view
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    //Array of item objects currently in the database
    private Item[] stockItems;
    private OnItemListener itemListener;

    //Provides reference to each of the views within an item object
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        //Member variables for every view element in stock_item.xml
        //These will hold the data to be displayed in the recycler view
        public TextView itemName;
        public TextView itemQuantity;
        public TextView itemUnit;
        public Button goToItemView;

        //Item listener object for handling what happens when the current view holder is tapped
        OnItemListener onItemListener;

        //View Holder Constructor: accepts entire item row and does view lookups to find each subview
        public ViewHolder(View itemView, OnItemListener onItemListener) {
            //itemView is passed to parent class and is stored as a variable that can be used to access content from any view holder instance
            super(itemView);

            //Sets all text view variables to their corresponding layout in the given view
            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemUnit = itemView.findViewById(R.id.itemUnit);
            goToItemView = itemView.findViewById(R.id.itemPageButton);

            //Sets the passed item listener to the listener for this view
            this.onItemListener = onItemListener;

            //Sets the click listener to the entire view holder
            goToItemView.setOnClickListener(this);
        }

        //Gets the index of the current adapter and passes it to the click listener
        @Override
        public void onClick(View view) {
            onItemListener.onItemClick(getAdapterPosition());
        }
    }

    //Item Adapter Constructor:
    //Takes in stock item array and assigns it to a private local variable
    public ItemsAdapter(Item[] items, OnItemListener onItemListener){
        stockItems = items;
        itemListener = onItemListener;
    }

    //Takes in layout XML and returns it to holder
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Gets parent context and creates new inflater from that context
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflates custom layout for each row of data
        View itemView = inflater.inflate(R.layout.stock_item, parent, false);

        //Returns a new holder instance
        return new ViewHolder(itemView, itemListener);
    }

    //Populates data into the item through holder
    @Override
    public void onBindViewHolder(ItemsAdapter.ViewHolder holder, int index) {
        //Get data for item object based on given index
        Item item = (Item) stockItems[index];

        //Gets needed views from the passed item views
        TextView name = holder.itemName;
        TextView quantity = holder.itemQuantity;
        TextView unit = holder.itemUnit;

        //Assigns views data from the current object
        name.setText(item.getName());
        quantity.setText(Integer.toString(item.getQuantity()));
        unit.setText(item.getQuantityMetric());
    }

    //Returns total count of items currently in the array
    @Override
    public int getItemCount() {
        int length = -1;
        if(stockItems != null){
            length = stockItems.length;
        }
        return length;
    }

    public interface OnItemListener{
        void onItemClick(int index);
    }
}
