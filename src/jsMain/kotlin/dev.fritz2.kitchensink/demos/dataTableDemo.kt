package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.dataTable
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.Developer
import dev.fritz2.kitchensink.L
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.developers

fun RenderContext.dataTableDemo(): Div {
    return contentFrame {
        showcaseHeader("DataTable")

        paragraph {
            +"The DataTable component provides a way to visualize tabular data and offers interaction for the user "
            +"in order to sort by columns or select specific rows."
        }

        paragraph {
            +"The API is designed to scale well from the simplest use case of a read only table, towards a fully "
            +"interactive table with live editing a cell directly within the table itself. "
            +"The component is also very flexible and offers lots of customization possibilities like for..."
            ul {
                li { +"the styling of the header or the columns" }
                li { +"the styling based upon the index or the content of a row or cell" }
                li { +"the sorting mechanisms (logic and UI)" }
                li { +"the selection mechanism (logic and UI)" }
            }
        }

        showcaseSection("Usage")
        paragraph {
            +"In order to use the DataTable you need at least one store of type "
            c("List<T>")
            +" where "
            c("T")
            +" represents the data model and one function "
            c("(T) -> I")
            +" called "
            c("rowIdProvider")
            +" which determines the unique ID for one row of the data within the whole list of all rows."
        }
        componentFrame {
            dataTable(rows = storeOf(developers), rowIdProvider = Developer::id) {
                columns {
                    column(title = "Lastname") { lens(L.Developer.lastname) }
                    column(title = "Firstname") { lens(L.Developer.firstname) }
                    column(title = "Address") { lens(L.Developer.address + L.Address.main) }
                    column(title = "Country") { lens(L.Developer.address + L.Address.country) }
                }
            }
        }

    }
}
