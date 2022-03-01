package database;

import objects.IDSO;
import objects.Item;

public interface IDBLayer extends IDSO {

    public IDSO Get(int id);
	public IDSO Create(IDSO object);
	public IDSO Delete(int id);
	public IDSO[] GetDB();
	public void ClearDB();
    public boolean VerifyID(int id);
}
