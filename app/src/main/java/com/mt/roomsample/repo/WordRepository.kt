package com.mt.roomsample.repo

import androidx.annotation.WorkerThread
import com.mt.roomsample.dao.WordDao
import com.mt.roomsample.model.Word
import kotlinx.coroutines.flow.Flow

/**
 * time : 8/19/25
 *
 * 什么是存储库？
 * 存储库类会将多个数据源的访问权限抽象化。存储库并非架构组件库的一部分，但它是推荐为代码分离和架构采用的最佳做法。
 * 存储库类会提供一个简洁的 API，用于获取对应用其余部分的数据访问权限。
 *
 * 为什么使用存储库？
 * 存储库可管理查询，且允许您使用多个后端。
 * 在最常见的示例中，存储库可实现对以下任务做出决定时所需的逻辑：是否从网络中提取数据；是否使用缓存在本地数据库中的结果。
 *
 * - DAO 会被传递到存储库构造函数中，而非整个数据库中。DAO 包含数据库的所有读取/写入方法，因此它只需要访问 DAO，无需向存储库公开整个数据库。
 * - 字词列表具有公开属性。它通过从 Room 获取 Flow 字词列表来进行初始化；您之所有能够实现该操作，是因为您在“观察数据库变化‘步骤中定义 `getAlphabetizedWordsFlow` 方法以返回 Flow 的方式。
 *   Room 将在单独的现成上执行所有查询。
 * - suspend 修饰符会告知编译器需要从协程或其他挂起函数进行调用。
 * - Room 在主线程之外执行挂起查询。
 *
 * 存储库的用途是在不同的数据源之间进行协调。在这个简单示例中，数据源只有一个，因此该存储库并未执行多少操作。
 */
class WordRepository(private val wordDao: WordDao) {

	val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWordsFlow()

	@WorkerThread
	suspend fun insert(word: Word) {
		wordDao.insert(word)
	}
}