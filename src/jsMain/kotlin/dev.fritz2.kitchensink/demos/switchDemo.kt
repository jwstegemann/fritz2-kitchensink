package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.switch
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.switchDemo(): Div {

    return contentFrame {
        showcaseHeader("Switch")
        paragraph {
            +"A switch button works like a checkbox. It can be used to set a single property in your application."
            +" It comes with its own styling contexts for the dot style and the checked style."
        }
        showcaseSection("Usage")
        paragraph {
            +"Create a switch by defining a label and passing a boolean store into the factory function."
        }

        componentFrame {
            val checkedStore = storeOf(false)
            switch(store = checkedStore) {
                label("Simple Switch")
            }
            storeContentBox {
                p {
                    b { +"Switched on: " }
                    checkedStore.data.asText()
                }
            }
        }

        highlight {
            source(
                """
                val checkedStore = storeOf(false)                    
                switch(store = checkedStore) {
                    label("Simple Switch")
                }
                """
            )
        }


        showcaseSection("Sizes")
        paragraph {
            +"Choose from the available sizes "
            c("small")
            +", "
            c("normal")
            +", and "
            c("large")
            +". "
        }

        componentFrame {
            switch(store = storeOf(false)) {
                label("Small")
                size { small }
            }
        }
        componentFrame {
            switch(store = storeOf(false)) {
                label("Normal (default)")
            }
        }
        componentFrame {
            switch(store = storeOf(false)) {
                label("Large")
                size { large }
            }
        }
        highlight {
            source(
                """
                    switch(store = someStore) {
                        label("Small")
                        size { small }
                    }
                """
            )
        }

        showcaseSection("Customizing")
        paragraph {
            +"Use the component's context functions "
            c("checkedStyle")
            +" and "
            c("dotStyle")
            +" to change their respective appearances. Any styling for the unchecked state can be done via the regular styling parameter."
            +" Please note that some stores and events were omitted in the code fragments below to increase readability."
        }


        componentFrame {
            switch(styling = {
                background {
                    color { secondary.highlight }
                }
            }, store = storeOf(false)) {
                label("Custom background colors")
                checkedStyle {
                    background { color { secondary.base } }
                }
            }
        }
        componentFrame {
            switch(store = storeOf(false)) {
                label("Custom dot")
                dotStyle {
                    size { "0.8rem" }
                    radius { "3px" }
                    background { color { gray700 } }
                }
            }
        }
        highlight {
            source(
                """
                switch(styling = {
                    // change unchecked style via styling parameter
                    background { 
                        color { tertiary }
                    }
                }, store = someStore) {
                    label("Custom background colors")
                    // use component function to change checked style
                    checkedStyle {
                        background { color { primary.base } }
                    }
                }    

                switch(store = someStore) {
                    label("Custom dot")
                    // use component function to change dot style
                    dotStyle {
                        size { "0.8rem" }
                        radius { "3px" }
                        background { color { gray700 } }
                    }
                }
                """
            )
        }
    }
}