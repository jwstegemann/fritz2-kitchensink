package dev.fritz2.kitchensink.base

import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.theme.Property

//TODO: move to styling
val Property.important: Property
    get() = "$this !important"

val switchLayoutSm: Style<FlexParams> = {
    direction(sm = { column.important }, md = { row.important })
    children(" > *") {
        margins(
            sm = {
                horizontal { none.important }
                bottom { normal.important }
            },
            md = {
                vertical { none.important }
                right { normal.important }
            }
        )
    }
}