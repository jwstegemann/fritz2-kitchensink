package dev.fritz2.kitchensink.base

import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.theme.DefaultTheme
import dev.fritz2.styling.theme.Property
import dev.fritz2.styling.theme.ScaledValue
import dev.fritz2.styling.theme.Theme


interface ExtendedTheme : Theme {
    interface MyProp {
        val a: Property
        val b: Property
    }

    val test: MyProp

    val teaserText: Style<BasicParams>
}

open class SmallFonts : ExtendedTheme, DefaultTheme() {
    override val name = "small Fonts"

    override val test = object : ExtendedTheme.MyProp {
        override val a: Property = space.normal
        override val b: Property = "b"
    }

    override val teaserText: Style<BasicParams> = {
        fontWeight { semiBold }
        textTransform { uppercase }
        fontSize { smaller }
        letterSpacing { large }
        textShadow { glowing }
        color { info }
    }

    /*
    override val colors = object : Colors {
        override val primary = "#6ba506" // green
        override val primary_hover = "#b4cc85" // more transparent green
        override val secondary = "#F3B82E" // yellowish orange
        override val tertiary = "#718096" // bluish gray
        override val dark = "#2d3748" // dark gray
        override val light = "#cdd1d6" // light gray
        override val light_hover = "rgb(205,209,214, 0.5)" // same as light with less opac
        override val info = "#1998BF" // blue
        override val success = "#00B300" // bright green
        override val warning = "#F3A42E" // orange
        override val danger = "#E14F2A" // red
        override val base = "#ffffff" // white, rgb(255,255,255)
        override val disabled = light
        //color of focus of input elements: inner: #3182ce / rgb(49,130,206) outer: #acd2f2 / rgb(172,210,242)
        override val focus = primary_hover // this does not apply to shadow color
    }

     */
}


class LargeFonts : SmallFonts() {
    override val name = "large Fonts"

    override val fontSizes = ScaledValue(
        smaller = "1.125rem",
        small = "1.25rem",
        normal = "1.5rem",
        large = "1.875rem",
        larger = "2.25rem",
        huge = "3rem"
    )
}

