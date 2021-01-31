package com.example.mycontacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add_contact.*
import kotlinx.android.synthetic.main.layout_contact.*

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        val actionbar = supportActionBar
        actionbar!!.title = "Add New Contact" // Set actionbar title of AddContactActivity
        actionbar.setDisplayHomeAsUpEnabled(true) // Set "Go Back" button as true for AddContactActivity

        // If Add Contact button pressed...
        btnAddContact.setOnClickListener{
            // Statement for handling if contact name plain text is empty...
            if(txtAddContactName.text.isEmpty()){
                Toast.makeText(this, "Enter Contact Name!", Toast.LENGTH_SHORT).show()
                txtAddContactName.requestFocus()
            }
            else{
                val contact = Contact()
                contact.setName(txtAddContactName.text.toString())
                contact.setNumber(txtAddContactNumber.text.toString())
                contact.setEmail(txtAddContactEmail.text.toString())
                contact.setOrganization(txtAddContactOrg.text.toString())

                if(chkBoxContactFav.isChecked())
                    contact.setFav(1)
                else
                    contact.setFav(0)
                MainActivity.dbHandle.addContact(this, contact) // Add data to the database...
                finish() // End the AddContactActivity and return to MainActivity...
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    // Function to clear edits if activity abandoned...
    private fun clearEdits(){
        txtAddContactName.text.clear()
        txtAddContactNumber.text.clear()
        txtAddContactEmail.text.clear()
        txtAddContactOrg.text.clear()
        // TODO: add favourite chkbox function to clear
    }
}