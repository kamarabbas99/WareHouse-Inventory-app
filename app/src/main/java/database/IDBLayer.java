package database;

import objects.IDSO;
import objects.Item;

public interface IDBLayer extends IDSO {

    public IDSO get(int id);
	public IDSO create(IDSO object);
	public IDSO delete(int id);
	public IDSO[] getDB();
	public void clearDB();
    public boolean verifyID(int id);
}
