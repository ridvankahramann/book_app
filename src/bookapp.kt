import Global.Companion.gson
import Global.Companion.model
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.util.*


public class Global() {
    companion object {
        @JvmField
        var gson = GsonBuilder().setPrettyPrinting().create()
        var filePath = Utils().getCurrentPath() + "/assets/app.json"
        var jsonText = File(filePath).readText(Charsets.UTF_8)
        var model: Model = Gson().fromJson(jsonText, Model::class.java)
    }
}

fun main() {
    Global()
    print("\n 1.kitap ekle\n 2.kitap teslim et\n 3.kitap teslim al\n 4.kitap ara \n 5.kitapları listele\n")
    var inputnumber:Int = readLine()!!.toInt()
    when(inputnumber){
        1->book_add()
        else -> {
            print("yok")
        }
    }
}

fun book_add(){
    print("Kitap İsmi\n")
    val name = readLine()!!.toString()
    print("Kitap Sayfa\n")
    val page = readLine()!!.toInt()
    print("Kitap Fiyatı\n")
    val piece = readLine()!!.toInt()
    print("Yazar İsmi\n")
    val writerName = readLine()!!.toString()
    model.writers.forEach { writer -> if(writer.writerName == writerName){
        var holders = listOf<Holder>()
        var books = Book(holders,name,page,piece,writer)
        model.books.toMutableList().add(books)
        GsonBuilder().setPrettyPrinting().create()
        var modeltext: String = gson.toJson(model,Model::class.java)
        File(Global.url).writeText(modeltext)
    }else{
        val writerId = Random().nextInt(0, 100)
    } }
}
