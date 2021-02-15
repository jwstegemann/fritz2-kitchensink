package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.invoke
import dev.fritz2.components.*
import dev.fritz2.components.data.File
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.buttons_
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.fileDemo(): Div {

    fun Long.asKB(digits: Int = 0): String = "${(this / 1000.toDouble()).asDynamic().toFixed(digits)} KB"

    fun fileModal(file: File) = modal {
        size { small }
        closeButton()
        content {
            stackUp {
                items {
                    h2{ +"Selected file" }
                    (::hr.styled { width { full } }){}
                    p { +"Name: ${file.name}" }
                    p { +"Media Type: ${file.type}" }
                    p { +"Size: ${file.size.asKB(2)}" }
                }
            }
        }
    }

    fun filesModal(files: List<File>) = modal {
        size { small }
        closeButton()
        content {
            stackUp {
                items {
                    h2{ +"Selected files" }
                    (::hr.styled { width { full } }){}
                    files.forEach {
                        lineUp {
                            items {
                                p { +it.name }
                                p { +"Size: ${it.size.asKB(2)}" }
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
    }

    return contentFrame {

        showcaseHeader("File")

        paragraph {
            +"""
            Use this component to open a file selection dialog which returns a single or multiple files 
            which can be received by a handler of a store. 
            """.trimIndent()
        }
        paragraph {
            +"Please note that the creation of modal dialogs was omitted in some of the examples to keep the source fragments short."
        }

        showcaseSection("Usage")
        paragraph {
            +"Use the "
            c("file{}")
            +" function for a single file selection and or the "
            c("files{}")
            +" function for selecting multiple files. "
            +"As visible button you can use everything that is described on "
            internalLink("button page", buttons_)
            +" , just by calling the "
            c("button()")
            +" function inside. "
            +"Both functions return a "
            c("File")
            +" or a "
            c("List")
            +" of them wich can directed to a matching handler by using "
            c("handledBy()")
            +"."
        }
        paragraph {
            +"Note: For selecting multiple files at once you need to press and hold the "
            c("Shift")
            +" key while selection the files."
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
        playground {
            source(
                """
                    val fileStore = object : RootStore<Unit>(Unit) {
                        val showFile = handle<File> { _, file -> fileModal(file)() }
                        val showFiles = handle<List<File>> { _, files -> filesModal(files)() }
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

        showcaseSection("Accept")
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
                            background { color { info } }
                        }) {
                            icon { fromTheme { cloudUpload } }
                            text("Accept every file")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("image/*")
                        button({
                            background { color { warning } }
                        }) {
                            icon { fromTheme { image } }
                            text("Accept only image files")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("application/pdf")
                        button({
                            background { color { danger } }
                        }) {
                            icon { fromTheme { document } }
                            text("Accept only pdf files")
                        }
                    } handledBy fileStore.showFile
                }
            }
        }
        playground {
            source(
                """
                    file {
                        button({
                            background { color { info } }
                        }) {
                            icon { fromTheme { cloudUpload } }
                            text("Accept every file")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("image/*")
                        button({
                            background { color { warning } }
                        }) {
                            icon { fromTheme { image } }
                            text("Accept only image files")
                        }
                    } handledBy fileStore.showFile

                    file {
                        accept("application/pdf")
                        button({
                            background { color { danger } }
                        }) {
                            icon { fromTheme { document } }
                            text("Accept only pdf files")
                        }
                    } handledBy fileStore.showFile
                """
            )
        }

        showcaseSection("Text files")
        paragraph {
            +"In case of selecting text-files only you can parse their contents by providing an encoding to the "
            c("encoding()")
            +" function. Then the content of the resulting "
            c("File")
            +" object is a string of that encoding."
        }
        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    files {
                        encoding("utf-8")
                        accept("plain/*")
                        button {
                            icon { fromTheme { cloudUpload } }
                            text("Text files")
                        }
                    } handledBy fileStore.showFiles
                }
            }
        }
        playground {
            source(
                """
                    files {
                        encoding("utf-8")
                        accept("plain/*")
                        button {
                            icon { fromTheme { cloudUpload } }
                            text("Text files")
                        }
                    } handledBy fileStore.showFiles
                """
            )
        }
    }
}