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
            +"Spinners currently don't come with their own sizes context. However, you can use the styling parameter to"
            +" set their size to any size property or value."
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
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner({
                                size { "4.0rem" }
                            }) {}
                            p { +"4.0rem" }
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
        paragraph {
            +"You can change the line width of the spinner using the thickness property. Choose between "
            c("thin")
            +", "
            c("normal")
            +" (default), and "
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
                                thickness { thin }
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
                                thickness { fat }
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
            +" complete one round. The default is 0.5 seconds."
        }
        componentFrame {
            lineUp({
                alignItems { flexEnd }
            }) {
                items {
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {}
                            p { +"0.5s" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {
                                speed { "1s" }
                            }
                            p { +"1s" }
                        }
                    }
                    stackUp({ alignItems { center } }) {
                        items {
                            spinner {
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
                    speed { "3s" }
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