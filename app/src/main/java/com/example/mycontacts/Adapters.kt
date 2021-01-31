package com.example.mycontacts

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.menu.MenuView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_contact.view.*
import kotlinx.android.synthetic.main.layout_update_contact.view.*

class ContactAdapter(mCtx: Context, val contacts: ArrayList<Contact>) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    val mCtx = mCtx
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val txtContactName = itemView.txtContactName
        val txtContactNumber = itemView.txtContactNumber
        val txtContactEmail = itemView.txtContactEmail
        val txtContactOrg = itemView.txtContactOrg
        val editButton = itemView.editButton
        val deleteButton = itemView.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.layout_contact, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact: Contact = contacts[position]

        if(contact.getFav() == 1)
            holder.itemView.setBackgroundColor(Color.parseColor("#E3E5B3"))

        // Fill the boxes with class' values...
        holder.txtContactName.text = contact.getName()
        holder.txtContactNumber.text = contact.getNumber()
        holder.txtContactEmail.text = contact.getEmail()
        holder.txtContactOrg.text = contact.getOrganization()

        //If DELETE button pressed...
        holder.deleteButton.setOnClickListener{
            val contactName = contact.getName() // Hold contact name in a variable since we will use it in message...

            var alertDialog = AlertDialog.Builder(mCtx)
                .setTitle("Attention!") // Set AlertDialog main title...
                .setMessage("Are you sure to delete: $contactName ?") // Show message for confirmation of deletion...
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                    if (MainActivity.dbHandle.deleteContact(contact.getId())){
                        contacts.removeAt(position) // Remove contact...
                        notifyItemRemoved(position) // Show notification after item removed...
                        notifyItemRangeChanged(position, contacts.size) // Update list item count...
                        Toast.makeText(mCtx, "$contactName deleted...", Toast.LENGTH_SHORT).show() // Show toast notification if contact deleted successfully...
                    }
                    else{
                        Toast.makeText(mCtx, "Error while deleting contact!", Toast.LENGTH_LONG).show() // Show toast notification if contact can not be deleted...
                    }
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
                })
                .setIcon(R.drawable.warning_icon)
                .show()
        }

        // If EDIT button pressed...
        holder.editButton.setOnClickListener(){
            val inflater = LayoutInflater.from(mCtx)
            val view = inflater.inflate(R.layout.layout_update_contact, null)
            val txtContactName: TextView = view.findViewById(R.id.editTextPersonName)
            val txtContactNumber: TextView = view.findViewById(R.id.editTextPersonNumber)
            val txtContactEmail: TextView = view.findViewById(R.id.editTextPersonEmail)
            val txtContactOrg: TextView = view.findViewById(R.id.editTextPersonOrg)
            val chkBox: CheckBox = view.findViewById(R.id.chkBoxEditFav)

            txtContactName.text = contact.getName()
            txtContactNumber.text = contact.getNumber()
            txtContactEmail.text = contact.getEmail()
            txtContactOrg.text = contact.getOrganization()
            if(contact.getFav() == 1)
                chkBox.isChecked = true
            else
                chkBox.isChecked = false

            val builder = AlertDialog.Builder(mCtx)
                .setTitle("Update Contact Info")
                .setView(view)
                .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, which ->
                    val isUpdate = MainActivity.dbHandle.updateContact(
                        view.editTextPersonName.text.toString(),
                            view.editTextPersonName.text.toString(),
                            view.editTextPersonNumber.text.toString(),
                            view.editTextPersonEmail.text.toString(),
                            view.editTextPersonOrg.text.toString(),
                            view.chkBoxEditFav.isChecked())
                    if(isUpdate){
                        contacts[position].setName(view.editTextPersonName.text.toString())
                        contacts[position].setNumber(view.editTextPersonNumber.text.toString())
                        contacts[position].setEmail(view.editTextPersonEmail.text.toString())
                        contacts[position].setOrganization(view.editTextPersonOrg.text.toString())
                        contacts[position].setFav(view.chkBoxEditFav.isChecked())
                        notifyDataSetChanged()
                        Toast.makeText(mCtx, "Edited Successfully", Toast.LENGTH_SHORT).show()
                    }
                    else
                        Toast.makeText(mCtx, "Error while editing!", Toast.LENGTH_LONG).show()
                })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->  })
            val alert = builder.create()
            alert.show()
        }
    }
}