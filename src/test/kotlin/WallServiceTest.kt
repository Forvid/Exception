package main

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WallServiceTest {

    @Test
    fun testAdd() {
        WallService.clear()
        val post = Post(id = 0, ownerId = 100, fromId = 200, createdBy = 1, date = 1663157200, text = "Тестовый пост", friendsOnly = false)
        val addedPost = WallService.add(post)

        assertTrue(addedPost.id > 0)
        assertEquals(post.text, addedPost.text)
    }

    @Test
    fun testUpdateSuccess() {
        WallService.clear()
        val post = Post(id = 0, ownerId = 100, fromId = 200, createdBy = 1, date = 1663157200, text = "Тестовый пост", friendsOnly = false)
        val addedPost = WallService.add(post)

        val updatedPost = addedPost.copy(text = "Обновленный текст")
        val result = WallService.update(updatedPost)

        assertTrue(result)
        assertEquals("Обновленный текст", updatedPost.text)
    }

    @Test
    fun testCreateComment() {
        WallService.clear()
        val post = Post(id = 0, ownerId = 100, fromId = 200, createdBy = 1, date = 1663157200, text = "Тестовый пост", friendsOnly = false)
        val addedPost = WallService.add(post)

        val comment = Comment(id = 0, postId = addedPost.id, fromId = 200, date = 1663157200, text = "Отличный пост!")
        val addedComment = WallService.createComment(addedPost.id, comment)

        assertTrue(addedComment.id > 0)
        assertEquals(comment.text, addedComment.text)
    }

    @Test
    fun testCreateCommentForNonExistentPost() {
        WallService.clear()
        assertFailsWith<PostNotFoundException> {
            WallService.createComment(999, Comment(id = 0, postId = 999, fromId = 200, date = 1663157200, text = "Не существующий пост"))
        }
    }
}
