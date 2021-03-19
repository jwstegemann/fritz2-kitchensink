package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.stackUp
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
            +"Switches behave just like Checkboxes but comes with a different style."
        }
        showcaseSection("Usage")
        paragraph {
            +"Create a "
            c("switch")
            +" by defining a label and passing a "
            c("Store<Boolean>")
            +" into the function."
        }

        componentFrame {
            val checkedStore = storeOf(false)
            switch(store = checkedStore) {
                label("Simple Switch")
            }
            storeContentBox("Switched on") {
                span {
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
            c("normal (default)")
            +" and "
            c("large")
            +". "
        }

        componentFrame {
            stackUp {
                items {
                    switch(store = storeOf(false)) {
                        label("small")
                        size { small }
                    }
                    switch(store = storeOf(false)) {
                        label("normal")
                    }
                    switch(store = storeOf(false)) {
                        label("large")
                        size { large }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    switch(store = storeOf(false)) {
                        label("small")
                        size { small }
                    }
                    switch(store = storeOf(false)) {
                        label("normal")
                    }
                    switch(store = storeOf(false)) {
                        label("large")
                        size { large }
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
            +" to change their respective appearances."
            +" Any styling for the unchecked state can be done via the regular styling parameter."
        }


        componentFrame {
            stackUp {
                items {
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
                    switch(store = storeOf(false)) {
                        label("Custom dot")
                        dotStyle {
                            size { "0.8rem" }
                            radius { "3px" }
                            background { color { secondary.base } }
                        }
                    }
                }
            }
        }

        highlight {
            source(
                """
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
                switch(store = storeOf(false)) {
                    label("Custom dot")
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