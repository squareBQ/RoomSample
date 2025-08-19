package com.mt.roomsample

import android.app.Application
import com.mt.roomsample.database.WordRoomDatabase
import com.mt.roomsample.repo.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * time : 8/19/25
 * desc :
 */
class WordApplication : Application() {

	/**
	 * 应用范围
	 */
	val applicationScope = CoroutineScope(SupervisorJob())
	/**
	 * 数据库
	 */
	val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
	/**
	 * 仓库
	 */
	val repository by lazy { WordRepository(database.wordDao()) }
}