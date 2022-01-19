import java.nio.file.Paths

class Utils {
    fun getCurrentPath(): String {
        return Paths.get("")
            .toAbsolutePath()
            .toString()
    }
}