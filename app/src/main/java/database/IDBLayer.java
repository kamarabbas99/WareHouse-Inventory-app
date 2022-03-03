package database;

import objects.IDSO;
import objects.Item;

public interface IDBLayer {

    public IDSO get(int id);
	public void create(IDSO object);
	public IDSO delete(int id);
	public IDSO[] getDB();
	public void clearDB();
    public boolean verifyID(int id);
	public IDSO addItem(int id, int qty);
	public IDSO removeItem(int id, int qty);
}
