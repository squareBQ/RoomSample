package com.mt.roomsample.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mt.roomsample.R

class NewWordActivity : AppCompatActivity() {

	private lateinit var editWordView: EditText

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_new_word)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		editWordView = findViewById<EditText>(R.id.edit_word)
		val button = findViewById<Button>(R.id.button_save)
		button.setOnClickListener {
			val replyIntent = Intent()
			if (TextUtils.isEmpty(editWordView.text)) {
				setResult(RESULT_CANCELED, replyIntent)
			} else {
				val word = editWordView.text.toString()
				replyIntent.putExtra(EXTRA_REPLY, word)
				setResult(RESULT_OK, replyIntent)
			}
			finish()
		}
	}

	companion object {
		const val EXTRA_REPLY = "com.mt.roomsample.REPLY"
	}
}