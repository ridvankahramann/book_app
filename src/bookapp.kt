import Global.Companion.gson
import Global.Companion.model
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException
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
    val inputNumber: Int = readLine()!!.toInt()
    when(inputNumber){
        1->addNewBook()
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

fun saveModelToFile(): Boolean {
    var isSuccess = true
    try {
        val jsonText: String = gson.toJson(model, Model::class.java)
        File(Global.filePath).writeText(jsonText)
    } catch (exception: Exception) {
        print("Model data couldn\'t save: $exception")
        isSuccess = false
    } catch (ioException: IOException) {
       print("Model data couldn\'t save: $ioException")
        isSuccess = false
    }

    return isSuccess
}
