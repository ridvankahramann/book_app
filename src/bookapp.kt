import Global.Companion.gson
import Global.Companion.model
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException

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
        2->deliverBook()
        3->pickupBook()
        4->searchBook()
        5->listBook()
        else -> {
            print("yok")
        }
    }
}

fun addNewBook(){
    val holders = listOf<Holder>()
    val book = Book(holders,"",0, 0, null)
    print("Kitap İsmi\n")
    val name = readLine()!!.toString()
    print("Kitap Sayfa\n")
    val page = readLine()!!.toInt()
    print("Kitap Fiyatı\n")
    val piece = readLine()!!.toInt()
    print("Yazar İsmi\n")
    val writerName = readLine()!!.toString()
    var filtersBooks = model.books.filter { book -> book.name == name }.size
    model.writers.forEach { writer ->
        if (writer.writerName == writerName && filtersBooks > 0){
            for (i in model.books.indices){
                var holders = listOf<Holder>()
                var book = Book(holders,model.books[i].name,model.books[i].page,model.books[i].piece++,writer)
                GsonBuilder().setPrettyPrinting().create()
                saveModelToFile()
            }
        }
        if(writer.writerName == writerName){
            var holders = listOf<Holder>()
            var books = Book(holders,name,page,piece,writer)
            model.books.toMutableList().add(books)
            GsonBuilder().setPrettyPrinting().create()
            saveModelToFile()
        }else{
            var holders = listOf<Holder>()
            var books = Book(holders,name,page,piece,writer)
            model.books.toMutableList().add(books)
            GsonBuilder().setPrettyPrinting().create()
            saveModelToFile()
        } }
}

fun deliverBook(){
    print("kitap teslim et")
}

fun pickupBook(){
    print("kitap teslim al")
}

fun searchBook(){
    print("Kitap İsmi\n")
    val bookName = readLine()!!.toString()
    var filter = model.books.filter { it.name == bookName}
    filter.forEach{print(listOf(it.holders,it.name,it.page,it.piece,it.writer))}
}

fun listBook(){
    model.books.forEach{
        print(listOf(it.holders,it.name,it.page,it.piece,it.writer))
    }
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
