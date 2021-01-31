package com.example.mycontacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    companion object {
        lateinit var dbHandle: DatabaseHandler
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHandle = DatabaseHandler(this, null, null, 1)

        viewContacts()
    }

    // Function to show action bar items...
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu)
        return true
    }

    private fun viewContacts(){
        val contactList = dbHandle.getAllContacts(this)
        val adapter = ContactAdapter(this, contactList)
        val rv : RecyclerView = findViewById(R.id.recyclerview)
        rv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false) as RecyclerView.LayoutManager
        rv.adapter = adapter
    }

    override fun onResume() {
        viewContacts()
        super.onResume()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, AddContactActivity::class.java)

        if(item.getItemId() == R.id.action_add_contact)
            startActivity(intent)

        return super.onOptionsItemSelected(item)
    }
}