package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.components.*
import dev.fritz2.components.data.File
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.buttons_
import dev.fritz2.styling.hr
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.fileDemo(): Div {

    fun Long.asKB(digits: Int = 0): String = "${(this / 1000.toDouble()).asDynamic().toFixed(digits)} KB"

    fun fileModal(file: File) = modal {
        size { small }
        content {
            stackUp {
                items {
                    h2{ +"Selected file" }
                    hr({ width { full } }){}
                    p { +"Name: ${file.name}" }
                    p { +"Media Type: ${file.type}" }
                    p { +"Size: ${file.size.asKB(2)}" }
                }
            }
        }
    }

    fun filesModal(files: List<File>, showContent: Boolean = false) = modal {
        size { small }
        content {
            stackUp {
                items {
                    h2{ +"Selected files" }
                    hr({ width { full } }){}
                    files.forEach {
                        lineUp {
                            items {
                                p { +it.name }
                                p { +"Size: ${it.size.asKB(2)}" }
                                if(showContent) p { +it.content.substring(0, 30)}
                            }
                        }
                    }
                }
            }
        }
    }

    val fileStore = object : RootStore<Unit>(Unit) {
        val showFile = handle<File> { _, file ->
            fileModal(file)()
        }
        val showFiles = handle<List<File>> { _, files ->
            filesModal(files)()
        }
        val showFilesWithContent = handle<List<File>> { _, files ->
            filesModal(files, showContent = true)()
        }
    }

    return contentFrame {

        showcaseHeader("File")

        paragraph {
            +"""
            Use this component to open a file selection dialog. The dialog returns a single or multiple files 
            which can be received by the handler of a store. 
            """
        }
        paragraph {
            +"""Please note that the creation of modal dialogs was omitted in some of 
                the examples to keep the source fragments short.
            """
        }

        showcaseSection("Usage")
        paragraph {
            +"Use the "
            c("file")
            +" function for a single file selection, or "
            c("files")
            +" for selecting multiple files. "
            +"You can use any button described on our "
            internalLink("button page", buttons_)
            +" - just call the "
            c("button")
            +" function inside the file function context."
            +"The functions return a "
            c("File")
            +" or a "
            c("List<File>")
            +"  which can directed to a matching handler using "
            c("handledBy")
            +"."
        }

        coloredBox(Theme().colors.info){
            p {
                +"To select multiple files at once, you need to press and hold the "
                c("Shift")
                +" key while selecting the files."
            }
        }

        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    file {
                        button { text("Single select") }
                    } handledBy fileStore.showFile

                    files {
                        button { text("Multi select") }
                    } handledBy fileStore.showFiles
                }
            }
        }
        highlight {
            source(
                """
                val fileStore = object : RootStore<Unit>(Unit) {
                    val showFile = handle<File> { _, file ->
                        fileModal(file)()
                    }
                    val showFiles = handle<List<File>> { _, files ->
                        filesModal(files)()
                    }
                    val showFilesWithContent = handle<List<File>> { _, files ->
                        filesModal(files, showContent = true)()
                    }
                }
                
                file {
                    button { text("Single select") }
                } handledBy fileStore.showFile

                files {
                    button { text("Multi select") }
                } handledBy fileStore.showFiles
                """
            )
        }

        showcaseSection("Accepting File Types")
        paragraph {
            +"An important property for the the file selection is the "
            externalLink("accept", "https://developer.mozilla.org/en-US/docs/Web/HTML/Element/input/file#htmlattrdefaccept")
            +" property, which controls the default file-types in the file selection dialog."
        }
        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    file {
                        button({
                            background { color { info.main } }
                        }) {
                            icon { cloudUpload }
                            text("Accept every file")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("image/*")
                        button({
                            background { color { warning.main } }
                        }) {
                            icon { image }
                            text("Accept only image files")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("application/pdf")
                        button({
                            background { color { danger.main } }
                        }) {
                            icon { document }
                            text("Accept only pdf files")
                        }
                    } handledBy fileStore.showFile
                }
            }
        }
        highlight {
            source(
                """
                file {
                    button({
                        background { color { info.main } }
                    }) {
                        icon { cloudUpload }
                        text("Accept every file")
                    }
                } handledBy fileStore.showFile

                file {
                    accept("image/*")
                    button({
                        background { color { warning.main } }
                    }) {
                        icon { image }
                        text("Accept only image files")
                    }
                } handledBy fileStore.showFile

                file {
                    accept("application/pdf")
                    button({
                        background { color { danger.main } }
                    }) {
                        icon { document }
                        text("Accept only pdf files")
                    }
                } handledBy fileStore.showFile
                """
            )
        }

        showcaseSection("Text Files")
        paragraph {
            +"In case of selecting text-files only, you can parse their contents by passing a value to the "
            c("encoding")
            +" function. The content of the resulting "
            c("File")
            +" object is then encoded in this format (e.g. utf-8)."
        }
        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    files {
                        encoding("utf-8")
                        accept("text/plain")
                        button {
                            icon { cloudUpload }
                            text("Text files")
                        }
                    } handledBy fileStore.showFilesWithContent
                }
            }
        }
        highlight {
            source(
                """
                files {
                    encoding("utf-8")
                    accept("text/plain")
                    button {
                        icon { cloudUpload }
                        text("Text files")
                    }
                } handledBy fileStore.showFilesWithContent
                """
            )
        }
    }
}