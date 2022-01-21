data class Model(
    val books: List<Book>,
    val members: List<Member>,
    val writers: List<Writer>
)

data class Book(
    var holders: List<Holder>,
    var name: String,
    var page: Int,
    var piece: Int,
    var writer: Writer?
)

data class Member(
    val memberId: Int,
    val name: String
)

data class Holder(
    val dueData: String,
    val memberId: Int
)

data class Writer(
    val writerId: Int,
    val writerName: String
)