package dev.fritz2.kitchensink.demos

import dev.fritz2.components.alert
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.styled

fun RenderContext.alertDemo(): Div {
    return contentFrame {
        showcaseHeader("Alerts")

        paragraph {
            c("Alerts")
            +" provide a solution to display content in a highlighted way."
            +" They come with a variety of color schemes and layout variants to choose from,"
            +" and can be custom-styled as well."
        }

        showcaseSection("Usage")
        paragraph {
            +"A simple alert with default properties can be created by simply specifying a title and content."
        }
        contentFrame {
            alert {
                title("Alert")
                content("This is an alert.")
            }
        }
        playground {
            source(
                """
                    alert {
                    title("Alert")
                    content("This is an alert.")
                }
                """.trimIndent()
            )
        }

        showcaseSection("Customizing")
        paragraph {
            +"You can customize the alert's color scheme, choose from a variety of layout variants and even use "
            +" custom layouts for the title and content."
        }

        paragraph {
            +"The color scheme can be changed by specifying the alert's severity using the "
            c("severity")
            +" property. Possible values are info, success, warning and error."
        }
        componentFrame {
            alert({
                margin { tiny }
            }) {
                content("Severity: Info")
                severity { info }
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: Success")
                severity { success }
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: Warning")
                severity { warning }
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: Error")
                severity { error }
            }
        }
        playground {
            source(
                """
                    alert {
                        content("Severity: Info")
                        severity { info }
                    }
                    alert {
                        content("Severity: Success")
                        severity { success }
                    }
                    alert {
                        content("Severity: Warning")
                        severity { warning }
                    }
                    alert {
                        content("Severity: Error")
                        severity { error }
                    }
                """.trimIndent()
            )
        }

        paragraph {
            +"The alert's layout variant can be changed via the "
            c("variant")
            +" property. Currently a subtle (default) and solid variant are available as well as variants with either"
            +" a colored left or top accent."
        }
        componentFrame {
            alert({
                margin { tiny }
            }) {
                content("This is a subtle alert.")
            }
            alert({
                margin { tiny }
            }) {
                content("This is a solid alert.")
                variant { solid }
            }
            alert({
                margin { tiny }
            }) {
                content("This is a left-accented alert.")
                variant { leftAccent }
            }
            alert({
                margin { tiny }
            }) {
                content("This is a top-accented alert.")
                variant { topAccent }
            }
        }
        playground {
            source(
                """
                    alert {
                        content("This is a subtle alert.")
                    }
                    alert {
                        content("This is a solid alert.")
                        variant { solid }
                    }
                    alert {
                        content("This is a left-accented alert.")
                        variant { leftAccent }
                    }
                    alert {
                        content("This is a top-accented alert.")
                        variant { topAccent }
                    }
                """.trimIndent()
            )
        }

        paragraph {
            +"It is also possible to pass custom layouts for the title and content. This can be done via the "
            c("title")
            +" and "
            c("content")
            +" methods as well."
        }
        componentFrame {
            alert({
                margin { tiny }
            }) {
                title {
                    // Title that takes up the whole line
                    (::div.styled {
                        fontWeight { bold }
                    }) {
                        +"Custom title"
                    }
                }
                content("Content")
            }
            alert({
                margin { tiny }
            }) {
                title("Title")
                content {
                    (::span.styled {
                        color { lighterGray }
                    }) {
                        +"Custom content"
                    }
                }
            }
        }
        playground {
            source(
                """
                    alert {
                        title {
                            // Title that takes up the whole line
                            (::div.styled {
                                fontWeight { bold }
                            }) {
                                +"Custom title"
                            }
                        }
                        content("Content")
                    }
                    alert {
                        title("Title")
                        content {
                            // Content with custom text color
                            (::span.styled {
                                color { lighterGray }
                            }) {
                                +"Custom content"
                            }
                        }
                    }
                """.trimIndent()
            )
        }
    }
}