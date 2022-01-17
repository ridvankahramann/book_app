data class Model(
    val books: List<Book>,
    val members: List<Member>,
    val writers: List<Writers>
)

data class Book(
    val holders: List<Holder>,
    val name: String,
    val page: Int,
    val piece: Int,
    val writer: Writer
)

data class Member(
    val memberId: String,
    val name: String
)

data class Writers(
    val writerId: Int,
    val writerName: String
)

data class Holder(
    val dueData: String,
    val memberId: Int
)

data class Writer(
    val writerId: Int,
    val writerName: String
)