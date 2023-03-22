package com.emedstore.video_with_paint.generalHelper

import android.content.Context
import android.content.Intent
import com.emedstore.video_with_paint.R
import com.emedstore.video_with_paint.VideoWithPaintActivity

fun Context.navigateToPlayerWithPaintActivity(
    videoUrl: String?, showPaintOption: Boolean? = false
) {
    startActivity(
        Intent(
            this,
            VideoWithPaintActivity::class.java
        ).putExtra(getString(R.string.bundle_key_pass_video_url), videoUrl)
            .putExtra(getString(R.string.bundle_key_pass_show_paint_options), showPaintOption)
    )
}