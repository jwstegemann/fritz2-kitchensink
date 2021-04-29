package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.clickButton
import dev.fritz2.components.dataTable
import dev.fritz2.components.datatable.DataTableComponent
import dev.fritz2.components.datatable.SelectionContext
import dev.fritz2.components.datatable.SelectionStrategy
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.datatable.*
import dev.fritz2.lenses.asString
import dev.fritz2.styling.theme.Theme
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.Style
import kotlinx.coroutines.flow.map
import dev.fritz2.components.datatable.*

val simpleColumnsDefinition: DataTableComponent<Person, Int>.() -> Unit = {
    columns {
        column(title = "Id") { lens(L.Person.id.asString()) }
        column(title = "Lastname") { lens(L.Person.lastname) }
        column(title = "Firstname") { lens(L.Person.firstname) }
        column(title = "Birthday") { lens(L.Person.birthday) }
    }
}

fun RenderContext.renderDataTableSelectionSingle(
    selectedPerson: RootStore<Person?>,
    strategy: SelectionContext.StrategyContext.StrategySpecifier
) {
    dataTable(
        rows = storeOf(persons.take(8)), rowIdProvider = Person::id,
        selection = selectedPerson
    ) {
        selection { strategy { strategy } }
        simpleColumnsDefinition()
    }
    lineUp({
        alignItems { end }
    }) {
        items {
            storeContentBox("Selected") {
                selectedPerson.data.render {
                    span {
                        if (it == null) {
                            "nothing selected..."
                        } else {
                            +"${it.firstname} ${it.lastname}"
                        }
                    }
                }
            }
            clickButton { text("Clear selection") }.map { null } handledBy selectedPerson.update
        }
    }
}

fun RenderContext.renderDataTableSelectionMultiple(
    selectedPersons: RootStore<List<Person>>,
    strategy: SelectionContext.StrategyContext.StrategySpecifier
) {
    dataTable(
        rows = storeOf(persons.take(8)), rowIdProvider = Person::id,
        selection = selectedPersons
    ) {
        selection { strategy { strategy } }
        simpleColumnsDefinition()
    }
    lineUp({
        alignItems { baseline }
    }) {
        items {
            storeContentBox("Selected") {
                selectedPersons.data.render {
                    if (it.isNotEmpty()) {
                        ul {
                            selectedPersons.data.renderEach {
                                li { +"${it.firstname} ${it.lastname}" }
                            }
                        }
                    }
                }
            }
            clickButton { text("Clear selection") }.map {
                emptyList<Person>()
            } handledBy selectedPersons.update
        }
    }
}

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
        paragraph {
            +"The following simple example uses a decent model of "
            c("List<Person>")
            +" to show a complete DataTable, "
            +"where each column is already sortable by default:"
        }
        componentFrame {
            dataTable(rows = storeOf(persons.take(8)), rowIdProvider = Person::id) {
                simpleColumnsDefinition()
            }
        }
        highlight {
            source(
                """
                    // start with some model type
                    data class Person(
                        val id: Int,
                        val firstname: String,
                        val lastname: String,
                        val birthday: String
                    )
                    
                    // create the whole data model
                    val persons = listOf(/* ... */)

                    // inject a store of persons and an id-provider
                    dataTable(rows = storeOf(persons), rowIdProvider = Person::id) {
                        // start declaration of the columns
                        columns {
                            // Declare each column that should appear:
                            // provide an (optional) title string, then start the declaration 
                            // context of a column
                            column(title = "Id") { 
                                // in order to render the content and enable the default sorting, 
                                // we need to provide a Lens<T, String>. You can omit this lens, 
                                // but then you must take care of the content rendering and 
                                // sorting manually! (This will be explained later!) 
                                lens(L.Person.id.asString()) 
                            }
                            column(title = "Lastname") { lens(L.Person.lastname) }
                            column(title = "Firstname") { lens(L.Person.firstname) }
                            column(title = "Birthday") { lens(L.Person.birthday) }
                        }
                    }
                """
            )
        }

        paragraph {
            +"In order to emphasize the elegance and simplicity, we show this declaration again without noisy comments:"
        }
        highlight {
            source(
                """
                dataTable(rows = storeOf(persons), rowIdProvider = Person::id) {
                    columns {
                        column(title = "Id") { lens(L.Person.id.asString()) }
                        column(title = "Lastname") { lens(L.Person.lastname) }
                        column(title = "Firstname") { lens(L.Person.firstname) }
                        column(title = "Birthday") { lens(L.Person.birthday) }
                    }
                }                    
                """
            )
        }

        showcaseSection("Selection")
        paragraph {
            +"A DataTable supports different kind of selection mechanisms out of the box:"
            ul {
                li { +"Selecting a single row" }
                li { +"Selecting multiple rows" }
            }
            +"This behaviour is determined automatically by passing an appropriate "
            c("Store")
            +" into the factory function."
        }

        showcaseSubSection("Single Selection")
        paragraph {
            +"In order to activate the selection of one row, just pass a "
            c("Store<T?>")
            +" into the factory function "
            c("dataTable")
            +" that acts as the target (and source) of the selection:"
        }
        componentFrame {
            val selectedPerson = storeOf<Person?>(persons[2])
            renderDataTableSelectionSingle(selectedPerson, SelectionContext.StrategyContext.StrategySpecifier.Click)
        }
        highlight {
            source(
                """
                // create a store of a *nullable* T 
                val selectedPerson = storeOf<Person?>(persons[2]) // pre-select 3rd entry
                
                dataTable(
                    rows = storeOf(persons), 
                    rowIdProvider = Person::id,
                    selection = selectedPerson // inject this selection store 
                ) {
                    columns {
                        column(title = "Id") { lens(L.Person.id.asString()) }
                        column(title = "Lastname") { lens(L.Person.lastname) }
                        column(title = "Firstname") { lens(L.Person.firstname) }
                        column(title = "Birthday") { lens(L.Person.birthday) }
                    }
                }                    
                """
            )
        }
        coloredBox(Theme().colors.info) {
            +"Remember to make the model type "
            c("T")
            +" nullable! So the store must always be a "
            c("Store<T?>")
            +" to support the state, where nothing (="
            c("null")
            +") is selected!"
        }

        showcaseSubSection("Multiple Selection")
        paragraph {
            +"In order to enable the selection of multiple rows, just pass a "
            c("Store<List<T>>")
            +" into the factory function "
            c("dataTable")
            +" that acts as the target (and source) of the selection:"
        }
        componentFrame {
            val selectedPersons = storeOf<List<Person>>(emptyList())
            renderDataTableSelectionMultiple(
                selectedPersons,
                SelectionContext.StrategyContext.StrategySpecifier.Checkbox
            )
        }
        highlight {
            source(
                """
                // selection is now based upon a List<T> 
                val selectedPersons = storeOf<List<Person>>(emptyList())
                
                dataTable(
                    rows = storeOf(persons), 
                    rowIdProvider = Person::id,
                    selection = selectedPerson // inject this selection store 
                ) {
                    columns {
                        column(title = "Id") { lens(L.Person.id.asString()) }
                        column(title = "Lastname") { lens(L.Person.lastname) }
                        column(title = "Firstname") { lens(L.Person.firstname) }
                        column(title = "Birthday") { lens(L.Person.birthday) }
                    }
                }                    
                """
            )
        }
        paragraph {
            +"By default multiple selection is realized by prepending an additional column with checkboxes to the "
            +"table. This offers the possibility to have also a checkbox within the header of that selection column, "
            +"so that one can select all the rows at once."
        }

        showcaseSubSection("Explicit Configuration")
        paragraph {
            +"As shown in the sections above, there are three built-in combinations of selection mode and strategy "
            +"determined depending on the passed parameter "
            c("selection")
            +":"
            ul {
                li {
                    c("RootStore<T?>")
                    +" -> "
                    c("mode=single")
                    +" and "
                    c("strategy=click")
                }
                li {
                    c("RootStore<List<T>>")
                    +" -> "
                    c("mode=multi")
                    +" and "
                    c("strategy=checkbox")
                }
                li {
                    +"no selection store passed -> "
                    c("mode=none")
                    +" (Strategy does not matter anymore!)"
                }
            }
        }
        paragraph {
            +"If the default selection behaviours based upon the type of the passed "
            c("Store")
            +" do not fit to your use case, it is possible to configure the behaviour manually."
        }
        paragraph {
            +"By explicit configuration the strategy can be chosen freely:"
        }
        componentFrame {
            data class SelectionModel(
                val mode: dev.fritz2.components.datatable.SelectionMode,
                val strategy: SelectionContext.StrategyContext.StrategySpecifier
            ) {
                fun toSingleClick() = SelectionModel(
                    dev.fritz2.components.datatable.SelectionMode.Single,
                    SelectionContext.StrategyContext.StrategySpecifier.Click
                )

                fun toMultiClick() = SelectionModel(
                    dev.fritz2.components.datatable.SelectionMode.Multi,
                    SelectionContext.StrategyContext.StrategySpecifier.Click
                )

                fun toSingleCheckbox() = SelectionModel(
                    dev.fritz2.components.datatable.SelectionMode.Single,
                    SelectionContext.StrategyContext.StrategySpecifier.Checkbox
                )

                fun toMultiCheckbox() = SelectionModel(
                    dev.fritz2.components.datatable.SelectionMode.Multi,
                    SelectionContext.StrategyContext.StrategySpecifier.Checkbox
                )
            }

            val selectionStore = storeOf(
                SelectionModel(
                    dev.fritz2.components.datatable.SelectionMode.Single,
                    SelectionContext.StrategyContext.StrategySpecifier.Click
                )
            )

            val thStyle: Style<BoxParams> = {
                background { color { primary.main } }
                color { primary.mainContrast }
                padding { normal }
                border {
                    width { thin }
                    style { solid }
                    color { primary.highlight }
                }
            }
            val tdStyle: Style<BoxParams> = {
                background { color { tertiary.highlight } }
                color { tertiary.mainContrast }
                padding { normal }
                border {
                    width { thin }
                    style { solid }
                    color { tertiary.mainContrast }
                }
                textAlign { center }
            }

            fun RenderContext.renderCell(selected: Boolean, model: SelectionModel) {
                td({
                    tdStyle()
                    fontSize { large }
                    if (selected) {
                        background { color { secondary.main } }
                        color { secondary.mainContrast }
                    }
                }) {
                    if (selected) {
                        +"✓"
                    } else {
                        clicks.events.map { model } handledBy selectionStore.update
                    }
                }
            }

            selectionStore.data.render {
                table({
                    margins { bottom { normal } }
                }) {
                    thead {
                        th(thStyle) { +"strategy \\ mode" }
                        th(thStyle) { +"single" }
                        th(thStyle) { +"multiple" }
                        th(thStyle) { +"none" }
                    }
                    tbody {
                        tr {
                            th(thStyle) { +"click" }
                            renderCell(
                                it.strategy == SelectionContext.StrategyContext.StrategySpecifier.Click &&
                                        it.mode == SelectionMode.Single,
                                selectionStore.current.toSingleClick()
                            )
                            renderCell(
                                it.strategy == SelectionContext.StrategyContext.StrategySpecifier.Click &&
                                        it.mode == SelectionMode.Multi,
                                selectionStore.current.toMultiClick()
                            )
                            td(tdStyle) {
                                rowSpan(2)
                                if (it.mode == SelectionMode.None) {
                                    +"✓"
                                } else {
                                    clicks.events.map {
                                        selectionStore.current.copy(mode = SelectionMode.None)
                                    } handledBy selectionStore.update
                                }
                            }
                        }
                        tr {
                            th(thStyle) { +"checkbox" }
                            renderCell(
                                it.strategy == SelectionContext.StrategyContext.StrategySpecifier.Checkbox &&
                                        it.mode == SelectionMode.Single,
                                selectionStore.current.toSingleCheckbox()
                            )
                            renderCell(
                                it.strategy == SelectionContext.StrategyContext.StrategySpecifier.Checkbox &&
                                        it.mode == SelectionMode.Multi,
                                selectionStore.current.toMultiCheckbox()
                            )
                        }
                    }
                }
            }
            val selectedPersons = storeOf<List<Person>>(emptyList())
            val selectedPerson = storeOf<Person?>(null)
            selectionStore.data.render {
                when (it.mode) {
                    dev.fritz2.components.datatable.SelectionMode.Single ->
                        renderDataTableSelectionSingle(
                            selectedPerson,
                            it.strategy
                        )
                    dev.fritz2.components.datatable.SelectionMode.Multi ->
                        renderDataTableSelectionMultiple(
                            selectedPersons,
                            it.strategy
                        )
                    else -> dataTable(rows = storeOf(persons.take(8)), rowIdProvider = Person::id) {
                        simpleColumnsDefinition()
                    }
                }
            }
        }
        highlight {
            source(
                """
                dataTable(
                    rows = storeOf(persons),
                    rowIdProvider = Person::id,
                    selection = selectionStore
                ) {
                    // open declaration context for selection aspects 
                    selection {
                    // set the strategy *explicitly*
                        strategy { click } // or ``checkbox`` or ``none`` 
                    }
                    columns {
                        // omitted ...
                    }
                }                 
                """
            )
        }


        showcaseSection("Sorting")
        showcaseSection("Content Rendering")
        showcaseSection("Styling")
    }
}

