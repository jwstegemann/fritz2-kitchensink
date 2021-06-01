package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.styling.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.flow.map

fun RenderContext.sliderDemo(): Div {

    return contentFrame {

        showcaseHeader("Slider")

        paragraph {
            +"A slider is a component focusing on easy value input by movement, thus dragging the thumb with a mouse "
            +"click or by finger on a mobile device. It should be used therefore if visual feedback about the parameter "
            +"is more important than the exact number."
        }

        coloredBox(Theme().colors.danger) {
            +"Devices with touchscreen are not yet supported for sliding actions. "
            +"See "
            externalLink("Issue #376", "https://github.com/jwstegemann/fritz2/issues/376")
        }

        showcaseSection("Usage")
        paragraph {
            +"Define some store which holds an "
            c("Int")
            +" value and pass it as "
            c("value")
            +" parameter into the factory function. The slider will assume a "
            em { +"range" }
            +" of 1 to 100 per default."
        }
        componentFrame {
            val valueStore = storeOf(42)
            slider(value = valueStore) { }
            storeContentBox("Value") {
                valueStore.data.render { span { +it.toString() } }
            }
        }
        highlight {
            source(
                """
                val valueStore = storeOf(42)
                slider(value = valueStore) { }                    
                """
            )
        }

        showcaseSection("Ranges")
        paragraph {
            +"A slider is built on top of a "
            em { +"range" }
            +" in order to manage the values it can produce, which consists of the following properties:"
            ul {
                li {
                    c("lower")
                    +": The lower bound. This can be also negative!"
                }
                li {
                    c("upper")
                    +": The upper bound, which must always be greater than the lower bound. It may be negative too "
                    +"though."
                }
                li {
                    c("step")
                    +": A step is per default "
                    c("1")
                    +" but can be any other positive integer value. Be aware that if the "
                    em { +"distance" }
                    +" between the two bounds is not dividable by step, the upper bound will not be reachable!"
                }
            }
        }
        componentFrame {
            val valueStore = storeOf(0)
            slider(value = valueStore) {
                range {
                    lower(-30)
                    upper(30)
                    step(5)
                }
            }
            storeContentBox("Value") {
                valueStore.data.render { span { +it.toString() } }
            }
        }
        highlight {
            source(
                """
                slider(value = valueStore) {
                    range {
                        lower(-30)
                        upper(30)
                        step(5)
                    }
                }
                """
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"Choose from the three predefined sizes "
            c("small")
            +", "
            c("normal")
            +" (default), or  "
            c("large")
            +", or scale your checkboxes to your needs using the styling parameter."
        }
        componentFrame {
            stackUp {
                items {
                    slider(value = 20) { size { small } }
                    slider(value = 40) { }
                    slider(value = 60) { size { large } }
                }
            }
        }
        highlight {
            source(
                """
                slider(value = valueStore) {
                    size { small }
                }
                slider(value = valueStore) {
                }
                slider(value = valueStore) {
                    size { large }
                }
                """
            )
        }

        showcaseSection("Orientation")
        paragraph {
            +"The "
            c("orientation")
            +" of a slider may be either "
            c("horizontal")
            +" (default) or "
            c("vertical")
            +"."
        }
        componentFrame {
            val valueStore = storeOf(42)
            slider({
                height { "10rem" }
            }, value = valueStore) {
                orientation { vertical }
            }
            storeContentBox("Value") {
                valueStore.data.render { span { +it.toString() } }
            }
        }
        highlight {
            source(
                """
                slider({
                    height { "10rem" }
                }, value = valueStore) {
                    orientation { vertical }
                }                 
                """
            )
        }

        showcaseSection("Disabled")
        paragraph {
            +"A slider can be disabled, so its value will not change due to any kind of UI based interaction. "
            +"But the value can change of course reflecting changes within its data-binding of course!"
        }
        componentFrame {
            val disabled = storeOf(true)
            val valueStore = storeOf(0)
            stackUp {
                spacing { small }
                items {
                    slider(value = valueStore) { }
                    lineUp({
                        width { full }
                    }) {
                        items {
                            switch(value = disabled) { label(disabled.data.map { if (it) "Disabled" else "Enabled" }) }
                            slider(value = valueStore) {
                                disabled(disabled.data)
                            }
                        }
                    }
                }
            }
            storeContentBox("Value") {
                valueStore.data.render { span { +it.toString() } }
            }
        }
        highlight {
            source(
                """
                // layout elements omitted
                val disabled = storeOf(true)
                val valueStore = storeOf(0)
                
                // always enabled
                slider(value = valueStore) { }
                
                // choose interactive mode of following slider
                switch(value = disabled) { 
                    label(disabled.data.map { if (it) "Disabled" else "Enabled" }) 
                }
                
                // dynamically change between enabled and disabled state
                slider(value = valueStore) {
                    disabled(disabled.data) // could be a simple value too
                }
                """
            )
        }

        showcaseSection("Customizing")

        showcaseSubSection("Icon on thumb")
        paragraph {
            +"To support the common use case of adding an icon on top of the thumb, there is a dedicated "
            c("icon")
            +":"
        }
        componentFrame {
            slider(value = 0) {
                icon { add }
            }
        }
        highlight {
            source(
                """
                slider(value = valueStore) {
                    icon { add }
                }                    
                """
            )
        }

        showcaseSubSection("Depp customization")
        paragraph {
            +"This component allows customization for all relevant sub components:"
            ul {
                li {
                    c("track")
                    +": access its styling context and provide any "
                    c("Style<BoxParam>")
                    +"."
                }
                li {
                    c("trackFilled")
                    +": access its styling context and provide any "
                    c("Style<BoxParam>")
                    +". On top the current percent according to its value is injected, so the styling can be "
                    em { +"dynamically" }
                    +" adapted."
                }
                li {
                    c("thumb")
                    +": change its styling and its content. As before the percent value is available within the "
                    +"styling expression. The content expression offers the full "
                    c("state")
                    +" object of the slider, so following properties are available:"
                    ul {
                        li { c("percent") }
                        li { c("value") }
                        li { c("disabled") }
                        li {
                            c("movementTracking")
                            +": This flag is only "
                            c("true")
                            +" if the slider is moved by a sliding action"
                        }
                    }
                }
            }
        }
        componentFrame {
            val valueStore = storeOf(78)
            slider({
                margins { top { normal } }
            }, value = valueStore) {
                size { large }
                track {
                    background { color { gray100 } }
                }
                trackFilled { percent ->
                    background {
                        color {
                            when {
                                percent < 33 -> danger.main
                                percent < 66 -> warning.main
                                else -> success.main
                            }
                        }
                    }
                }
                thumb({ percent ->
                    background {
                        color {
                            when {
                                percent < 33 -> danger.highlight
                                percent < 66 -> warning.highlight
                                else -> success.highlight
                            }
                        }
                    }
                    fontSize { smaller }
                }) { state ->
                    +when {
                        state.progress.percent < 33 -> "\uD83D\uDE41"
                        state.progress.percent < 66 -> "\uD83D\uDE10"
                        else -> "\uD83D\uDE42"
                    }
                    if (state.movementTracking) {
                        div({
                            position { absolute { } }
                            background { color { secondary.highlight } }
                            color { secondary.highlightContrast }
                            css("transform: translateY(-1.75rem)")
                            radius { small }
                            paddings { horizontal { small } }
                        }) {
                            +state.progress.value.toString()
                        }
                    }
                }
            }
            storeContentBox("Value") {
                valueStore.data.render {
                    span { +it.toString() }
                }
            }
        }

        coloredBox(Theme().colors.info) {
            +"The walk through the code of the above example is split up into the relevant parts. "
            +"The omitted aspects are just slider DSL as explained in the top sections."
        }

        paragraph {
            +"In order to lighten up the "
            c("track")
            +" just pass a styling expression into this property:"
        }
        highlight {
            source(
                """
                track {
                    background { color { gray100 } }
                }                    
                """
            )
        }

        paragraph {
            +"The "
            c("trackFilled")
            +" should adapt its color to the percent:"
        }
        highlight {
            source(
                """
                trackFilled { percent ->
                    background {
                        color {
                            when {
                                percent < 33 -> danger.main
                                percent < 66 -> warning.main
                                else -> success.main
                            }
                        }
                    }
                }                    
                """
            )
        }

        paragraph {
            +"The "
            c("thumb")
            +" should adapt in two ways:"
            ul {
                li { +"Its background color should also change with the current percent" }
                li { +"Its content should also depend on the percent to emphasize the quality of the value" }
            }
        }
        highlight {
            source(
                """
                thumb({ percent -> // styling section as first parameter
                    background {
                        color {
                            when {
                                percent < 33 -> danger.highlight
                                percent < 66 -> warning.highlight
                                else -> success.highlight
                            }
                        }
                    }
                    fontSize { smaller }
                }) { state -> // content section as second parameter
                    +when {
                        // add a smiley according to the percent
                        state.progress.percent < 33 -> "\uD83D\uDE41" // sad
                        state.progress.percent < 66 -> "\uD83D\uDE10" // neutral
                        else -> "\uD83D\uDE42" // smiling
                    }
                }                    
                """
            )
        }

        paragraph {
            +"As special highlight the use of the "
            c("state")
            +" object within the "
            c("thumb")
            +" content context should be demonstrated by adding a small tooltip alike box on top of the slider "
            +"that will only appear during the movement of the thumb."
        }
        highlight {
            source(
                """
                thumb({ percent -> 
                    // omitted
                }) { state -> // content section as second parameter
                    // code for smileys omitted (see snippet above)
                    if (state.movementTracking) { // render the box only on sliding actions 
                        div({
                            position { absolute { } }
                            background { color { secondary.highlight } }
                            color { secondary.highlightContrast }
                            css("transform: translateY(-1.75rem)")
                            radius { small }
                            paddings { horizontal { small } }
                        }) {
                            // show the current value, not percent!
                            +state.progress.value.toString()
                        }
                    }                    
                }                    
                """
            )
        }
    }
}