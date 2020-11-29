package com.hackyeah.nowaste.ui.reuse

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.hackyeah.nowaste.data.model.Product
import com.hackyeah.nowaste.data.remote.NewReuse
import com.hackyeah.nowaste.databinding.NewReuseActivityBinding
import com.hackyeah.nowaste.utils.EXTRA_PRODUCT
import com.hackyeah.nowaste.utils.Resource
import com.hackyeah.nowaste.utils.Session
import com.hackyeah.nowaste.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

@AndroidEntryPoint
class NewReuseActivity : AppCompatActivity() {
    val GET_IMAGE_CONTENT_REQUEST_CODE = 10

    private val viewModel: NewReuseViewModel by viewModels()

    private lateinit var binding: NewReuseActivityBinding
    private lateinit var product: Product

    private val imageUriList: ArrayList<Uri> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = NewReuseActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getSerializableExtra(EXTRA_PRODUCT) as Product

        binding.picturePreview1.setOnClickListener { onAddPicture() }
        binding.picturePreview2.setOnClickListener { onAddPicture() }
        binding.picturePreview3.setOnClickListener { onAddPicture() }
        binding.picturePreview4.setOnClickListener { onAddPicture() }

        binding.addButton.setOnClickListener { onAddReview() }

        setupObservers()
    }

    override fun onActivityResult(reqCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK &&
            reqCode == GET_IMAGE_CONTENT_REQUEST_CODE
        ) {
            try {
                val imageUri: Uri? = data?.data
                imageUri?.let {
                    saveImageUri(it)
                }
            } catch (e: Exception) {
                showToast(e.message ?: "Ups..\nSome error occurred!")
            }
        }
    }

    private fun saveImageUri(it: Uri) {
        val imageStream = contentResolver.openInputStream(it)
        val imageBitmap = BitmapFactory.decodeStream(imageStream)
        when (imageUriList.size) {
            0 -> {
                binding.picturePreview1.imageTintList = null
                binding.picturePreview1.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.picturePreview1.setImageBitmap(imageBitmap)
                binding.picturePreview2.visibility = View.VISIBLE
            }
            1 -> {
                binding.picturePreview2.imageTintList = null
                binding.picturePreview2.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.picturePreview2.setImageBitmap(imageBitmap)
                binding.picturePreview3.visibility = View.VISIBLE
            }
            2 -> {
                binding.picturePreview3.imageTintList = null
                binding.picturePreview3.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.picturePreview3.setImageBitmap(imageBitmap)
                binding.picturePreview4.visibility = View.VISIBLE
            }
            3 -> {
                binding.picturePreview4.imageTintList = null
                binding.picturePreview4.scaleType = ImageView.ScaleType.CENTER_CROP
                binding.picturePreview4.setImageBitmap(imageBitmap)
            }
        }
        this.imageUriList.add(it)
    }

    private fun onAddPicture() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, GET_IMAGE_CONTENT_REQUEST_CODE)
    }

    private fun onAddReview() {
        val title = binding.titleInput.text.toString()
        if (title.isBlank()) {
            showToast("Fill the title input")
            return
        }

        val description = binding.descriptionInput.text.toString()
        if (description.isBlank()) {
            showToast("Fill the description input")
            return
        }

        val model = NewReuse(
            Session.author,
            title,
            description,
            product.id,
            listOf(product.category.id),
            product.tags.map { tag -> tag.id })

        val files = arrayListOf<MultipartBody.Part>()
        imageUriList.forEach { uri ->
            try {
                val stream = contentResolver.openInputStream(uri)
                val bytes = stream?.readBytes()
                bytes?.let { data ->
                    val file: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "photos",
                        "photo.jpg",
                        RequestBody.create(
                            MediaType.parse("image/*"),
                            data,
                            0,
                            data.size
                        )
                    )
                    files.add(file)
                }
            } catch (ex: java.lang.Exception) {
            }
        }

        viewModel.addNewReuse(model, files)
    }

    private fun setupObservers() {
        viewModel.response.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    binding.progressView.visibility = View.GONE
                    finish()
                }
                Resource.Status.ERROR -> {
                    it.message?.let { message -> showToast(message) }
                    binding.progressView.visibility = View.GONE
                }
                Resource.Status.LOADING -> {
                    binding.progressView.visibility = View.VISIBLE
                }
            }
        })
    }
}