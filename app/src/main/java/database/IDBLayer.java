package database;

import objects.IDSO;
import objects.Item;

public interface IDBLayer {

    public IDSO get(int id);
	public int create(IDSO object);
	public IDSO delete(int id);
	public IDSO[] getDB();
	public void clearDB();
<<<<<<< HEAD
	public IDSO add(int id, int quantity);
	public IDSO remove(int id, int quantity);
=======
    public boolean verifyID(int id);
	public IDSO addItem(int id, int qty);
	public IDSO removeItem(int id, int qty);
>>>>>>> 11a6b7c7ba5cadfb111dcaf3c6aa8d4fbaa214b2
}
