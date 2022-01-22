import Global.Companion.gson
import Global.Companion.model
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random

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
    print("Kitap İsmi\n")
    val name = readLine()!!.toString()
    print("Kitap Sayfa\n")
    val page = readLine()!!.toInt()
    print("Kitap Fiyatı\n")
    val piece = readLine()!!.toInt()
    print("Yazar İsmi\n")
    val writerName = readLine()!!.toString()
    val writerId = (0..100).random()
    model.writers.forEach { writer ->
        for (i in model.books.indices) {
            if (writer.writerName == writerName && model.books[i].name == name) {
                val index = model.books.indexOf(model.books[i])
                val book = Book(holders, model.books[i].name, model.books[i].page, model.books[i].piece++, writer)
                (model.books as ArrayList)[index] = book
                saveModelToFile()
                print("Kitap Güncellendi.")
                return@forEach
            }
            else if (writer.writerName == writerName) {
                val book = Book(holders,name,page,piece,writer)
                (model.books as ArrayList).add(book)
                GsonBuilder().setPrettyPrinting().create()
                print("Kitap Eklendi.")
                saveModelToFile()
                return@forEach
            } else {
                val writer = Writer(writerId,writerName)
                val book = Book(holders,name,page,piece,writer)
                (model.books as ArrayList).add(book)
                GsonBuilder().setPrettyPrinting().create()
                saveModelToFile()
                print("Kitap Eklendi.")
                return@forEach
            }
        }
    }
}

fun deliverBook(){
    print("Kullanıcı Id\n")
    val userId = readLine()!!.toString()
    for (i in model.books.indices){
        if(model.books[i].holders[0].memberId == userId){
            var bookname = model.books[i].name
            var writername = model.books[i].writer
            print("Kitap ismi: $bookname")
            print("\n")
            print("Yazar ismi ve Id'si: $writername")
            print("\n")
            print("Teslim etmek istediğin kitabın ismi: \n")
            val getbook = readLine()!!.toString()
            if(model.books[i].name == getbook){
                (model.books[i].holders as ArrayList).remove(model.books[i].holders[0])
                saveModelToFile()
                print("Kitap Teslim Edildi.")
            }
        }
    }
}

fun pickupBook(){
    for (i in model.books.indices){
        if(model.members.count() < model.books[i].piece){
            print("\n")
            print(model.books[i].name)
        }
    }
    print("\n")
    print("Teslim almak istediğin kitap")
    print("\n")
    var bookdeliver = readLine()!!.toString()
    for (i in model.books.indices) {
        if (model.books[i].name == bookdeliver) {
            print("Kullanıcı isim\n")
            var holdersname = readLine()!!.toString()
            var holdersmemberId = (0..1000).random().toString()
            val holders = Holder(holdersname,holdersmemberId)
            val members = Member(holdersname,holdersmemberId)
            (model.books[i].holders as ArrayList).add(holders)
            (model.members as ArrayList).add(members)
            saveModelToFile()
            print("Kitap Teslim Alındı.")
        }
    }
}

fun searchBook(){
    print("Kitap İsmi\n")
    val bookName = readLine()!!.toString()
    for (i in model.books.indices){
        if (model.books[i].name == bookName){
            var name = model.books[i].name
            var page = model.books[i].page
            var piece = model.books[i].piece
            var writer = model.books[i].writer
            print("Kitap ismi: $name")
            print("\n")
            print("Kitap sayfa: $page")
            print("\n")
            print("Kitap fiyat: $piece")
            print("\n")
            print("Kitap fiyat: $writer")
        }
    }
}

fun listBook(){
    for (i in model.books.indices){
        var holders = model.books[i].holders
        var name = model.books[i].name
        var page = model.books[i].page
        var piece = model.books[i].piece
        var writer = model.books[i].writer
        print("Kullanıcı bilgileri: $holders")
        print("\n")
        print("Kitap ismi: $name")
        print("\n")
        print("Kitap fiyat: $page")
        print("\n")
        print("Kitap fiyat: $piece")
        print("\n")
        print("Kitap fiyat: $writer")
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
