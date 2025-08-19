package com.mt.roomsample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mt.roomsample.model.Word
import com.mt.roomsample.repo.WordRepository
import kotlinx.coroutines.launch

/**
 * time : 8/19/25
 *
 * - 什么是 ViewModel？
 * ViewModel 的作用是向界面提供数据，不受配置变化的影响。ViewModel 充当存储库和界面之间的通信中心。
 * 您还可以使用 ViewModel 在 fragment 之间共享数据。ViewModel 是 Lifecycle 库的一部分。
 *
 * - 为什么使用 ViewModel？
 * ViewModel 以一种可以感知生命周期的方式保存应用的界面数据，不受配置变化的影响。
 * 它会将应用的界面数据与 Activity 和 Fragment 类区分开，让您更好地遵循单一责任原则：
 * activity 和 fragment 负责将数据绘制到屏幕上，ViewModel 则负责保存并处理界面所需的所有数据。
 *
 * - LiveData 和 ViewModel
 * LiveData 是一种可观察的数据存储器，每当数据发生变化时，您都会收到通知。与 Flow 不同，LiveData 具有声明周期感知能力，
 * 即遵循其他应用组件（如 activity 或 fragment）的生命周期。LiveData 会根据负责监听变化的组件的生命周期自动停止或恢复观察。
 * 因此，LiveData 适用于界面使用或显示的可变数据。
 * ViewModel 会将存储库中的数据从 Flow 转换为 LiveData，并将字词列表作为 LiveData 传递给界面。这样可以确保每次数据库中的数据发生变化时，界面都会自动更新。
 *
 * - viewModelScope
 * 在 kotlin 中，所有协程都在 CoroutineScope 中运行。范围用于控制协程在整个作业过程中的生命周期。如果取消某一范围的作业，该范围内气动的所有协程也将取消。
 */
class WordViewModel(private val repository: WordRepository) : ViewModel() {

	// 将 Flow<List<Word>> 转换为 LiveData<List<Word>>，当数据库更改时，allWords 将更新。
	val allWords: LiveData<List<Word>> = repository.allWords.asLiveData()

	// 启动一个新的协程以在非阻塞方式插入数据，在后台线程上运行挂起函数，因此它不会阻塞主线程。
	fun insert(word: Word) = viewModelScope.launch {
		repository.insert(word)
	}
}

class WordViewModelFactory(private val repository: WordRepository) : ViewModelProvider.Factory {

	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return WordViewModel(repository) as T
		}
		return super.create(modelClass)
	}
}