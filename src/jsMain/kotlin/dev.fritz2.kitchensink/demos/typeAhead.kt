package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.typeAhead.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


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
                    +" items. This might be useful for large proposal spaces."
                }
                li {
                    c("draftThreshold(value)")
                    +": start the proposal function only if the input has at least "
                    c("value")
                    +" length. This might be useful if a longer draft before searching improves the selectivity."
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

    }
}