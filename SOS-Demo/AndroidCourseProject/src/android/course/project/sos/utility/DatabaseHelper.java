package android.course.project.sos.utility;

import android.content.ContentValues;
import android.content.Context;
import android.course.project.sos.domain.Contact;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	static final String dbName="sosSettingDB";
	static final String contactTable="EmergencyContact";
	static final String colID="ContactID";
	static final String colName="ContactName";
	static final String colPhone="Phone";
	static final String colEmail="Email";
	static final String colisSms="isSms";
	static final String colisPhone="isPhone";
	static final String colisEmail="isEmail";


	public DatabaseHelper(Context context) {
		super(context, dbName, null,33);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL("CREATE TABLE "+contactTable+" ("+colID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
				colName+" TEXT, "+
				colPhone+" Integer, "+
				colEmail+" TEXT,  "+
				colisSms+" Integer,  "+
				colisPhone+" Integer,  "+
				colisEmail+" Integer	);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		db.execSQL("DROP TABLE IF EXISTS "+contactTable);
		onCreate(db);
	}

	void AddContact(Contact data)
	{
		SQLiteDatabase db= this.getWritableDatabase();

		ContentValues cv=new ContentValues();

		cv.put(colName, data.getName());
		cv.put(colPhone, data.getPhoneNumber());
		cv.put(colEmail, data.getEmail());
		cv.put(colisEmail, data.isEmail());
		cv.put(colisPhone, data.isPhone());
		cv.put(colisSms, data.isSms());

		db.insert(contactTable, colName, cv);
		db.close();
	}

	int getContactCount()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("Select * from "+contactTable, null);
		int x= cur.getCount();
		cur.close();
		return x;
	}

	Cursor getAllPhoneCallContact()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+contactTable+" where "+colisPhone+" = 1",null);
		return cur;

	}

	Cursor getAllSMSContact()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+contactTable+" where "+colisSms+" = 1",null);
		return cur;

	}

	Cursor getAllEmailContact()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+contactTable+" where "+colisEmail+" = 1",null);
		return cur;

	}

	Cursor getAllConfiguredContact()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		Cursor cur= db.rawQuery("SELECT * FROM "+contactTable,null);
		return cur;

	}


	public void DeleteContact(Contact contact)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		db.delete(contactTable,colID+"=?", new String [] {String.valueOf(contact.getID())});
		db.close();
	}

}
