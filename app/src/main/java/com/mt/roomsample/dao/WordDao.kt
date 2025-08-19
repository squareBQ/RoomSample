package com.mt.roomsample.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mt.roomsample.model.Word
import kotlinx.coroutines.flow.Flow

/**
 * time : 8/19/25
 *
 * - WordDao 是一个接口，DAO必须是接口或抽象类
 * - @Dao 注解将其标识为 Room 的 DAO 类
 * - suspend fun insert(word: Word) 声明挂起函数以插入一个字词
 * - @Insert 注解是一种特殊的 DAO 方法注解，使用 DAO 方法注解时，您无需提供任何 SQL 代码。
 * - onConflict = OnConflictStrategy.IGNORE 所选 onConflict 策略将忽略与列表中的现有字词完全相同的新字词
 * - suspend fun deleteAll()：声明挂起函数以删除所有字词
 * - @Query("DELETE FROM word_table")：@Query 要求您将 SQL 查询作为字符串参数提供给注解，以执行复杂的读取查询及其他操作。
 * - @Query 注解声明一个返回字词列表的查询方法
 * - @Query("SELECT * FROM word_table ORDER BY word ASC")：可返回按升序排序的字词列表的查询。
 */
@Dao
interface WordDao {

	@Query("SELECT * from word_table ORDER BY word ASC")
	fun getAlphabetizedWords(): List<Word>


	@Query("SELECT * from word_table ORDER BY word ASC")
	fun getAlphabetizedWordsFlow(): Flow<List<Word>>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	suspend fun insert(word: Word)

	@Query("DELETE FROM word_table")
	suspend fun deleteAll()
}


/**
 * Flow 是值的异步操作
 * Flow 通过异步操作（列如网络请求、数据库调用或其他异步代码），一次生成一个值（而不是一次生成所有值）。
 * 它的 API 支持协程，因此也可以使用协程来完善 Flow 。
 */