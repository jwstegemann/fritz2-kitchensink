package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.spinner
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.AlignContentValues.flexStart
import dev.fritz2.styling.params.AlignContentValues.start
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.spinnerDemo(): Div {
    return contentFrame {
        showcaseHeader("Spinner")
        paragraph {
            +"A spinner is an animated element which indicates a running process. fritz2 spinners are customizable and"
            +" can of course be styled using the regular styling parameter."
        }

        showcaseSection("Usage")
        paragraph {
            +"A default spinner is created by simply calling the factory function:"
        }
        componentFrame {
            spinner {}
        }
        playground {
            source("""
                spinner {}
            """.trimIndent())
        }

        showcaseSection("Sizes")
        paragraph {
            +"Choose from three predefined sizes. The " // todo these are not spinner specific and should not be named as such
            c("normal")
            +" value is the default and does not need to be set explicitly."
        }
        componentFrame {
            lineUp({
                alignItems { flexEnd }
            }) {
                items {
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({ // todo should these go into component context? compare with other components
                                size { small }
                            }) {}
                            p { +"small" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {}
                            p { +"normal" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { huge }
                            }) {}
                            p { +"large" }
                        }
                    }
                }
            }
        }
        playground {
            source("""
                spinner({
                    size { large }
                }) {
                    // other properties
                }
            """.trimIndent())
        }

        showcaseSection("Thickness")
        paragraph { // todo change size to thickness in component context, also: "normal" should be default, but it's thin
            +"You can change the thickness of the spinner using the size property. Choose between "
            c("thin")
            +", "
            c("normal")
            +", and "
            c("fat")
            +", or use the styling parameter to define a custom value."
        }
        componentFrame {
            lineUp({
                alignItems { flexEnd }
            }) {
                items {
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {
                                size { thin }
                            }
                            p { +"thin" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {}
                            p { +"normal" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {
                                size { fat }
                            }
                            p { +"fat" }
                        }
                    }
                }
            }
        }
        playground {
            source("""
                spinner {
                    size { thin }
                }
                """.trimIndent())
        }
        showcaseSection("Speed")
        paragraph {
            +"Let your spinner turn as fast as you like: choose the time in seconds it should take the spinner to "
            +" complete one round. The default is 0.75 seconds." // todo check default value
        }
        componentFrame {
            lineUp({
                alignItems { flexEnd }
            }) {
                items {
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { large }
                            }) {
                                size { fat }
                                speed { ".5s" }
                            }
                            p { +"0.5s" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { large }
                            }) {
                                size { fat }
                                speed { "1s" }
                            }
                            p { +"1s" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { large }
                            }) {
                                size { fat }
                                speed { "3s" }
                            }
                            p { +"3s" }
                        }
                    }
                }
            }
        }
        playground {
            source("""
                spinner {
                    speed { ".5s" }
                }
            """.trimIndent())
        }

        showcaseSection("Icon Based Spinner")
        paragraph {
            +"You can choose an icon for your spinner which works just like the default semi circle:"
        }
        componentFrame {
            lineUp({
                alignItems { center }
            }) {
                items {
                    stackUp({
                        alignItems { center }
                    }) {
                        items {
                            spinner({
                                color { danger }
                                size { "2rem" }
                            }) {
                                icon { heart }
                            }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { "5rem" }
                            }) {
                                icon { fritz2 }
                                speed { "1.5s" }
                            }
                        }
                    }
                }
            }
        }
        playground {
            source("""
                    spinner({
                        size { "5rem" }
                    }) {
                        icon { fritz2 }
                        speed { "1.5s" }
                    }
            """.trimIndent())
        }
    }
}