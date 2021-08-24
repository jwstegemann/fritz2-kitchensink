package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.datatable.DataTableComponent
import dev.fritz2.components.datatable.SelectionContext
import dev.fritz2.components.datatable.SelectionMode
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.html.Span
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.datatable.*
import dev.fritz2.lenses.Lens
import dev.fritz2.lenses.asString
import dev.fritz2.lenses.format
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDate

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
        alignItems { baseline }
    }) {
        items {
            storeContentBox("Selected") {
                selectedPerson.data.render {
                    span {
                        if (it == null) {
                            +"nothing selected..."
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
                    span {
                        if (it.isEmpty()) {
                            +"nothing selected..."
                        } else {
                            selectedPersons.data.map { list ->
                                list.joinToString { "${it.firstname} ${it.lastname}" }
                            }.asText()
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

val dateFormat: Lens<LocalDate, String> = format(
    parse = { LocalDate.parse(it) },
    format = {
        "${it.dayOfMonth.toString().padStart(2, '0')}.${
            it.monthNumber.toString().padStart(2, '0')
        }.${it.year}"
    }
)

enum class Functionality {
    BASIC, ADVANCED, ULTIMATE
}

val categories = mapOf(
    "Python" to Functionality.BASIC,
    "Scala" to Functionality.ADVANCED,
    "Java" to Functionality.BASIC,
    "Kotlin" to Functionality.BASIC,
    "Rust" to Functionality.BASIC,
    "Clojure" to Functionality.ADVANCED,
    "Frege" to Functionality.ULTIMATE,
)

fun FinalPerson.functionalSkill() =
    languages.mapNotNull { categories[it] }.maxOf { it }

fun RenderContext.dataTableDemo(): Div {

    return contentFrame {
        showcaseHeader("DataTable")

        paragraph {
            +"The "
            +"DataTable"
            +" component provides a way to visualize tabular data and offers interaction for the user "
            +"in order to sort by columns or select specific rows."
        }

        paragraph {
            +"The API is designed to scale well from the simplest use case of a read only table, towards a fully "
            +"interactive table with live editing a cell directly within the table itself. "
            +"The component is also very flexible and offers lots of customization possibilities like for..."
            ul {
                li { +"the sorting mechanisms (logic and UI)" }
                li { +"the selection mechanism (logic and UI)" }
                li { +"the general styling of the header or the columns" }
                li {
                    +"styling and content customization based upon the data and meta-information like the index, "
                    +"selection or sorting state"
                }
            }
        }

        showcaseSection("Usage")
        paragraph {
            +"In order to use the "
            +"DataTable"
            +" you need at least one store of type "
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

        showcaseSubSection("Multi Selection")
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
                    selection = selectedPersons // inject this selection store 
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
            +"By default multi selection is realized by prepending an additional column with checkboxes to the "
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
                val mode: SelectionMode,
                val strategy: SelectionContext.StrategyContext.StrategySpecifier
            ) {
                fun toSingleClick() = SelectionModel(
                    SelectionMode.Single,
                    SelectionContext.StrategyContext.StrategySpecifier.Click
                )

                fun toMultiClick() = SelectionModel(
                    SelectionMode.Multi,
                    SelectionContext.StrategyContext.StrategySpecifier.Click
                )

                fun toSingleCheckbox() = SelectionModel(
                    SelectionMode.Single,
                    SelectionContext.StrategyContext.StrategySpecifier.Checkbox
                )

                fun toMultiCheckbox() = SelectionModel(
                    SelectionMode.Multi,
                    SelectionContext.StrategyContext.StrategySpecifier.Checkbox
                )
            }

            val selectionStore = storeOf(
                SelectionModel(
                    SelectionMode.Single,
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
            val tdStyle: BoxParams.(Boolean) -> Unit = { selected ->
                background { color { tertiary.highlight } }
                color { tertiary.mainContrast }
                padding { normal }
                border {
                    width { thin }
                    style { solid }
                    color { tertiary.mainContrast }
                }
                textAlign { center }
                fontSize { large }
                if (selected) {
                    background { color { secondary.main } }
                    color { secondary.mainContrast }
                }
            }

            fun RenderContext.renderCell(selected: Boolean, model: SelectionModel, rowSpan: Int = 1) {
                td({
                    tdStyle(selected)
                }) {
                    if (rowSpan > 1) {
                        rowSpan(2)
                    }
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
                        th(thStyle) { +"multi" }
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
                            renderCell(
                                it.mode == SelectionMode.None,
                                selectionStore.current.copy(mode = SelectionMode.None),
                                2
                            )
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
                    SelectionMode.Single ->
                        renderDataTableSelectionSingle(
                            selectedPerson,
                            it.strategy
                        )
                    SelectionMode.Multi ->
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
                    selection = selectedPerson // or selectedPersons
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

        showcaseSubSection("Double Click")
        paragraph {
            +"Besides the former mechanisms to select rows, there is always the possibility to react to a double click "
            +"onto one row."
        }
        paragraph {
            +"The "
            c("dbClicks")
            +" event is exposed within the "
            c("events")
            +" context of the "
            +"DataTable"
            +" component."
        }
        componentFrame {
            val selectedPerson = storeOf<Person?>(null)
            dataTable(rows = storeOf(persons.take(8)), rowIdProvider = Person::id) {
                events {
                    dbClicks handledBy selectedPerson.update
                }
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
                                    +"nothing selected..."
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
        highlight {
            source(
                """
                // create a store 
                val selectedPerson = storeOf<Person?>(null)
                
                dataTable(rows = storeOf(persons), rowIdProvider = Person::id) {
                    events {
                        // use specific event that offers a ``Flow<T>``                  
                        dbClicks handledBy selectedPerson.update
                    }                
                    columns {
                        // omitted...
                    }
                }                    
                """
            )
        }

        showcaseSection("Formatting")
        paragraph {
            +"In order to adapt the appearance of the table to the content, the "
            +"DataTable"
            +" offers some configuration "
            +"possibilities."
        }
        paragraph {
            +"Within this section the following use cases are explained:"
            ul {
                li { +"Control maximum height and scrolling" }
                li { +"Adjust column size to expected content" }
                li { +"Apply custom formats via lenses" }
                li { +"Combine model properties into one column" }
            }
        }
        paragraph {
            +"In order to demonstrate those features, the data model gets enhanced with richer types and an "
            +"additional collection based property: Programming languages!"
        }
        highlight {
            source(
                """
                @Lenses
                data class Person(
                    val id: Int,
                    val firstname: String,
                    val lastname: String,
                    val birthday: LocalDate, // richer type for Date from kotlinx-datetime
                    val languages: List<String>
                ) {
                    // combined property for the name                
                    val name = "${'$'}firstname ${'$'}lastname"
                }                    
                """
            )
        }
        paragraph {
            +"The following table rendering shows the final result. Afterwards the separate configuration steps are "
            +"explained one by one:"
        }

        componentFrame {
            val fixedHeaderToggle = storeOf(true)
            val scrollingToggle = storeOf(true)
            val personStore = storeOf(finalPersons.take(25))

            stackUp {
                spacing { normal }
                items {
                    lineUp {
                        items {
                            switch(value = scrollingToggle) {
                                label("Enable Scrolling (set maximum height)")
                            }
                            scrollingToggle.data.render {
                                switch(value = fixedHeaderToggle) {
                                    enabled(it)
                                    label("Fix Header for Scrolling")
                                }
                            }
                        }
                    }
                    scrollingToggle.data.combine(fixedHeaderToggle.data) { scrolling, header -> scrolling to header }
                        .render { (scrolling, fixedHeader) ->
                            dataTable(rows = personStore, rowIdProvider = FinalPerson::id) {
                                options {
                                    if (scrolling) {
                                        maxHeight("35vh")
                                    } else {
                                        maxHeight("auto")
                                    }
                                }
                                header {
                                    fixedHeader(fixedHeader)
                                }
                                columns {
                                    column(title = "Id") {
                                        lens(L.FinalPerson.id.asString())
                                        width { minmax("70px") }
                                    }
                                    column(title = "Name") {
                                        width { minmax("2fr") }
                                        content { (_, state), _, _ ->
                                            +state.item.name
                                        }
                                        sorting { disabled }
                                    }
                                    column(title = "Birthday") {
                                        lens(L.FinalPerson.birthday + dateFormat)
                                        sorting { disabled }
                                    }
                                    column(title = "Programming Languages") {
                                        width { minmax("3fr") }
                                        content { (_, state), _, _ ->
                                            state.item.languages.forEach {
                                                span({
                                                    background { color { "#9EF01A" } }
                                                    radius { "1rem" }
                                                    paddings { horizontal { smaller } }
                                                    margins { right { tiny } }
                                                }) { +it }
                                            }
                                        }
                                        sorting { disabled }
                                    }
                                }
                            }
                        }
                }
            }
        }

        showcaseSubSection("Scrolling")
        paragraph {
            +"Scrolling is automatically activated, if a "
            em { +"maximum" }
            +" size is set via the "
            c("maxHeight")
            +" property within the "
            c("options")
            +" context. It is recommended to provide a viewport percentage by the "
            c("vh")
            +" unit! The default value ist set to "
            c("97vh")
            +" automatically."
        }
        coloredBox(Theme().colors.warning) {
            +"Currently there is no way to disable the scrolling via our DataTable configuration. "
            +"But this can be achieved by setting "
            c("maxHeight")
            +" property to "
            c("auto")
            +"."
            highlight {
                source(
                    """
                    dataTable(rows = personStore, rowIdProvider = Person::id) {
                        options {
                            maxHeight("auto")
                        }                        
                        // omitted                        
                    }                        
                    """.trimIndent()
                )
            }
            +"This missing feature should be added in future versions! "
            +"See "
            externalLink("Issue #376", "https://github.com/jwstegemann/fritz2/issues/376")
        }
        paragraph {
            +"If a table supports scrolling by default the header row behaves sticky. To disable this behaviour, "
            +"one can set the "
            c("fixedHeader")
            +" property of the "
            c("header")
            +" context to "
            c("false")
            +"."
        }
        highlight {
            source(
                """
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    options {
                        // apply 35% of the vertical space to the table
                        // if rows exceeds space, vertical scrollbar appears
                        maxHeight("35vh")
                    }
                    header {
                        fixedHeader(false) // default ``true``
                    }
                    columns {
                        // omitted...
                    } 
                }
                """
            )
        }

        showcaseSubSection("Column Size")
        paragraph {
            +"In order to reflect the data of different cells, it is possible to define the space a column takes. "
            +"Best practise is to think in "
            em { +"fractions" }
            +" of the whole space available."
        }
        paragraph {
            +"For columns with rather good maximum or even constant width, you can also apply fixed units like pixels."
        }
        highlight {
            source(
                """
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    columns {
                        // all "noise" omitted to focus on size specific options 
                        column(title = "Id") {
                            // Ids won't exceed some amount of digits -> constant width
                            width { minmax("70px") }
                        }
                        column(title = "Name") {
                            // concatenated names takes more space,
                            // so 2 fractions of whole space
                            width { minmax("2fr") }
                        }
                        column(title = "Birthday") {
                            // nothing declared -> 1 fraction as default                        
                        }
                        column(title = "Programming Languages") {
                            // concatenated languages needs even longer space
                            width { minmax("3fr") }
                        }
                    }
                }
                """
            )
        }

        showcaseSubSection("Type Conversion")
        paragraph {
            +"In order to render the content of a cell, the "
            +"DataTable"
            +" needs a "
            c("Lens<T, String>")
            +" that is a conversion from an arbitrary type into a string."
        }
        paragraph {
            +"If the model uses another type than a "
            c("String")
            +", you must provide a "
            c("Lens<T, String>")
            +" that integrates the formatting (and parsing). "
            +"Have a look at the "
            externalLink(
                "documentation",
                "https://docs.fritz2.dev/Format.html"
            )
            +" for further details."
        }
        highlight {
            source(
                """
                // create a formatting Lens for mapping Dates to Strings and vice versa 
                val dateFormat: Lens<LocalDate, String> = format(
                    parse = { LocalDate.parse(it) },
                    format = {
                        "${'$'}{it.dayOfMonth.toString().padStart(2, '0')}.${'$'}{
                            it.monthNumber.toString().padStart(2, '0')
                        }.${'$'}{it.year}"
                    }
                )  
                                  
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    columns {
                        column(title = "Birthday") {
                            // apply formatting lens to Date based lens
                            lens(L.Person.birthday + dateFormat)
                        }                    
                        // omitted...
                    } 
                }                                  
                """
            )
        }

        showcaseSubSection("Custom Content")
        paragraph {
            +"To override the default "
            c("Lens<T, String>")
            +" based rendering, you can specify the whole render method within the "
            c("content")
            +" property of the "
            c("column")
            +" context."
        }
        paragraph {
            +"In order to get access to the raw, typed data, there are some parameters injected into the expression "
            +"by the "
            +"DataTable"
            +":"
            ul {
                li {
                    c("IndexedValue<StatefulItem<T>>")
                    +": The index of the row and the item combined with some state information (sorting activated?, "
                    +"is row selected?). We will use this parameter here."
                }
                li {
                    c("Lens<T, String>")
                    +": The provided lens. The default implementation uses this."
                }
                li {
                    c("SubStore<T>")
                    +": A substore of the whole data store with exactly the current row inside. This is useful for "
                    +"forms inside a cell, like an "
                    c("inputField")
                    +" for examples, that allows to change the content of a cell in place."
                }
            }
        }
        highlight {
            source(
                """
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    columns {
                        column(title = "Name") {
                            // declare custom content
                            content { (_, state), _, _ -> // access StatefulItem<T>
                                // refer to the model's name property
                                +state.item.name
                            }
                        }
                        column(title = "Programming Languages") {
                            content { (_, state), _, _ ->
                                state.item.languages.forEach {
                                    // create pile for each language
                                    span({
                                        background { color { "#9EF01A" } }
                                        radius { "1rem" }
                                        paddings { horizontal { smaller } }
                                        margins { right { tiny } }
                                    }) { +it }
                                }
                            }                            
                        }
                        // omitted...
                    } 
                }                                  
                """
            )
        }

        showcaseSection("Sorting")
        paragraph {
            +"As already shown the "
            +"DataTable"
            +" offers an automatic sorting functionality, that can be quite easily "
            +"configured to fit..."
            ul {
                li {
                    +"... none "
                    c("String")
                    +" types"
                }
                li {
                    +"... modified cell content like combined model properties"
                }
            }
        }
        paragraph {
            +"The default sorting - as the content rendering - ist based upon the "
            c("Lens<T, String>")
            +", which is used by the table core to get a string based representation of the data for applying "
            +"the standard lexicographic sorting."
        }
        paragraph {
            +"In the former section about "
            em { +"formatting" }
            +" the sorting has been disabled for columns with special types and custom content for good reason: "
            +" Lexicographic sorting does not make sense for human readable dates for example."
        }
        paragraph {
            +"Within this section the missing sorting possibilities will be reintroduced."
            ul {
                li {
                    +"Sorting for "
                    c("id")
                    +" will be disabled"
                }
                li {
                    c("name")
                    +" will be sorted first by the "
                    c("lastname")
                    +" and then by the "
                    c("firstname")
                    +" if two equal lastnames are found (which is at least once the case for Id 1 and 8!)"
                }
                li {
                    c("birthday")
                    +" will be sorted by the sorting implementation of the "
                    c("LocalDate")
                    +" type"
                }
                li {
                    c("languages")
                    +" will be sorted by size of the list"
                }
            }
        }
        paragraph {
            +"The following table configures those needed sorting options but allows also to disable those to make"
            +" the different behaviours visible and easier to grasp:"
        }

        componentFrame {
            val applySpecificSorting = storeOf(true)
            val personStore = storeOf(
                finalPersons.take(7) + finalPersons.take(1).map { p ->
                    p.copy(id = 8, firstname = "A. ${p.firstname}")
                })

            stackUp {
                spacing { normal }
                items {
                    switch(value = applySpecificSorting) {
                        label("Apply specific sorting configuration")
                    }
                    applySpecificSorting.data.render { applySorting ->
                        dataTable(rows = personStore, rowIdProvider = FinalPerson::id) {
                            options {
                                maxHeight("40vh")
                            }
                            columns {
                                column(title = "Id") {
                                    lens(L.FinalPerson.id.asString())
                                    width { minmax("70px") }
                                    sorting { disabled }
                                }
                                column(title = "Name") {
                                    width { minmax("2fr") }
                                    content { (_, state), _, _ ->
                                        +state.item.name
                                    }
                                    if (applySorting) {
                                        sortBy(FinalPerson::lastname, FinalPerson::firstname)
                                    }
                                }
                                column(title = "Birthday") {
                                    lens(L.FinalPerson.birthday + dateFormat)
                                    if (applySorting) {
                                        sortBy(FinalPerson::birthday)
                                    }
                                }
                                column(title = "Programming Languages") {
                                    width { minmax("3fr") }
                                    content { (_, state), _, _ ->
                                        state.item.languages.forEach {
                                            span({
                                                background { color { "#9EF01A" } }
                                                radius { "1rem" }
                                                paddings { horizontal { smaller } }
                                                margins { right { tiny } }
                                            }) { +it }
                                        }
                                    }
                                    if (applySorting) {
                                        sortBy(compareBy { person ->
                                            person.languages.size
                                        })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    columns {
                        // content and other noise omitted!
                        column(title = "Id") {
                            lens(L.Person.id.asString())
                            // preselect default strategy (``asc``, ``desc`` or ``none`` )
                            // or disable sorting at all
                            sorting { disabled }
                        }
                        column(title = "Name") {                                                
                            // provide arbitrary ``(T) -> Comparable<*>``
                            // each one is applied from first to last if
                            // comparison results in ``0``
                            sortBy(Person::lastname, Person::firstname)
                        }
                        column(title = "Birthday") {
                            // same as above; ``birthday`` is ``LocalDate``
                            // -> Comparable implementation of specific type is used!
                            sortBy(Person::birthday)
                        }
                        column(title = "Programming Languages") {
                            // provide a custom comparator for full flexibility
                            sortBy(compareBy<Person> { person ->
                                person.languages.size
                            })
                        }
                    } 
                }                                  
                """
            )
        }
        coloredBox(Theme().colors.info) {
            +"There are much more options to customize the sorting in a deeper way, for example change the UI inside "
            +"the column headers or even change the sorting at all. It might be useful to have a multi column "
            +"selection for some use cases, that is, sort by first marked column, then by second one and so on. "
            paragraph {
                +"This is already possible to realize, but will be described in later sections!"
            }
        }

        showcaseSection("Styling")
        paragraph {
            +"There are many possibilities to apply custom styling to the DataTable: "
            ul {
                li { +"changing the DataTable section within your theme" }
                li { +"changing the header columns all at once within the declaration" }
                li { +"changing the header columns individually within the declaration of a column" }
                li { +"changing the columns all at once within the declaration of columns" }
                li { +"changing the columns individually within the declaration of one column" }
            }
        }
        paragraph {
            +"All the styling within the DSL is done as usual within our components: As first parameter of the "
            +"corresponding context opening function as the following snippet shows."
        }
        highlight {
            source(
                """
                dataTable(rows = storeOf(persons), rowIdProvider = Person::id) {
                    header({
                        // style all header columns at once
                    }) {
                        // omitted
                    }
                    columns({
                        // style all columns at once
                    }) {
                        column(styling = {
                            // style one column specifically
                        } title = "Id") {
                            header({
                                // style only the header of this column
                            }) {
                                // omitted
                            }
                            // omitted
                        }
                    }
                }                    
                """
            )
        }
        paragraph {
            +"Depending on the context level, additional information is injected into the styling expression, so "
            +"one can access the "
            c("index")
            +" of the row, the content as "
            c("T")
            +" or even stateful information like whether the row is currently selected or the column is sorted at "
            +" the moment."
        }

        showcaseSubSection("Styling Example")
        paragraph {
            +"Let's assume the following analytics use case:"
        }

        fun RenderContext.colorBox(text: String, color: ColorProperty) {
            flexBox({
                background { color { color } }
                width(sm = { "80px" }, md = { "110px" }, lg = { "150px" })
                height { "50px" }
                justifyContent { center }
                alignItems { center }
                margin { smaller }
                radius { small }
            }) { +text }
        }

        paragraph {
            +"Each of the available programming languages is assigned a level of supporting "
            em { +"functional programming" }
            +" paradigm. We consider three categories:"

            div({ margins { all { normal } } }) {
                colorBox("Ultimate", "#AED9E0")
                colorBox("Advanced", "#B8F2E6")
                colorBox("Basic", "#FAF3DD")
            }

            +"To make it easier to analyse which knowledge level each person has, we would like to colorize the "
            +"rows according to the above categories. (The sorting of the languages column is analogous changed, so "
            +"that one can group this also by sorting)"
        }
        componentFrame {
            val personStore = storeOf(finalPersons.take(30))

            dataTable(rows = personStore, rowIdProvider = FinalPerson::id) {
                options {
                    maxHeight("35vh")
                }
                columns({ (_, state) ->
                    background {
                        color {
                            when (state.item.functionalSkill()) {
                                Functionality.ULTIMATE -> "#AED9E0"
                                Functionality.ADVANCED -> "#B8F2E6"
                                Functionality.BASIC -> "#FAF3DD"
                            }
                        }
                    }
                    border {
                        style { solid }
                        width { thin }
                        color { neutral.main }
                    }
                }) {
                    column(title = "Id") {
                        lens(L.FinalPerson.id.asString())
                        width { minmax("70px") }
                        sorting { disabled }
                    }
                    column(title = "Name") {
                        width { minmax("2fr") }
                        content { (_, state), _, _ ->
                            +state.item.name
                        }
                        sortBy(FinalPerson::lastname, FinalPerson::firstname)
                    }
                    column(title = "Birthday") {
                        lens(L.FinalPerson.birthday + dateFormat)
                        sortBy(FinalPerson::birthday)
                    }
                    column(title = "Programming Languages") {
                        width { minmax("3fr") }
                        content { (_, state), _, _ ->
                            state.item.languages.forEach {
                                span({
                                    background { color { "#9EF01A" } }
                                    radius { "1rem" }
                                    paddings { horizontal { smaller } }
                                    margins { right { tiny } }
                                }) { +it }
                            }
                        }
                        sortBy(compareBy { person ->
                            person.functionalSkill()
                        })
                    }
                }
            }
        }
        highlight {
            source(
                """
                enum class Functionality {
                    BASIC, ADVANCED, ULTIMATE
                }
                
                val categories = mapOf(
                    "Python" to Functionality.BASIC,
                    "Java" to Functionality.BASIC,
                    "Kotlin" to Functionality.BASIC,
                    "Rust" to Functionality.BASIC,
                    "Clojure" to Functionality.ADVANCED,
                    "Scala" to Functionality.ADVANCED,
                    "Frege" to Functionality.ULTIMATE,
                )
                
                // just pick "best" functional language
                fun Person.functionalSkill() =
                    languages.mapNotNull { categories[it] }.maxOf { it }
                
                dataTable(rows = personStore, rowIdProvider = Person::id) {
                    columns({ (_, state) -> // access state with ``Person`` inside
                        background {
                            color {
                                // choose bgcolor by functional category
                                when (state.item.functionalSkill()) {
                                    Functionality.BASIC -> "#AED9E0"
                                    Functionality.ADVANCED -> "#B8F2E6"
                                    Functionality.ULTIMATE -> "#FAF3DD"
                                }
                            }
                        }
                    }) {
                        // noise omitted!
                        column(title = "Programming Languages") {
                            sortBy(compareBy<Person> { person ->
                                // adapt sorting
                                person.functionalSkill()
                            })        
                        }
                    } 
                }                                                      
                """
            )
        }

        showcaseSection("Advanced Topics")

        fun RenderContext.pill(content: Span.() -> Unit): Span {
            return span({
                display { inlineFlex }
                alignItems { center }
                background { color { "#9EF01A" } }
                color { neutral.mainContrast }
                radius { "1rem" }
                paddings { horizontal { smaller } }
                margins {
                    right { tiny }
                    top { tiny }
                }
            }) {
                content()
            }
        }

        showcaseSection("Editable Content")
        paragraph {
            +"In order to make the content of da DataTable editable, there are arbitrary possibilities, as the "
            +"component itself provides no built-in functionality and therefore there are no constraints imposed "
            +"on the client."
        }
        paragraph {
            +"Nevertheless there are two approaches we want to describe here:"
            ul {
                li { +"Edit columns in place" }
                li { +"Use a dedicated action to edit a single row" }
            }
        }

        showcaseSubSection("Edit Columns in place")
        paragraph {
            +"Imagine simple "
            c("inputField")
            +"s and some custom "
            em { +"pill" }
            +" based mechanism in combination with "
            c("selectField")
            +" in order to edit the content of a DataTable."
        }
        paragraph {
            +"The key idea is to use the exposed "
            c("SubStore")
            +"s of the "
            c("column")
            +" context's "
            c("content")
            +" function. Those stores with a single "
            c("T")
            +" of the whole data rows represented by a "
            c("List<T>")
            +" can be further "
            em { +"substored" }
            +" in order to create a dedicated store for one form element. "
            +"The following snippet shows this approach:"
        }
        highlight {
            source(
                """
                column(title = "Firstname") {
                    // define a custom content to render some form
                    content { _, _, row -> // access the store for this specific row
                        // create a form element and pass a *fitting* sub-store
                        // so the changes can be directly handled by the form itself
                        inputField(value = row.sub(L.Person.firstname)) {
                            placeholder("firstname")
                        }
                    }
                }                    
                """
            )
        }
        paragraph {
            +"The following table shows this concept and is explained afterwards. "
            +"The rows can be selected in order to demonstrate, that the changes to the data are reflected by the "
            +"selection:"
        }
        componentFrame {
            val personStore = storeOf(finalPersons.take(8))
            val selectedPerson = storeOf<FinalPerson?>(null)

            dataTable(
                rows = personStore,
                rowIdProvider = FinalPerson::id,
                selection = selectedPerson
            ) {
                selection { strategy { checkbox } }
                options {
                    maxHeight("50vh")
                }
                columns {
                    column(title = "Id") {
                        lens(L.FinalPerson.id.asString())
                        width { minmax("70px") }
                        sorting { disabled }
                    }
                    column(title = "Firstname") {
                        lens(L.FinalPerson.firstname)
                        content { _, _, row ->
                            val firstname = row.sub(L.FinalPerson.firstname)
                            inputField(value = firstname) {
                                placeholder("firstname")
                                size { small }
                            }
                        }
                    }
                    column(title = "Lastname") {
                        lens(L.FinalPerson.lastname)
                        content { _, _, row ->
                            val lastname = row.sub(L.FinalPerson.lastname)
                            inputField(value = lastname) {
                                placeholder("lastname")
                                size { small }
                            }
                        }
                    }
                    column(title = "Programming Languages") {
                        width { minmax("3fr") }
                        content { _, _, row ->
                            val editToggle = storeOf(false)
                            val languages = row.sub(L.FinalPerson.languages)
                            val addLanguage = languages.handle<String> { langs, new ->
                                langs + new
                            }
                            val dropLanguage = languages.handle<String> { langs, dropped ->
                                langs - dropped
                            }
                            editToggle.data.render { editable ->
                                if (editable && row.current.languages.size < categories.size) {
                                    selectField(items = categories.keys.subtract(row.current.languages).toList()) {
                                        size { small }
                                        events {
                                            selected handledBy addLanguage
                                            selected.map { false } handledBy editToggle.update
                                        }
                                    }
                                } else {
                                    span {
                                        languages.data.renderEach { language ->
                                            pill {
                                                +language
                                                icon({
                                                    size { "1rem" }
                                                }) { fromTheme { close } }
                                                clicks.stopImmediatePropagation()
                                                    .map { language } handledBy dropLanguage
                                            }
                                        }
                                    }
                                }
                            }
                            clicks.map { true } handledBy editToggle.update
                        }
                        sortBy(compareBy { person ->
                            person.languages.size
                        })
                    }
                }
            }
            lineUp({
                alignItems { end }
            }) {
                items {
                    storeContentBox("Selected") {
                        selectedPerson.data.render {
                            span {
                                if (it == null) {
                                    +"nothing selected..."
                                } else {
                                    +"${it.firstname} ${it.lastname} {${it.languages.joinToString(", ")}}"
                                }
                            }
                        }
                    }
                    clickButton { text("Clear selection") }.map { null } handledBy selectedPerson.update
                }
            }
        }
        highlight {
            source(
                """
                // noise omitted (datatable structure, styling, some content)
                column(title = "Id") { }
                column(title = "Firstname") {
                    content { _, _, row ->
                        inputField(value = row.sub(L.Person.firstname)) {
                            placeholder("firstname")
                        }
                    }
                }
                column(title = "Lastname") {
                    // just like firstname above!
                }
                column(title = "Programming Languages") {
                    content { _, _, row ->
                        // create a local state to decide if the languages pills 
                        // or the ``selectField`` should be rendered
                        val editToggle = storeOf(false)
                        
                        // create sub-store for languages list to
                        // enrich the sub-store with expressive custom handlers
                        // to make event configuration more readable later on
                        val languages = row.sub(L.Person.languages)
                        val addLanguage = languages.handle<String> { languages, new ->
                            languages + new
                        }
                        val dropLanguage = languages.handle<String> { languages, dropped ->
                            languages - dropped
                        }
                        
                        editToggle.data.render { editable ->
                            // render ``selectField`` for new language to add
                            if (editable && row.current.languages.size < categories.size) {
                                selectField(items = /* all missing languages */) {
                                    events {
                                        // tie new language to custom "add" handler
                                        selected handledBy addLanguage
                                        // switch to view mode
                                        selected.map { false } handledBy editToggle.update                                        
                                    }
                                }
                            } else {
                                // render the existing languages
                                span {
                                    languages.data.renderEach { language ->
                                        // use a custom ``pill`` "component" (code omitted)
                                        pill {
                                            +language
                                            // tie language to custom "drop" handler
                                            // and prevent switch to edit mode
                                            clicks.stopImmediatePropagation()
                                                .map { language } handledBy dropLanguage
                                        }
                                    }
                                }
                            }
                        }
                        clicks.map { true } handledBy editToggle.update
                    }
                }
                """
            )
        }

        showcaseSubSection("Edit by dedicated Action")
        paragraph {
            +"As second scenario we consider an overall readonly table with special CRUD action buttons inside "
            +"each row to activate the "
            em { +"draft" }
            +" mode for one column at once. The changed data row can then be saved or the changes can be reverted "
            +" with another action."
        }
        paragraph {
            +"The core idea with this approach is to embed the additional needed "
            em { +"state" }
            +" directly into the model. In this case there is a model based upon a "
            c("Person")
            +" and each person object has an optional counterpart: Another person that represents the "
            em { +"draft" }
            +". So instead of having a "
            c("List<Person>")
            +" as model, we create an UI-logic tailored model "
            c("DraftablePerson")
            +" that consists of two individual person objects for each state:"
            ul {
                li { +"stable" }
                li { +"draft" }
            }
        }
        highlight {
            source(
                """
                @Lenses
                data class DraftablePerson(
                    val stable: Person, // the "core" data
                    val draft: Person // the editable data part
                ) {
                    val isDrafted = stable.id == draft.id
                
                    // helper factories to convert to new state 
                    fun drafted() = DraftablePerson(stable, stable)
                    fun committed() = DraftablePerson(draft, FinalPerson.emptyPerson())
                    fun resetted() = DraftablePerson(stable, FinalPerson.emptyPerson())
                }                    
                """
            )
        }
        paragraph {
            +"Based upon this model can gather the following information inside each rendered column of a row:"
            ul {
                li {
                    +"Decide whether this row is "
                    em { +"stable" }
                    +" or a "
                    em { +"draft" }
                }
                li { +"create sub-stores for changing the draft object with form elements." }
            }
            +"So for all rendering inside a column for the readonly parts, we refer to "
            c("stable")
            +" property and for all editable content to "
            c("draft")
            +" property."
        }
        paragraph {
            +"The following table shows the result including the functionality to remove a person and to add a new one."
            +"The currently drafted person is shown below in order to make the changes during the editing visible:"
        }

        componentFrame {
            val persons = finalPersons.take(8).map { DraftablePerson(it, FinalPerson.emptyPerson()) }
            val personsStore = object : RootStore<List<DraftablePerson>>(persons) {

                val existDraft = data.map { person -> person.any { it.isDrafted } }

                val drop = handle<DraftablePerson> { persons, personToDrop ->
                    persons - personToDrop
                }
                val add = handle<DraftablePerson> { persons, personToAdd ->
                    persons + personToAdd
                }

                val updatePerson = handle<DraftablePerson> { persons, changedPerson ->
                    persons.map {
                        if (it.stable.id == changedPerson.stable.id) changedPerson
                        else it
                    }
                }

                val createPerson = handle { persons ->
                    val new = FinalPerson
                        .emptyPerson()
                        .copy(id = current.maxOf { it.stable.id } + 1)
                    persons + DraftablePerson(new, new)
                }
            }

            dataTable(rows = personsStore, rowIdProvider = { it.stable.id }) {
                selection { strategy { checkbox } }
                options {
                    maxHeight("50vh")
                }
                columns {
                    column(title = "Id") {
                        lens(L.DraftablePerson.stable + L.FinalPerson.id.asString())
                        width { minmax("70px") }
                        sorting { disabled }
                    }
                    column(title = "Firstname") {
                        lens(L.DraftablePerson.stable + L.FinalPerson.firstname)
                        content { (_, state), _, row ->
                            if (state.item.isDrafted) {
                                val firstname = row.sub(L.DraftablePerson.draft + L.FinalPerson.firstname)
                                inputField(value = firstname) {
                                    placeholder("firstname")
                                    size { small }
                                }
                            } else {
                                +state.item.stable.firstname
                            }
                        }
                    }
                    column(title = "Lastname") {
                        lens(L.DraftablePerson.stable + L.FinalPerson.lastname)
                        content { (_, state), _, row ->
                            if (state.item.isDrafted) {
                                val lastname = row.sub(L.DraftablePerson.draft + L.FinalPerson.lastname)
                                inputField(value = lastname) {
                                    placeholder("lastname")
                                    size { small }
                                }
                            } else {
                                +state.item.stable.lastname
                            }
                        }
                    }
                    column(title = "Programming Languages") {
                        width { minmax("3fr") }
                        content { (_, state), _, row ->
                            if (state.item.isDrafted) {
                                val editToggle = storeOf(false)
                                editToggle.data.render { editable ->
                                    val languages = row.sub(L.DraftablePerson.draft + L.FinalPerson.languages)
                                    val addLanguage = languages.handle<String> { langs, added ->
                                        langs + added
                                    }
                                    val removeLanguage = languages.handle<String> { langs, dropped ->
                                        langs - dropped
                                    }
                                    if (editable && state.item.draft.languages.size < categories.size) {
                                        selectField(
                                            items = categories.keys.subtract(state.item.draft.languages).toList()
                                        ) {
                                            size { small }
                                            events {
                                                selected handledBy addLanguage
                                            }
                                        }
                                    } else {
                                        state.item.draft.languages.forEach { language ->
                                            pill {
                                                +language
                                                icon({
                                                    size { "1rem" }
                                                }) { fromTheme { close } }
                                                clicks.map { language } handledBy removeLanguage
                                            }
                                        }
                                    }
                                }
                                clicks.map { true } handledBy editToggle.update
                            } else {
                                state.item.stable.languages.forEach { language ->
                                    pill { +language }
                                }
                            }
                        }
                        sortBy(compareBy { state ->
                            state.stable.languages.size
                        })
                    }
                    column(title = "CRUD Actions") {
                        width { minmax("10rem") }
                        sorting { disabled }
                        header({
                            this as FlexParams
                            display { inlineFlex }
                            justifyContent { center }
                        }) { column -> +column.title }
                        content { (_, state), _, row ->
                            lineUp({
                                justifyContent { center }
                            }) {
                                spacing { tiny }
                                items {
                                    clickButton {
                                        icon { remove }
                                        size { small }
                                    }.map { row.current } handledBy personsStore.drop
                                    if (state.item.isDrafted) {
                                        clickButton {
                                            icon { check }
                                            size { small }
                                        }.map { state.item.committed() } handledBy personsStore.updatePerson
                                        clickButton {
                                            icon { close }
                                            size { small }
                                        }.map { state.item.resetted() } handledBy personsStore.updatePerson
                                    } else {
                                        clickButton {
                                            icon { edit }
                                            size { small }
                                            disabled(personsStore.existDraft)
                                        }.map { state.item.drafted() } handledBy personsStore.updatePerson
                                    }
                                }
                            }
                        }
                    }
                }
            }
            lineUp({
                alignItems { end }
            }) {
                items {
                    storeContentBox("Draft") {
                        personsStore.data.map {
                            it.filter { person -> person.isDrafted }.map { it.draft }.firstOrNull()
                        }.render {
                            span {
                                if (it == null) {
                                    +"nothing drafted..."
                                } else {
                                    +"${it.firstname} ${it.lastname} {${it.languages.joinToString(", ")}}"
                                }
                            }
                        }
                    }
                    clickButton { icon { add } } handledBy personsStore.createPerson
                }
            }
        }
        paragraph {
            +"First of all let's discover the store concept of this solution:"
        }
        highlight {
            source(
                """
                val persons = // some List of DraftablePerson
                val personsStore = object : RootStore<List<DraftablePerson>>(persons) {
    
                    // needed to enable or disable edit-buttons
                    val existDraft = data.map { person -> person.any { it.isDrafted } }
    
                    val drop = handle<DraftablePerson> { persons, personToDrop ->
                        persons - personToDrop
                    }
                    val add = handle<DraftablePerson> { persons, personToAdd ->
                        persons + personToAdd
                    }
    
                    val updatePerson = handle<DraftablePerson> { persons, changedPerson ->
                        persons.map {
                            if (it.stable.id == changedPerson.stable.id) changedPerson
                            else it
                        }
                    }
    
                    val createPerson = handle { persons ->
                        val new = FinalPerson
                            .emptyPerson()
                            .copy(id = current.maxOf { it.stable.id } + 1)
                        // create new person a priori as "draft"
                        // -> so it gets rendered for editing right away!
                        persons + DraftablePerson(new, new) 
                    }
                }                    
                """
            )
        }
        paragraph {
            +"As the content would get a bit long and repitetive, we present each relevant column declaration "
            +"separately."
        }
        paragraph {
            +"The easiest declaration belongs to the name columns. We need to stateful information here:"
            ul {
                li {
                    +"the "
                    em { +"draft" }
                    +" state in order to render the read-only or respectively the editable content"
                }
                li {
                    +"a fitting sub-store of the name's property to pass this into the factory function of "
                    c("inputField")
                    +"."
                }
            }
        }
        highlight {
            source(
                """
                column(title = "Firstname") {
                    content { (_, state), _, row -> // access the item and the row-store
                        if (state.item.isDrafted) {
                            // render the editable version
                            
                            // create a fitting sub-store by combining lenses:
                            // 1.) refer to ``draft`` part of ``DraftablePerson`` object
                            // 2.) refer to the ``firstname`` property of a ``Person``
                            // -> created a ``Lens<DraftablePerson, String>``
                            // the sub-store refers to the ``draft`` only
                            // -> the stable part remains untouched!
                            // (so we can easily revert the changes later)
                            val firstname = row.sub(
                                L.DraftablePerson.draft + L.FinalPerson.firstname
                            )
                            
                            // let form itself handle the value changing automatically
                            inputField(value = firstname) {
                                placeholder("firstname")
                            }
                        } else {
                            // render the read-only version
                            +state.item.stable.firstname
                        }
                    }
                }                    
                """
            )
        }

        paragraph {
            +"The column of the languages just looks a bit more complex, but at the core the same mechanisms are "
            +"applied here as for the names: "
        }
        paragraph {
            +"At first there is a branching based upon the "
            c("draft")
            +" state as before, then the appropriate sub-store including proper handlers for manipulating the "
            +"languages list are defined."
        }
        paragraph {
            +"In order to switch between the language pills and the "
            c("selectField")
            +" for adding a new language, an additional boolean toggle store is declared. Based upon its state, "
            +"the tow cases gets rendered."
        }
        highlight {
            source(
                """
                column(title = "Programming Languages") {
                    content { (_, state), _, row ->
                        if (state.item.isDrafted) {
                            // render the editable version
                            
                            // need state to decide whether known
                            // languages or ``selectField`` for adding
                            // a new language should be rendered
                            val editToggle = storeOf(false)
                            editToggle.data.render { editable ->
                            
                                // create sub-store and enrich it with
                                // expressive specialized handlers
                                // for better readability
                                val languages = row.sub(
                                    L.DraftablePerson.draft + L.FinalPerson.languages
                                )
                                val addLanguage = languages.handle<String> { 
                                    langs, added -> langs + added
                                }
                                val removeLanguage = languages.handle<String> { 
                                    langs, dropped -> langs - dropped
                                }
                                
                                // evaluate second state:
                                if (editable && 
                                    state.item.draft.languages.size < categories.size) {
                                    // render select for new language
                                    selectField(items = /* all missing languages */) {
                                        events {
                                            selected handledBy addLanguage
                                        }
                                    }
                                } else {
                                    // render known languages with removable pill
                                    state.item.draft.languages.forEach { language ->
                                        pill {
                                            +language
                                            icon { fromTheme { close } }
                                            clicks.map { language } handledBy removeLanguage
                                        }
                                    }
                                }
                            }
                            // Remark: we just need to set it once to ``true``
                            // After the content has changed, the whole cell
                            // is newly rendered -> the initial state will
                            // again be set to ``false``!
                            clicks.map { true } handledBy editToggle.update
                            
                        } else {
                            // render the read-only version
                            state.item.stable.languages.forEach { language ->
                                pill { +language }
                            }
                        }
                    }
                }                    
                """.trimIndent()
            )
        }

        paragraph {
            +"Last but not least the CRUD buttons need to be defined. Nothing really new here besides the "
            +"expressive use of specialized handlers of the main store and the converter factories of the "
            c("DraftablePerson")
            +" model for creating a draft or committing it back as stable."
        }
        highlight {
            source(
                """
                column(title = "CRUD Actions") {
                    content { (_, state), _, row ->
                    
                        // always offer remove button
                        clickButton {
                            icon { remove }
                        }.map { row.current } handledBy personsStore.drop
                        
                        if (state.item.isDrafted) {
                            // Draft is active -> offer buttons to save or cancel
                            clickButton {
                                icon { check }
                            }.map { state.item.committed() } handledBy personsStore.updatePerson
                            clickButton {
                                icon { close }
                            }.map { state.item.resetted() } handledBy personsStore.updatePerson
                            
                        } else {
                            // No draft active -> offer edit button
                            clickButton {
                                icon { edit }
                                // use specialized flow to disable all other edit buttons
                                disabled(personsStore.existDraft)
                            }.map { state.item.drafted() } handledBy personsStore.updatePerson
                        }
                    }
                }                    
                """
            )
        }

    }
}

