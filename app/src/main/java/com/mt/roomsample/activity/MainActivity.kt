package com.mt.roomsample.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat.enableEdgeToEdge
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.mt.roomsample.R
import com.mt.roomsample.WordApplication
import com.mt.roomsample.adapter.WordListAdapter
import com.mt.roomsample.model.Word
import com.mt.roomsample.viewmodel.WordViewModel
import com.mt.roomsample.viewmodel.WordViewModelFactory

class MainActivity : AppCompatActivity() {

	private var adapter: WordListAdapter = WordListAdapter()

	private val wordViewModel: WordViewModel by viewModels {
		WordViewModelFactory((application as WordApplication).repository)
	}

	private val activityResultLauncher =
		registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object :
			ActivityResultCallback<ActivityResult> {
			override fun onActivityResult(result: ActivityResult) {
				activityResult(result)
			}
		})

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_main)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		initRecycler()

		wordViewModel.allWords.observe(this) { words ->
			words?.let { adapter.submitList(it) }
		}

		val fab = findViewById<FloatingActionButton>(R.id.fab)
		fab.setOnClickListener {
			val intent = Intent(this@MainActivity, NewWordActivity::class.java)
			activityResultLauncher.launch(intent)
		}
	}

	private fun initRecycler() {
		val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
		recyclerView.layoutManager = LinearLayoutManager(this)
		recyclerView.adapter = adapter
	}

	private fun activityResult(result: ActivityResult) {
		if (result.resultCode == RESULT_OK) {
			result.data?.getStringExtra(NewWordActivity.EXTRA_REPLY)?.let {
				val word = Word(it)
				wordViewModel.insert(word)
			}
		} else {
			Toast.makeText(
				applicationContext,
				R.string.empty_not_saved,
				Toast.LENGTH_LONG
			).show()
		}
	}
}