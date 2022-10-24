package com.example.instagramapplication.fragments

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.instagramapplication.Post
import com.example.instagramapplication.R
import com.parse.ParseFile
import com.parse.ParseUser
import java.io.File

class ComposeFragment : Fragment() {
    val photoFileName = "photo.jpg"
    var photoFile: File? = null
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    lateinit var ivPreview : ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivPreview = view.findViewById(R.id.imageView)

        //Need to be able to make posts and retrieve posts
        view.findViewById<Button>(R.id.btn_Submit).setOnClickListener{
            val description = view.findViewById<EditText>(R.id.et_Description).text.toString()
            val user = ParseUser.getCurrentUser()
            if (photoFile != null){
                submitPost(description, user, photoFile!!)
            }else{
                //Toast.makeText(this, "Error posting post", Toast.LENGTH_SHORT).show()
            }
        }
        view.findViewById<Button>(R.id.btn_TakePic).setOnClickListener{
            Log.i("ROB","PRESSING BUTTON")
            onLaunchCamera()
        }
    }

    fun submitPost(description: String, user:ParseUser, file: File){
        val post = Post()
        post.setDescription(description)
        post.setUser(user)
        post.setImage(ParseFile(file))
        post.saveInBackground{ exc->
            if (exc != null){
                Log.e("ROB", "Error saving post")
                exc.printStackTrace()
            }else{
                Log.i("ROB", "Saved post suc")
                view?.findViewById<EditText>(R.id.et_Description)?.setText("")
                //findViewById<ImageView>(R.id.imageView).setImageBitmap(null)
            }
        }
    }

    fun onLaunchCamera() {
        // create Intent to take a picture and return control to the calling application
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName)

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(requireContext(), "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.

            // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
            // So as long as the result is not null, it's safe to use the intent.
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                // Start the image capture intent to take photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    // Returns the File for a photo stored on disk given the fileName
    fun getPhotoFileUri(fileName: String): File {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        val mediaStorageDir =
            File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "MainActivity")

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d("MainActivity", "failed to create directory")
        }

        // Return the file target for the photo based on filename
        return File(mediaStorageDir.path + File.separator + fileName)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE){
            if(resultCode == AppCompatActivity.RESULT_OK){
                val takenImage = BitmapFactory.decodeFile(photoFile!!.absolutePath)
                ivPreview.setImageBitmap(takenImage)
            }
        }
    }

}