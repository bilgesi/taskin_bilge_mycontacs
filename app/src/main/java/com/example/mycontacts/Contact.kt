package com.example.mycontacts

import kotlin.properties.Delegates

class Contact {
    // Attributes of Contact class...
    private var id: Int = 0
    private lateinit var name: String
    private lateinit var number: String
    private lateinit var email: String
    private lateinit var organization: String
    private var favourite: Boolean = false

    // Methods of Contact class...
    constructor(){}

    constructor(id: Int, name: String, number: String, email: String, organization: String, favourite: Boolean){
        this.id = id
        this.name = name
        this.number = number
        this.email = email
        this.organization = organization
        this.favourite = favourite
    }

    constructor(name: String, number: String, email: String, organization: String, favourite: Boolean){
        this.name = name
        this.number = number
        this.email = email
        this.organization = organization
        this.favourite = favourite
    }


    public fun getId(): Int{
        return this.id
    }

    public fun setId(id: Int){
        this.id = id
    }

    public fun getName(): String{
        return this.name
    }

    public fun setName(name: String){
        this.name = name
    }

    public fun getNumber(): String{
        return this.number
    }

    public fun setNumber(number: String){
        this.number = number
    }

    public fun getEmail(): String{
        return this.email
    }

    public fun setEmail(email: String){
        this.email = email
    }

    public fun getOrganization(): String{
        return this.organization
    }

    public fun setOrganization(org: String){
        this.organization = org
    }

    public fun getFav(): Int{
        if(this.favourite)
            return 1
        else
            return 0
    }

    public fun setFav(fav: Int){
        if(fav == 1)
            this.favourite = true
        else
            this.favourite = false
    }

    public fun setFav(fav: Boolean){
        this.favourite = fav
    }
}