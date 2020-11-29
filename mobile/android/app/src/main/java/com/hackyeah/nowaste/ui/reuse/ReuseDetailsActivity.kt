package com.hackyeah.nowaste.ui.reuse

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.hackyeah.nowaste.data.remote.Reuse
import com.hackyeah.nowaste.databinding.ReuseDetailsActivityBinding
import com.hackyeah.nowaste.utils.EXTRA_REUSE
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReuseDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ReuseDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reuse = intent.getSerializableExtra(EXTRA_REUSE) as Reuse
        binding.title.setText(reuse.title)
        binding.date.setText(
            String.format(
                "%d-%d-%d %d:%d:%d",
                reuse.date[0],
                reuse.date[1],
                reuse.date[2],
                reuse.date[3],
                reuse.date[4],
                reuse.date[5]
            )
        )
        binding.description.setText(reuse.description)
        binding.author.setText(reuse.author)

        if (reuse.photosUrl.isNotEmpty()) {
            Glide.with(binding.root)
                .load(reuse.photosUrl[0])
                .into(binding.image1)

            if (reuse.photosUrl.size > 1) {
                Glide.with(binding.root)
                    .load(reuse.photosUrl[1])
                    .into(binding.image2)

                if (reuse.photosUrl.size > 2) {
                    Glide.with(binding.root.context)
                        .load(reuse.photosUrl[2])
                        .into(binding.image3)

                    if (reuse.photosUrl.size > 3) {
                        Glide.with(binding.root)
                            .load(reuse.photosUrl[3])
                            .into(binding.image4)
                    }
                }
            }
        }
    }
}