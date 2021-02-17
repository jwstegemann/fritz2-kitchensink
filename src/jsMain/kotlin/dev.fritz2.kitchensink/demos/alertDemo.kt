package dev.fritz2.kitchensink.demos

import dev.fritz2.components.alert
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.kitchensink.toasts_
import dev.fritz2.styling.params.styled

fun RenderContext.alertDemo(): Div {
    return contentFrame {
        showcaseHeader("Alert")

        paragraph {
            +"Alerts provide a solution for highlighting important content."
            +" They come with a variety of color schemes and layout variants to choose from "
            +" and can of course be custom-styled as well."
            +" They can also be used as content for "
            internalLink("Toasts", toasts_)
            +"."
        }

        showcaseSection("Usage")
        paragraph {
            +"A simple alert with default properties can be created by simply specifying a title and content."
        }
        componentFrame {
            alert {
                title("Alert: ")
                content("This is a basic alert.")
            }
        }
        playground {
            source(
                """
                alert {
                    title("Alert: ")
                    content("This is a basic alert.")
                }
                """.trimIndent()
            )
        }

        showcaseSection("Icon")

        paragraph {
            +"The alert's icon can be changed via the "
            c("icon")
            +" property."
        }
        componentFrame {
            alert {
                icon { fritz2 }
                content("Alert using the fritz2 logo")
            }
        }
        playground {
            source(
                """
                alert {
                    icon { fritz2 }
                    content("Alert using the fritz2 logo")
                }
                """.trimIndent()
            )
        }

        showcaseSection("Customizing")

        paragraph {
            +"You can customize the alert's color scheme, choose from a variety of layouts and even use "
            +" custom layouts for title and content."
        }

        showcaseSubSection("Severity Color Scheme")
        paragraph {
            +"The color scheme can be changed by specifying the alert's severity using the "
            c("severity")
            +" property. Possible values are "
            c("info")
            +" (default), "
            c("success")
            +", "
            c("warning")
            +", and "
            c("error")
            +"."
        }
        componentFrame {
            alert({
                margin { tiny }
            }) {
                content("Severity: info")
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: success")
                severity { success }
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: warning")
                severity { warning }
            }
            alert({
                margin { tiny }
            }) {
                content("Severity: error")
                severity { error }
            }
        }
        playground {
            source(
                """
                    alert {
                        content("Severity: info (default)")
                    }
                    alert {
                        content("Severity: success")
                        severity { success }
                    }
                    alert {
                        content("Severity: warning")
                        severity { warning }
                    }
                    alert {
                        content("Severity: error")
                        severity { error }
                    }
                """.trimIndent()
            )
        }

        showcaseSubSection("Layout Variant")
        paragraph {
            +"The alert's layout variant can be changed via the "
            c("variant")
            +" property. Currently a solid and subtle variant are available as well as variants with either"
            +" a colored left or top accent."
        }
        componentFrame {
            alert({
                margin { tiny }
            }) {
                content("This is the default solid alert.")
            }
            alert({
                margin { tiny }
            }) {
                content("This is a subtle alert.")
                variant { subtle }
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
                        content("This is the default solid alert.")
                    }
                    alert {
                        content("This is a subtle alert.")
                        variant { subtle }
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

        showcaseSubSection("Custom Title And Content")
        paragraph {
            +"It is also possible to pass custom layouts for the title and content. This can be done via the "
            c("title")
            +" and "
            c("content")
            +" methods."
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
                            // Title in its own line
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