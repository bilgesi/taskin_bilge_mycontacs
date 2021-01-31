package com.example.mycontacts

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.mycontacts.Contact
import java.lang.Exception


class DatabaseHandler(context : Context, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, DBNAME, factory, 1) {

    companion object Database{
        const val DBNAME = "Contacts"
        const val TABLENAME = "contacts"
        const val ID = "id"
        const val NAME = "name"
        const val NUMBER = "phonenumber"
        const val EMAIL = "email"
        const val ORGANIZATION = "organization"
        const val FAVOURITE = "favourite"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
                "CREATE TABLE $TABLENAME (" +
                        "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "$NAME TEXT," +
                        "$NUMBER TEXT," +
                        "$EMAIL TEXT," +
                        "$ORGANIZATION TEXT," +
                        "$FAVOURITE TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE $TABLENAME")
        onCreate(db)
    }

    // Function to add a new contact to database...
    fun addContact(mCtx: Context, contact : Contact) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, contact.getName())
        values.put(NUMBER, contact.getNumber())
        values.put(EMAIL, contact.getEmail())
        values.put(ORGANIZATION, contact.getOrganization())
        values.put(FAVOURITE, contact.getFav())
        try{
            db.insert(TABLENAME, null, values)
            Toast.makeText(mCtx, "Contact Added Successfully...", Toast.LENGTH_SHORT).show()
        }
        catch (e: Exception){
            Toast.makeText(mCtx, e.message, Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    //Function to get all data in contacts table...
    fun getAllContacts(mCtx: Context): ArrayList<Contact> {
        val query = "SELECT * FROM $TABLENAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(query, null)
        val contacts = ArrayList<Contact>()

        if(cursor.count == 0)
            Toast.makeText(mCtx, "No Records Found", Toast.LENGTH_SHORT).show()
        else{
            cursor.moveToFirst()
            while(!cursor.isAfterLast()){
                val contact = Contact()
                contact.setId(cursor.getInt(cursor.getColumnIndex(ID)))
                contact.setName(cursor.getString(cursor.getColumnIndex(NAME)))
                contact.setNumber(cursor.getString(cursor.getColumnIndex(NUMBER)))
                contact.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)))
                contact.setOrganization(cursor.getString(cursor.getColumnIndex(ORGANIZATION)))
                contact.setFav(cursor.getInt(cursor.getColumnIndex(FAVOURITE)))
                contacts.add(contact)
                cursor.moveToNext()
            }
            Toast.makeText(mCtx, "${cursor.count.toString()} Records Found", Toast.LENGTH_SHORT).show()
        }
        cursor.close()
        db.close()
        return contacts
    }

    // Function to update contact in database...
    fun updateContact(id: String,
                      name: String?,
                      number: String?,
                      email: String?,
                      org: String?,
                      fav: Boolean) : Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        var flag: Boolean = false
        var favouriteflag = 0
        values.put(NAME, name)
        values.put(NUMBER, number)
        values.put(EMAIL, email)
        values.put(ORGANIZATION, org)

        if(fav)
            favouriteflag = 1
        else
            favouriteflag = 0
        values.put(FAVOURITE, favouriteflag)

        try {
            db.update(TABLENAME, values, "$ID = ?", arrayOf(id))
            flag = true
        }
        catch(e: Exception){
            Log.e(ContentValues.TAG, "Error deleting contact from database...")
            flag =false
        }
        return flag
    }

    // Function to delete contact from database...
    fun deleteContact(id: Int) : Boolean{
        val query = "DELETE FROM $TABLENAME where $ID = $id"
        val db = this.writableDatabase
        var flag : Boolean = false
        try{
            val cursor = db.execSQL(query)
            flag = true
        }
        catch (e: Exception){
            Log.e(ContentValues.TAG, "Error occured when deleting from database")
        }
        db.close()
        return flag
    }
}