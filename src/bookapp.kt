import com.google.gson.Gson
import java.io.File
import java.io.FileWriter
import java.util.*

//val outputJson: String = Gson().toJson(myClass)
fun main() {
    var membersName:String= "Ridvan Kahraman"
    var memberNameId:Int = 1
    val gson = Gson()
    val url = "C:\\Users\\Ridvan\\IdeaProjects\\book_app\\src\\app.json"
    var lines = File(url).readText(Charsets.UTF_8)
    val model: Model = Gson().fromJson(lines, Model::class.java)
    print(model)
    print("\n 1.kitap ekle\n 2.kitap teslim et\n 3.kitap teslim al\n 4.kitap ara \n 5.kitapları listele\n")
    var inputnumber:Int = readLine()!!.toInt()
    if(inputnumber == 1){
        print("Kitap İsmi\n")
        val Name = readLine()!!.toString()
        print("Kitap Sayfa\n")
        val page = readLine()!!.toInt()
        print("Kitap Fiyatı\n")
        val piece = readLine()!!.toInt()
        print("Yazar İsmi\n")
        val writerName = readLine()!!.toString()
        val dueData: Date = Calendar.getInstance().time

        model.writers.forEach { writer -> if(writer.writerName == writerName){
            val writerId = writer.writerId
            val model: Model = Gson().fromJson(array_json(membersName, memberNameId, Name, page, piece, writerName, writerId, dueData), Model::class.java)
            File(url).appendText(Gson().toJson(model))
        }else{
            val writerId = Random().nextInt(0, 100)
            val model: Model = Gson().fromJson(array_json(membersName, memberNameId, Name, page, piece, writerName, writerId, dueData), Model::class.java)
            File(url).appendText(Gson().toJson(model))
        } }
    }
}

fun array_json(membersName:String,memberNameId:Int,Name:String,page:Int,piece:Int,writerName:String,writerId:Int,dueData:Date): String {
    return "{  \"members\": [ { \"name\": \"$membersName\", \"memberId\":  \"$memberNameId\" } ],\n" +
            "  \"writers\": [ { \"writerName\": \"$writerName\", \"writerId\": $writerId } ],\n" +
            "  \"books\": [\n" +
            "    {\n" +
            "      \"name\": \"$Name\",\n" +
            "      \"writer\": { \"writerName\": \"$writerName\", \"$writerId\": 12 },\n" +
            "      \"page\": $page,\n" +
            "      \"holders\": [\n" +
            "        {\n" +
            "          \"memberId\": $writerId,\n" +
            "          \"dueData\": \"$dueData\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"piece\": $piece\n" +
            "    }\n" +
            "  ]}"
}