package com.diayan.kaal.lib

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import com.diayan.kaal.R

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class KaalProgress @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null
) :
    RelativeLayout(context, attrs) {
    // Setup views
    fun init(title: String?) {
        val progressTextView = findViewById<TextView>(R.id.progress_textView)
        progressTextView.text = title
    }

    init {
        //setOrientation(LinearLayout.VERTICAL);
        translationZ = 10f
        visibility = GONE
        setBackgroundColor(resources.getColor(R.color.transparent_progress))
        LayoutInflater.from(context).inflate(R.layout.progress, this, true)
        val title: String
        val a = context.theme.obtainStyledAttributes(attrs, R.styleable.KaalProgress, 0, 0)
        try {
            title = a.getString(R.styleable.KaalProgress_progressTitle)!!
        } finally {
            a.recycle()
        }
        init(title)
    }
}
