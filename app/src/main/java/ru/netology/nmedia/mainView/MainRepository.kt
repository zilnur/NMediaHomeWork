package ru.netology.nmedia.mainViewimport android.content.Contextimport androidx.lifecycle.LiveDataimport androidx.lifecycle.MutableLiveDataimport com.google.gson.Gsonimport com.google.gson.reflect.TypeTokenimport java.io.Fileinterface MainRepository {    fun get(): LiveData<List<Post>>    fun likeBy(id: Int)    fun shareBy(id: Int)    fun removeBy(id: Int)    fun save(post: Post)}class MainRepositoryImpl(    private val context: Context): MainRepository {    private var nextID = 1    private val gson = Gson()    private var posts = emptyList<Post>()        set(value) {            field = value            sync()        }    private val data = MutableLiveData(posts)    init {        val file = context.filesDir.resolve(FILENAME)        if (file.exists()) {            context.openFileInput(FILENAME).bufferedReader().use {                posts = gson.fromJson(it, type)                nextID = (posts.maxOfOrNull { it.id } ?: 0) + 1                data.value = posts            }        }    }    override fun get() = data    override fun likeBy(id: Int) {        posts = posts.map {            if (it.id != id) it else it.copy(isLiked = !it.isLiked, likes = if (it.isLiked) it.likes - 1 else it.likes + 1)        }        data.value = posts    }    override fun shareBy(id: Int) {        posts = posts.map {            if (it.id != id) it else it.copy(shares = it.shares + 1)        }        data.value = posts    }    override fun removeBy(id: Int) {        posts = posts.filter { it.id != id }        data.value = posts    }    override fun save(post: Post) {        if (post.id == 0) {            posts = listOf(                post.copy(                    id = nextID++,                    autor = "Нетология. Университет интернет-профессий",                    date = "21 мая в 18:36"                )            ) + posts            data.value = posts            return        }        posts = posts.map {            if (it.id != post.id) it else it.copy(text = post.text)        }        data.value = posts    }    private fun sync() {        context.openFileOutput(FILENAME, Context.MODE_PRIVATE).bufferedWriter().use {            it.write(gson.toJson(posts))        }    }    companion object {        private const val FILENAME = "posts.json"        private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type    }}