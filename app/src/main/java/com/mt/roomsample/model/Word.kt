package com.mt.roomsample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * time : 8/19/25
 * desc : Word实体
 *
 * @Entity(tableName = "word_table")  每个 @Entity 类代表一个表。为你的类定义的类名就是表名。如果不显示指定表名，则默认表名就是类名。
 * @PrimaryKey 每个实体都需要主键。通常主键是自增长或非空字段。为简便起见，每个字词都可充当自己的主键。
 * @ColumnInfo(name = "word") 如果您希望该表中列的名称与成员变量的名称不同，可以指定表中列的名称，此处的列名为“word”。
 *
 * @PrimaryKey(autoGenerate = true) 可自动生成唯一的键, 自增长
 */
@Entity(tableName = "word_table")
class Word(
	@PrimaryKey/*(autoGenerate = true) val id: Int,*/
	@ColumnInfo(name = "word") val word: String,
)
