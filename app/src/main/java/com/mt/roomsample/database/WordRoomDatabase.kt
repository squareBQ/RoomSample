package com.mt.roomsample.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mt.roomsample.dao.WordDao
import com.mt.roomsample.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * time : 8/19/25
 *
 * 什么是 Room 数据库？
 * - Room 是 SQLite 数据库之上的一个数据库层。
 * - Room 负责您平常使用 SQLiteOpenHelper 所处理的单调乏味的任务。
 * - Room 使用 DAO 向其数据库发出查询请求。
 * - 为避免界面性能不佳，默认情况下，Room 不允许在主线程上发出查询请求。当 Room 查询返回 Flow 时，这些查询会在后台线程上自动异步运行。
 * - Room 提供 SQLite 语句的编译时检查。
 *
 * 实现 Room 数据库
 * 您的 Room 数据库类必须是抽象且必须扩展 RoomDatabase，整个应用通常只需要一个 Room 数据库实例。
 */
@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase : RoomDatabase() {

	abstract fun wordDao(): WordDao

	private class WordDatabaseCallback(private val scope: CoroutineScope) : Callback() {
		override fun onCreate(db: SupportSQLiteDatabase) {
			super.onCreate(db)
			INSTANCE?.let { database ->
				scope.launch {
					populateDatabase(database.wordDao())
				}
			}
		}

		suspend fun populateDatabase(wordDao: WordDao) {
			// Delete all content here.
			wordDao.deleteAll()

			// Add sample words.
			var word = Word("Hello")
			wordDao.insert(word)
			word = Word("World!")
			wordDao.insert(word)

			// TODO: Add your own words!
			word = Word("TODO!")
			wordDao.insert(word)
		}
	}

	companion object {

		@Volatile
		private var INSTANCE: WordRoomDatabase? = null

		fun getDatabase(context: Context, scope: CoroutineScope): WordRoomDatabase {
			return INSTANCE ?: synchronized(this) {
				val instance = Room.databaseBuilder(
					context.applicationContext,
					WordRoomDatabase::class.java,
					"word_database" // 命名数据库对象为 word_database
				)
					.addCallback(WordDatabaseCallback(scope))
					.build()
				INSTANCE = instance
				instance
			}
		}
	}
}