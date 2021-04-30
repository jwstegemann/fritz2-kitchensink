package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.datatable.DataTableComponent
import dev.fritz2.components.datatable.SelectionContext
import dev.fritz2.components.datatable.SelectionMode
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.datatable.*
import dev.fritz2.lenses.Lens
import dev.fritz2.lenses.asString
import dev.fritz2.lenses.format
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.ColorProperty
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
            c("dataTable")
            +" component provides a way to visualize tabular data and offers interaction for the user "
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
            +"In order to use the "
            c("dataTable")
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
            c("dataTable")
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
            c("dataTable")
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
                    dataTable(rows = personStore, rowIdProvider = FinalPerson::id) {
                        options {
                            maxHeight("auto")
                        }                        
                        // omitted                        
                    }                        
                    """.trimIndent()
                )
            }
            +"This missing feature should be added in future versions of DataTable component! "
            +"Watch out for "
            externalLink("Issue 376", "https://github.com/jwstegemann/fritz2/issues/376")
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
            c("dataTable")
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
                            lens(L.FinalPerson.birthday + dateFormat)
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
            c("dataTable")
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
                    +": A Substore of the whole data store with exactly this one row inside. This is useful for "
                    +"forms inside a cell, like a "
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
                                // refer to the special name property
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
            c("dataTable")
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
                                maxHeight("35vh")
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
                            lens(L.FinalPerson.id.asString())
                            // preselect default strategy (``asc``, ``desc`` or ``none`` )
                            // or disable sorting at all
                            sorting { disabled }
                        }
                        column(title = "Name") {                                                
                            // provide arbitrary ``(T) -> Comparable<*>``
                            // each one is applied from first to last if
                            // comparison results in ``0``
                            sortBy(FinalPerson::lastname, FinalPerson::firstname)
                        }
                        column(title = "Birthday") {
                            // same as above; ``birthday`` is ``LocalDate``
                            // -> Comparable implementation of specific type is used!
                            sortBy(FinalPerson::birthday)
                        }
                        column(title = "Programming Languages") {
                            // provide a custom comparator for full flexibility
                            sortBy(compareBy<FinalPerson> { person ->
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
            +"one can be access the "
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
                width (sm = { "80px" }, md = { "110px" }, lg = { "150px" })
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

            div({ margins { all { normal } }}) {
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
                                Functionality.BASIC -> "#AED9E0"
                                Functionality.ADVANCED -> "#B8F2E6"
                                Functionality.ULTIMATE -> "#FAF3DD"
                            }
                        }
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
                    "Scala" to Functionality.ADVANCED,
                    "Java" to Functionality.BASIC,
                    "Kotlin" to Functionality.BASIC,
                    "Rust" to Functionality.BASIC,
                    "Clojure" to Functionality.ADVANCED,
                    "Frege" to Functionality.ULTIMATE,
                )
                
                // just pick "best" functional language
                fun FinalPerson.functionalSkill() =
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
                            sortBy(compareBy<FinalPerson> { person ->
                                // adapt sorting
                                person.functionalSkill()
                            })        
                        }
                    } 
                }                                                      
                """
            )
        }
    }
}

