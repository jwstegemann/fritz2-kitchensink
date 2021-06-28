package dev.fritz2.kitchensink.demos

import dev.fritz2.components.alert
import dev.fritz2.components.box
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.toasts_
import dev.fritz2.styling.span
import dev.fritz2.styling.theme.AlertSeverity
import dev.fritz2.styling.theme.ColorScheme
import dev.fritz2.styling.theme.IconDefinition
import dev.fritz2.styling.theme.Theme

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
        highlight {
            source(
                """
                alert {
                    title("Alert: ")
                    content("This is a basic alert.")
                }
                """
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
        highlight {
            source(
                """
                alert {
                    icon { fritz2 }
                    content("Alert using the fritz2 logo")
                }
                """
            )
        }

        showcaseSection("Customization")

        paragraph {
            +"You can customize the alert's color scheme, choose from a variety of layouts and even use "
            +" custom layouts for title and content."
        }

        showcaseSubSection("Severity")
        paragraph {
            +"The color scheme can be changed by specifying the alert's severity using the "
            c("severity")
            +" property. In case no other icon was set via the "
            c("color")
            +" property a matching icon is derived from the theme. "
            +"Possible values are "
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
                content("Severity: info (default)")
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
        highlight {
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
                """
            )
        }

        paragraph {
            +"For further customization it is also possible to add custom implementations of the "
            c("AlertSeverity")
            +" interface to your theme or pass them to the "
            c("AlertComponent")
            +" directly."
        }
        componentFrame {
            alert {
                content("Alert with custom severity")
                severity {
                    object : AlertSeverity {
                        override val colorScheme: ColorScheme = Theme().colors.primary
                        override val icon: IconDefinition = Theme().icons.pin
                    }
                }
            }
        }
        highlight {
            source(
                """
                alert {
                    content("Alert with custom severity")
                    severity {
                        object : AlertSeverity {
                            override val colorScheme: ColorScheme = Theme().colors.primary
                            override val icon: IconDefinition = Theme().icons.pin
                        }
                    }
                }
                """
            )
        }

        showcaseSubSection("Layout Variant")
        paragraph {
            +"The alert's layout variant can be changed via the "
            c("variant")
            +" property. Currently a solid, subtle and ghost variant are available, as well as variants of suble with "
            +" either a colored left or top accent."
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
            alert({
                margin { tiny }
            }) {
                content("This is a discreet alert.")
                variant { ghost }
            }
        }
        highlight {
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
                    alert {
                        content("This is a discreet alert.")
                        variant { discreet }
                    }
                """
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
                    box({
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
                    span({
                        color { gray200 }
                    }) {
                        +"Custom content"
                    }
                }
            }
        }
        highlight {
            source(
                """
                    alert {
                        title {
                            // Title that takes up the whole line
                            box({
                                fontWeight { bold }
                            }) {
                                +"Custom title"
                            }
                        }
                        content("Content")
                    }
                    alert({ margin { tiny } }) {
                        title("Title")
                        content {
                            span({
                                color { gray200 }
                            }) {
                                +"Custom content"
                            }
                        }
                    }
                """
            )
        }
    }
}