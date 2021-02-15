package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.components.switch
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.states
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.switchDemo(): Div {
    val checkedStore1 = RootStore(false)
    val checkedStore2 = RootStore(false)
    val checkedStore3 = RootStore(false)
    val checkedStore4 = RootStore(false)
    val checkedStore5 = RootStore(false)
    val checkedStore6 = RootStore(false)

    return contentFrame {
        showcaseHeader("Switch")
        paragraph {
            +"A switch button works like a checkbox. It can be used to set a single property in your application."
            +" It comes with its own styling contexts for the dot style and the checked style."
        }
        showcaseSection("Usage")
        paragraph {
            +"Create a switch by defining a label and a Flow of Boolean for its state. You also want to use the "
            c("events")
            +" context to connect your handlers."
        }

        componentFrame {
            switch {
                label("Simple Switch")
                checked(checkedStore1.data)
                events {
                    changes.states() handledBy checkedStore1.update
                }
            }
        }

        playground {
            source(
                """
                switch {
                    label("Simple Switch")
                    checked(checkedStore.data)
                    events {
                        changes.states() handledBy checkedStore.update
                    }
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
            switch() {
                label("Small")
                size { small }
                checked(checkedStore4.data)
                events {
                    changes.states() handledBy checkedStore4.update
                }
            }
        }
        componentFrame {
            switch() {
                label("Normal (default)")
                checked(checkedStore5.data)
                events {
                    changes.states() handledBy checkedStore5.update
                }
            }
        }
        componentFrame {
            switch() {
                label("Large")
                size { large }
                checked(checkedStore6.data)
                events {
                    changes.states() handledBy checkedStore6.update
                }
            }
        }
        playground {
            source(
                """
                    switch {
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
                    color { secondaryEffect }
                }
            }) {
                label("Custom background colors")
                checked(checkedStore2.data)
                checkedStyle {
                    background { color { secondary } }
                }
                events {
                    changes.states() handledBy checkedStore2.update
                }
            }
        }
        componentFrame {
            switch {
                label("Custom dot")
                checked(checkedStore3.data)
                dotStyle {
                    size { "0.8rem" }
                    radius { "3px" }
                    background { color { dark } }
                }
                events {
                    changes.states() handledBy checkedStore3.update
                }
            }
        }
        playground {
            source(
                """
                switch(styling = {
                    // change unchecked style via styling parameter
                    background { 
                        color { tertiary }
                    }
                }) {
                    label("Custom background colors")
                    // use component function to change checked style
                    checkedStyle {
                        background { color { primary } }
                    }
                }    

                switch {
                    label("Custom dot")
                    // use component function to change dot style
                    dotStyle {
                        size { "0.8rem" }
                        radius { "3px" }
                        background { color { dark } }
                    }
                }
                """
            )
        }
    }
}