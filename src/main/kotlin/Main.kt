package main

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = false,
    val canOpen: Boolean = true
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Views(
    val count: Int = 0
)

data class Post(
    val id: Int,
    val ownerId: Int,
    val fromId: Int,
    val createdBy: Int,
    val date: Int,
    val text: String? = null,
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val friendsOnly: Boolean,
    val comments: Comments? = null,
    val likes: Likes? = null,
    val reposts: Reposts? = null,
    val views: Views? = null
)

data class Comment(
    val id: Int,
    val postId: Int,
    val fromId: Int,
    val date: Int,
    val text: String
)

class PostNotFoundException(message: String) : RuntimeException(message)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextPostId = 1
    private var nextCommentId = 1

    fun add(post: Post): Post {
        val postWithId = post.copy(id = nextPostId++)
        posts += postWithId
        return postWithId
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = post.copy(ownerId = existingPost.ownerId, date = existingPost.date)
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        val post = posts.find { it.id == postId }
            ?: throw PostNotFoundException("Пост с ID $postId не найден")

        val commentWithId = comment.copy(id = nextCommentId++)
        comments += commentWithId
        return commentWithId
    }

    fun clear() {
        posts = emptyArray()
        comments = emptyArray()
        nextPostId = 1
        nextCommentId = 1
    }
}

fun main() {
    WallService.clear()

    val post1 = Post(id = 0, ownerId = 100, fromId = 200, createdBy = 1, date = 1663157200, text = "Первый пост", friendsOnly = false)
    val addedPost1 = WallService.add(post1)

    println("Добавленный пост:")
    println(addedPost1)

    try {
        val comment = Comment(id = 0, postId = addedPost1.id, fromId = 200, date = 1663157200, text = "Отличный пост!")
        val addedComment = WallService.createComment(addedPost1.id, comment)
        println("Добавленный комментарий:")
        println(addedComment)
    } catch (e: PostNotFoundException) {
        println(e.message)
    }
}
