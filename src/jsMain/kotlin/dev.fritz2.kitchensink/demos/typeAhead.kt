package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.typeAhead.Proposal
import dev.fritz2.components.typeAhead.asProposal
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.remote.getBody
import dev.fritz2.remote.http
import dev.fritz2.styling.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlin.js.Json


@ExperimentalCoroutinesApi
fun RenderContext.typeAheadDemo(): Div {

    return contentFrame {

        showcaseHeader("TypeAhead")

        paragraph {
            +"The TypeAhead component allows users to input a value and get some list of proposals to choose from "
            +" beneath the input field. This resembles a combination of an "
            internalLink("InputField", "InputField")
            +" and a "
            internalLink("SelectField", "SelectField")
            +"."
        }

        paragraph {
            +"This component should be used, if ..."
            ul {
                li {
                    +"... the amount of choices is too huge for other single selection components, that renders "
                    +"always all options like a SelectField or a RadioGroup."
                }
                li {
                    +"... the proposals should dynamically depend on the current user input, which is often "
                    +" useful for remote API calls."
                }
            }
        }

        coloredBox(Theme().colors.danger) {
            +"This component does not work reliable on Safari Browsers at the moment. "
            br {}
            +"See "
            externalLink("Issue 201121", "https://bugs.webkit.org/show_bug.cgi?id=201121")
            +" in the webkit issue tracker."
        }

        showcaseSection("Usage")
        paragraph {
            +"The factory function "
            c("typeAhead")
            +" needs at least one proposal generating function of type "
            c("Proposal")
            +" (which is an type alias for the function type "
            c("suspend (Flow<String>) -> Flow<List<String>>")
            +") as "
            c("item")
            +" parameter and a store for the result:"
        }

        val languages = listOf(
            "Kotlin", "Scala", "Java", "OCaml", "Haskell", "JavaScript", "Clojure",
            "F#", "C++", "C#", "Commodore Basic V2", "Scheme", "Befunge"
        )
        val proposals = languages.asProposal()
        componentFrame {
            val choice = storeOf("")
            typeAhead(value = choice, items = proposals) { }
            storeContentBox("Selected") {
                choice.data.render {
                    span { +it }
                }
            }
        }

        highlight {
            source(
                """
                // for extension function and type aliases
                import dev.fritz2.components.typeAhead.*
                
                val languages = listOf(
                    "Kotlin", "Scala", "Java", "OCaml", "Haskell", "JavaScript", "Clojure",
                    "F#", "C++", "C#", "Commodore Basic V2", "Scheme", "Befunge"
                )
                // apply extension function to create a `Proposal` from a `List<String>`
                val proposals = languages.asProposal()
    
                // create a store for the result
                val choice = storeOf("")
    
                typeAhead(value = choice, items = proposals) { }
                """
            )
        }

        showcaseSection("Strictness")
        paragraph {
            +"By setting the boolean "
            c("strict")
            +" property to "
            c("false")
            +" (default is "
            c("true")
            +") all input is accepted as result; so the proposals are just optionally and not mandatory any more:"
        }
        componentFrame {
            val choice = storeOf("")
            typeAhead(value = choice, items = proposals) {
                strict(false)
            }
            storeContentBox("Selected") {
                choice.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
                """
                // see first example for data setup!
                typeAhead(value = choice, items = proposals) { 
                    strict(false) // accept every input, not only matching items
                }
                """
            )
        }

        showcaseSection("Tune Proposals")
        paragraph {
            +"In order to reduce the amount of proposals there are two orthogonal concepts available through "
            +" properties:"
            ul {
                li {
                    c("limit(value)")
                    +": limit the proposals to the first "
                    c("value")
                    +" items. This might be useful for large proposal spaces. Default is "
                    c("20")
                    +"."
                }
                li {
                    c("draftThreshold(value)")
                    +": start the proposal function only if the input has at least "
                    c("value")
                    +" length. This might be useful if a longer draft before searching improves the selectivity. "
                    +"Default is "
                    c("1")
                    +"."
                }
            }
        }
        componentFrame {
            label {
                +"Try "
                c("a")
                +" that should only expose one result instead of multiple:"
            }
            val choiceWithLimit = storeOf("")
            typeAhead(value = choiceWithLimit, items = proposals) {
                limit(1)
            }
            storeContentBox("Selected") {
                choiceWithLimit.data.render {
                    span { +it }
                }
            }
            br {}
            label {
                +"Try to find"
                c("F#")
                +" - not possible to choose, so be cautious not to be too restrictive!"
            }
            val choiceWithThreshold = storeOf("")
            typeAhead(value = choiceWithThreshold, items = proposals) {
                draftThreshold(3)
            }
            storeContentBox("Selected") {
                choiceWithThreshold.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
                """
                // see first example for data setup!
                
                typeAhead(value = choice, items = proposals) {
                    limit(1) // just take one item from all found proposals
                }
            
                typeAhead(value = choice, items = proposals) {
                    // just start searching for proposals if input is >= 3 chars long
                    // -> thus it is impossible to select languages with only one or two chars!
                    draftThreshold(3) 
                }
                """
            )
        }

        paragraph {
            +"There is also the possibility to reduce the costs of searching for proposals (think of expensive "
            +"remote calls) by changing the "
            c("debounce(value)")
            +" timeout, which will make the component wait for "
            c("value")
            +" milliseconds (ms) after the last key stroke, before starting the proposal function. "
            +"Default is "
            c("250")
            +" ms, which is quite a good standard for users to fluently type in the first few chars of a guessed or "
            +"even known choice."
        }
        componentFrame {
            val timeout = storeOf(250)
            val choice = storeOf("")
            stackUp {
                items {
                    slider(value = timeout) {
                        range { upper(2000) }
                    }
                    lineUp({
                        width { full }
                        alignItems { center }
                    }) {
                        items {
                            timeout.data.render {
                                label({
                                    width { "15rem" }
                                }) { +"debounce = $it ms" }
                                typeAhead(value = choice, items = proposals) {
                                    draftThreshold(1) // for not getting any results at first call
                                    debounce(it.toLong())
                                }
                            }
                        }
                    }
                }
            }
            storeContentBox("Selected") {
                choice.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
                """
                // see first example for data setup!
                typeAhead(value = choice, items = proposals) { 
                    debounce(1000) // wait for one second; 250 ms is default
                }
                """
            )
        }

        showcaseSection("Patterns for Proposal Functions")
        paragraph {
            +"The most important aspect of using a TypeAhead is the proposal function. Therefore it is essential to "
            +"gather a solid understanding about its meaning and some common patterns that fit most use cases."
        }

        showcaseSubSection("Mapping Draft to create a List")
        paragraph {
            +"The proposal function is meant to react dynamically to the current user input called the "
            em { +"draft" }
            +" throughout the TypeAhead component. That is why the current draft is passed as parameter into the "
            +"proposal as the signature shows: "
            c("suspend (Flow<String>) -> Flow<List<String>>")
        }
        paragraph {
            +"Let us assume there is just a static collection of options we want to provide to the user, like a "
            c("List<String>")
            +" as common type:"
        }
        highlight {
            source(
                """
                val languages = listOf(
                    "Kotlin", "Scala", "Java", "OCaml", "Haskell", "JavaScript", "Clojure",
                    "F#", "C++", "C#", "Commodore Basic V2", "Scheme", "Befunge"
                )                    
                """.trimIndent()
            )
        }
        paragraph {
            +"On the way to create a valid proposal, we ignore the wrapping into a "
            c("Flow<>")
            +" at first and start by just using a draft parameter to filter the list by querying for containment:"
        }
        highlight {
            source(
                """
                // the component itself will inject the current `draft` as parameter 
                // into the passed function
                val filtered: (String) -> List<String> = { draft ->
                    languages.filter { language -> language.contains(draft) }
                }               
                """.trimIndent()
            )
        }
        paragraph {
            +"To lift this first approach up into a "
            c("Flow<T>")
            +" we need to "
            c("map")
            +" it:"
        }
        highlight {
            source(
                """
                val filtered: suspend (Flow<String>) -> Flow<List<String>> = {
                    it.map { draft -> 
                        languages.filter { language -> language.contains(draft) } 
                    }
                }
                
                // the above can be shortened by using the type alias `Proposal`
                import dev.fritz2.components.typeAhead.Proposal
                
                val filtered: Proposal = {
                    it.map { draft -> 
                        languages.filter { language -> language.contains(draft) } 
                    }
                }                
                """.trimIndent()
            )
        }
        coloredBox(Theme().colors.info) {
            +"By using typical "
            c("Flow")
            +" based operations on the injected draft parameter, we can create the desired "
            c("Flow<List<String>>")
            +"!"
            br {}
            +"If there is no other flow involved, just start by using "
            c("it.map {...}")
            +"."
        }
        paragraph {
            +"As a static list of items is quite common, there is a convenience extension function on "
            c("List<String>")
            +" as already showed in the very first example "
            c("asProposal()")
            +":"
        }
        highlight {
            source(
                """
                import dev.fritz2.components.typeAhead.asProposal
                val filtered = languages.asProposal()
                """.trimIndent()
            )
        }
        paragraph {
            +"Under the hood this function creates exactly the functional expression that we have developed within "
            +"this section!"
        }
        paragraph {
            +"There is only one aspect left to explore: This extension functions can be customized by passing a "
            +"factory function to create a filter predicate: "
            c("asProposal(predicate: (String) -> (String) -> Boolean)")
        }
        paragraph {
            +"To change the default behaviour (which is checking for containment as shown above!) like checking if "
            +"an item starts with the draft, the predicate factory can be passed like this:"
        }
        highlight {
            source(
                """
                import dev.fritz2.components.typeAhead.asProposal
                val filtered = languages.asProposal { draft -> 
                    // this predicate will be passed into the `filter`
                    // call on the list by the component
                    { language -> language.startsWith(draft) } 
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("Query Remote APIs")
        paragraph {
            +"As typical use case instead of using a static collection of options, we might want to query a remote "
            +"API in order to get dynamic options."
        }
        paragraph {
            +"Let's keep it simple here, so we just query the well known "
            externalLink("SWAPI", "https://swapi.dev/")
            +"!"
        }
        paragraph {
            +"We rely on the handy "
            externalLink("http API", "https://docs.fritz2.dev/HttpCalls.html")
            +" of fritz2 paired with some simple "
            externalLink("JSON parsing", "https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.js/-j-s-o-n/")
            +":"
        }

        val swapiHelper = object {
            private val baseApi = http("https://swapi.dev/api/")
                .acceptJson()
                .contentType("application/json")

            val getPersons: Proposal = {
                it.map { draft ->
                    val result = baseApi.get("people/?search=$draft").getBody()
                    buildList {
                        (JSON.parse<Json>(result)["results"]
                            .unsafeCast<Array<Json>>()).forEach { person ->
                                add(person["name"] as String)
                            }
                    }
                }
            }

            fun getData(category: Flow<Pair<String, String>>): Proposal = {
                it.combine(category) { draft, (fragment, prop) ->
                    val result = baseApi.get("$fragment/?search=$draft").getBody()
                    buildList {
                        (JSON.parse<Json>(result)["results"]
                            .unsafeCast<Array<Json>>()).forEach { item ->
                                add(item[prop] as String)
                            }
                    }
                }
            }
        }

        componentFrame {
            val choice = storeOf("")
            label {
                +"Try "
                c("Sky")
                +" for example..."
            }
            typeAhead(value = choice, items = swapiHelper.getPersons) {
            }
            storeContentBox("Selected") {
                choice.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
                """
                // encapsulate remote API
                object SwapiHelper {
                    // rely on fritz2's http wrapping:
                    private val baseApi = http("https://swapi.dev/api/")
                        .acceptJson()
                        .contentType("application/json")
        
                    // create a proposal function by applying the above presented
                    // pattern `it.map { ... }`
                    val getPersons: Proposal = {
                        it.map { draft ->
                            // fire `get` request and store result
                            val result = baseApi.get("people/?search=${'$'}draft").getBody()
                            buildList {
                                // simple (unsafe) JSON parsing
                                (JSON.parse<Json>(result)["results"]
                                    .unsafeCast<Array<Json>>()).forEach { person ->
                                        add(person["name"] as String)
                                    }
                            }
                        }
                    }
                }                    
                
                val choice = storeOf("")
                typeAhead(value = choice, items = SwapiHelper.getPersons) { }
                """
            )
        }

        showcaseSubSection("Combine Draft with other Flows")
        paragraph {
            +"Sometimes the valid proposals might depend on the current state of some other component too."
        }
        paragraph {
            +"As fritz2's components rely on the foundational store and therefore also flow concepts, it is in fact "
            +"quite easy to combine some external flow with the injected draft flow of the proposal function:"
        }
        componentFrame {
            val categories = listOf(
                "films" to "title",
                "people" to "name",
                "planets" to "name",
                "species" to "name",
                "starships" to "name",
                "vehicles" to "name"
            )
            val category = storeOf(categories.first(), "SwapiCategory")
            val choice = storeOf("", "SwapiChoice")
            stackUp {
                items {
                    formControl {
                        label("Category")
                        selectField(value = category, items = categories) {
                            label { (name, _) -> name }
                        }
                    }
                    category.data.render {
                        formControl {
                            label("Choose from ${it.first}")
                            typeAhead(value = choice, items = swapiHelper.getData(category.data)) {
                            }
                        }
                    }
                }
            }
            storeContentBox("Selected") {
                choice.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
                """                
                // model: we need a path fragment and a property name to query the API
                val categories = listOf(
                    "films" to "title", // title differs -> we need a Pair 
                    "people" to "name",
                    "planets" to "name",
                    "species" to "name",
                    "starships" to "name",
                    "vehicles" to "name"
                )
                
                // stores
                val category = storeOf(categories.first())
                val choice = storeOf("")
                
                // UI
                selectField(value = category, items = categories) {
                    label { (name, _) -> name }
                }
                category.data.render { label { +"Choose from ${'$'}{it.first}" } }                
                typeAhead(value = choice, items = SwapiHelper.getData(category.data)) { }
                //                                                    ^^^^^^^^^^^^^
                //                                                    pass additional flow
                
                // refactor former proposal to be more generic:  
                object SwapiHelper {
                    // pass additional Flow with 
                    // - category name for the API path
                    // - JSON property name for extracting its value
                    fun getData(category: Flow<Pair<String, String>>): Proposal = {
                        // mix both flows together: 
                        it.combine(category) { draft, (fragment, prop) ->
                            // use category as path fragment
                            val result = baseApi.get("${'$'}fragment/?search=${'$'}draft").getBody()
                            buildList {
                                (JSON.parse<Json>(result)["results"]
                                    .unsafeCast<Array<Json>>()).forEach { item ->
                                        // use property name for data extraction
                                        add(item[prop] as String)
                                    }
                            }
                        }
                    }                
                }                                    
                """
            )
        }
        coloredBox(Theme().colors.info) {
            +"Use any "
            c("Flow")
            +" operator function to combine the flows of other components with the proposal flow!"
            br {}
            +"Often "
            c("combine")
            +" is a good starting choice!"
        }

        paragraph {
            +"In fact the easy combination of other component's state with the proposal function has been driven the "
            +"design decision to choose the type signature of "
            c("Proposal")
            +" at it is!"
        }

        showcaseSection("Sizes")
        paragraph {
            +"Our TypeAhead component offers three predefined sizes: "
            c("small")
            +", "
            c("normal")
            +" (default), or "
            c("large")
        }

        componentFrame {
            stackUp {
                items {
                    val choice = storeOf("")
                    typeAhead(value = choice, items = proposals) {
                        size { small }
                    }
                    typeAhead(value = choice, items = proposals) {
                        // omitted, as `normal`` is default!
                    }
                    typeAhead(value = choice, items = proposals) {
                        size { large }
                    }
                }
            }
        }

        highlight {
            source(
                """
                typeAhead(value = choice, items = proposals) {
                    size { small}
                }
                typeAhead(value = choice, items = proposals) {
                    // omitted, as `normal`` is default!                
                }
                typeAhead(value = choice, items = proposals) {
                    size { large}
                }
                """
            )
        }

        showcaseSection("Variants")
        paragraph {
            +"You can choose between the variants "
            c("outline")
            +" or "
            c("filled")
            +" for the TypeAhead:"

        }
        componentFrame {
            stackUp {
                items {
                    val choice = storeOf("")
                    typeAhead(value = choice, items = proposals) {
                        // omitted, as outline is default!
                    }
                    typeAhead(value = choice, items = proposals) {
                        variant { filled }
                    }
                }
            }
        }

        highlight {
            source(
                """
                typeAhead(value = choice, items = proposals) {
                    // omitted, as outline is default!
                }
                typeAhead(value = choice, items = proposals) {
                    variant { filled }
                }
                """
            )
        }

        showcaseSection("Disabled and Readonly")
        paragraph {
            +"You can easily disable the component by using the "
            c("disabled")
            +" or "
            c("readonly")
            +" functions."
        }
        componentFrame {
            stackUp {
                items {
                    val choice = storeOf("")
                    val toggle = storeOf(true)
                    switch(value = toggle) {
                        label(toggle.data.map {
                            if (it) "disabled / readonly" else "enabled"
                        })
                    }
                    formControl {
                        label("Disabled")
                        typeAhead(value = choice, items = proposals) {
                            disabled(toggle.data)
                        }
                    }
                    formControl {
                        label("Readonly")
                        typeAhead(value = choice, items = proposals) {
                            readonly(toggle.data)
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                // Just to show the effect better, we can enable or disable the control
                val toggle = storeOf(true)
                switch(value = toggle) {
                    label(toggle.data.map {
                        if (it) "disabled / readonly" else "enabled"
                    })
                }
                
                typeAhead(value = choice, items = proposals) {
                    disabled(toggle.data)
                }
                typeAhead(value = choice, items = proposals) {
                    readonly(toggle.data)
                }                    
                """
            )
        }
    }
}