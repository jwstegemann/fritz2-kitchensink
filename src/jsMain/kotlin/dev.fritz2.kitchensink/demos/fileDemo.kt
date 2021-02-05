package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.invoke
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.fileDemo(): Div {

    fun fileModal(file: FileRead) = modal {
        size { small }
        closeButton()
        content {
            stackUp {
                items {
                    lineUp {
                        items {
                            h3 { +file.name }
                            p { +file.type }
                        }
                    }
                    textArea({
                        height { "30rem" }
                    }) {
                        value(file.content)
                        size { large }
                    }
                }
            }
        }
    }

    val fileStore = object : RootStore<Unit>(Unit) {
        val showFile = handle<FileRead> { _, file ->
            fileModal(file)()
        }
    }

    return contentFrame {

        showcaseHeader("File")

        paragraph {
            +"""
            Use a button to open a file selection dialog which returns a file which can be handled 
            by a store, for example showing the content in a modal dialog. 
            """.trimIndent()
        }

        showcaseSection("Usage")
        paragraph {
            +"Setup your handler for a single file which gets load by the "
            c("fileButton")
            +" . You can specify the accepted file types (mime-types) and use encodings for parsing the content. "
            +"By default you get the content of the selected file as base64 string."
        }
        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    fileButton {
                        encoding("utf-8")
                        accept("text/plain")
                        text("Select a text file")
                    } handledBy fileStore.showFile
                }
            }
        }
        playground {
            source(
                """
                    val fileStore = object : RootStore<Unit>(Unit) {
                        val showFile = handle<FileRead> { _, file ->
                            fileModal(file)()
                        }
                    }

                     fileButton {
                        encoding("utf-8")
                        accept("text/plain")
                        text("Select a text file")
                    } handledBy fileStore.showFile
                """
            )
        }
    }
}