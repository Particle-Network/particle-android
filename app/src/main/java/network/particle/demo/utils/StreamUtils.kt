package network.particle.demo.utils

import android.content.res.Resources
import java.io.BufferedReader
import java.io.InputStreamReader


object StreamUtils {

    fun getRawString(resource: Resources, rawId: Int): String {
        val stream = resource.openRawResource(rawId)
        try {
            val reader = BufferedReader(InputStreamReader(stream, "utf-8"))
            val sb = StringBuilder()
            var line: String? = null
            while (run {
                    line = reader.readLine()
                    line
                } != null) {
                sb.append(line + "\n")
            }
            return sb.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            stream.close()
        }
        return ""
    }

}