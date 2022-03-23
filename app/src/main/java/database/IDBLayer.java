package database;

import objects.IDSO;

public interface IDBLayer {

    public IDSO get(int id);
	public int create(IDSO object);
	public void delete(int id);
	public IDSO[] getDB();
	public void clearDB();
	public IDSO add(int id, int quantity);
	public IDSO remove(int id, int quantity);
}
