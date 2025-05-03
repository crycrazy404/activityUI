package util.fileChouser

import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

class FileSelector {
    fun selectFile(description: String, extension: String): String {
        var uri = ""
        try {
            val fileChooser = JFileChooser().apply {
                fileFilter = FileNameExtensionFilter(description, extension)
                dialogTitle = "Выберите файл"
            }

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                uri = fileChooser.selectedFile.absolutePath
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return uri
    }
}