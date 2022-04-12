package presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warehouseinventorysystem.R;

import objects.Inventory;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private Inventory[] inventories;
    private OnInventoryListener inventoryListener;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView invName;
        public Button goToInv;

        //Inventory listener object for handling what happens when the current view holder is tapped
        OnInventoryListener onInventoryListener;

        public ViewHolder(View invView, OnInventoryListener onInventoryListener){
            super(invView);

            //Sets all text view variables to their corresponding layout in the given view
            invName = invView.findViewById(R.id.itemName);
            goToInv = invView.findViewById(R.id.itemPageButton);

            //Sets the passed item listener to the listener for this view
            this.onInventoryListener = onInventoryListener;

            //Sets the on click listener
            goToInv.setOnClickListener(this);
        }

        //Gets index of current adapter position in list and passes it to click listener
        @Override
        public void onClick(View view){onInventoryListener.onInventoryClick(getAdapterPosition());}
    }

    //Adapter Constructor
    //Takes in an array of inventories and an on click listener
    public InventoryAdapter(Inventory[] inventories, OnInventoryListener onInventoryListener){
        this.inventories = inventories;
        inventoryListener = onInventoryListener;
    }

    //Takes in layout XML and returns it to holder
    @Override
    public InventoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        //Gets parent context and creates new inflater from that context
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //Inflates custom layout for each row of data
        View invView = inflater.inflate(R.layout.stock_item, parent, false);

        //Returns a new holder instance
        return new ViewHolder(invView, inventoryListener);
    }

    //Populates data into the item through holder
    @Override
    public void onBindViewHolder(InventoryAdapter.ViewHolder holder, int index){
        //Get data for inventory object based on given index
        Inventory inventory =(Inventory) inventories[index];

        //Gets needed views from the passed inventory views
        TextView name = holder.invName;

        //Assigns views data from the current object
        name.setText(inventory.getName());
    }

    //Returns total count of items currently in the array
    @Override
    public int getItemCount() {
        int length = -1;
        if(inventories != null){
            length = inventories.length;
        }
        return length;
    }

    public interface OnInventoryListener{
        void onInventoryClick(int index);
    }
}
