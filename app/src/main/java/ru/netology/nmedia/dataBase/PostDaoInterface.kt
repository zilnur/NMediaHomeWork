package ru.netology.nmedia.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDaoInterface {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(Post: PostEntity)

    @Query("""
        UPDATE postentity SET
        text = :text
        WHERE id = :id;
    """)
    fun updateBy(id: Int, text: String)

    @Query("""
        UPDATE PostEntity SET
            likes = likes + CASE WHEN isLiked THEN -1 ELSE 1 END,
            isLiked = CASE WHEN isLiked THEN 0 ELSE 1 END
        WHERE id = :id;
    """)
    fun likeBy(id: Int)

    @Query("""
        UPDATE PostEntity SET
        shares = shares + 1
        WHERE id = :id;
    """)
    fun shareBy(id: Int)

    @Query("DELETE FROM PostEntity WHERE id = :id;")
    fun removeBy(id: Int)
}