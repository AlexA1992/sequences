import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

data class Post(
    var id: Int,
    val userId: Int,
    var date: String = LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("MMM dd yyyy, hh:mm:ss a")).toString(),
    var text: String,
    var deleted: Boolean = false,
)

object postList {
    val allPosts: List<Post> = listOf(
        Post(1, 1, text = "First post"),
        Post(2, 1, text = "Second post", deleted = true),
        Post(3, 2, text = "Third post"),
        Post(4, 3, text = "Forth post"),
        Post(5, 3, text = "Fifth post"),
        Post(6, 3, text = "Sixth post"),
        Post(7, 4, text = "Seventh post", deleted = true),
        Post(8, 4, text = "Eighth post", deleted = true)
    )
}


fun main() {

    //делаем функцию joinToString сами
    var allContent: String = ""
    val antiJoinToString = postList.allPosts.forEach {
        allContent = allContent.plus(it.text).plus(", ")
    }
    println(
        "Наш joinToString:" +
                " ${allContent.substring(0, allContent.length - 2)}"
    )

    //делаем функцию joinToString с take
    var allContent1: String = ""
    val antiJoinToString1 = postList.allPosts.take(3).onEach {
        allContent1 = allContent1.plus(it.text).plus(", ")
    }
    println(
        "Наш joinToString с take:" +
                " ${allContent1.substring(0, allContent1.length - 2)}"
    )

    //делаем функцию reduce сами - схлопываем по полю text
    val res = { text: String, posts: List<Post> ->
        var allTexts = text
        posts.forEach {
            allTexts = allTexts.plus(it.text)
        }
        val thepost = Post(0, 0, text = allTexts)
        thepost
    }("", postList.allPosts)
    println("Самодельный reduce - $res")

    //fold
    val theFold = postList.allPosts.fold("") { acc, post ->
        "${acc} ${post.deleted}"
    }
    println("Функция Fold -$theFold")

    //map
    println("До функции map")
    postList.allPosts.forEach {
        print(it.deleted)
        print(" ")
    }
    println("Меняем с true на false")

    val theMap = postList.allPosts.asSequence().map {
        it.deleted = !it.deleted
        it.deleted
    }
        .take(3)
    println("После функции Map + Take - $theMap")

    //filter.map.reduce
    println("Бессмысленный метод (FIlter.Map.Reduce) по требованию ДЗ - " + postList.allPosts.filter {
        !it.deleted
    }
        .map {
            it.text = it.text + "***"
            it
        }.reduce { acc, post ->
            Post(0, 0, text = acc.text + post.text)
        })

    //takeIf
    val newList: MutableList<Post> = mutableListOf()
    postList.allPosts
        .forEach {
            it.takeIf {
                it.text.contains("First")
            }
                .run {
                    if (this != null) {
                        newList.add(this)
                    }
                }
        }
    println("Функция takeIf - берем только те посты, что иеют слово First в тексте - $newList")

    //sort
    println(
        "Sort и взять 1й элемент - ${
            postList.allPosts.sortedBy {
                it.text
            }
                .get(0)
        }")
}