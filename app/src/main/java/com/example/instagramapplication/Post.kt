package com.example.instagramapplication

import android.util.Log
import com.parse.ParseClassName
import com.parse.ParseFile
import com.parse.ParseObject
import com.parse.ParseUser
import java.io.File
import java.util.*

//decs, user, file
@ParseClassName("Post")
class Post : ParseObject() {

    fun getDescription():String?{
        return getString(KEY_DESCRIPTION)
    }
    fun setDescription(description: String){
        put(KEY_DESCRIPTION, description)
    }

    fun getImage(): ParseFile?{
        return getParseFile(KEY_IMAGE)
    }
    fun setImage(parseFile: ParseFile){
        put(KEY_IMAGE, parseFile)
    }

    fun getUser():ParseUser?{
        return getParseUser(KEY_USER)
    }
    fun setUser(user: ParseUser){
        put(KEY_USER, user)
    }

    fun getDate(): Date? {
        Log.i("ROB", "Date is ${getDate(KEY_CREATEDAT)}")
        return getDate(KEY_CREATEDAT)
    }

    companion object{

        const val KEY_DESCRIPTION = "description"
        const val KEY_USER = "user"
        const val KEY_IMAGE = "image"
        const val KEY_CREATEDAT = "createdAt"

    }
}