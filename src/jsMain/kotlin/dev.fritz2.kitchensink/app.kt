package dev.fritz2.kitchensink

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.demos.*
import dev.fritz2.routing.router
import dev.fritz2.styling.*
import dev.fritz2.styling.theme.Theme
import dev.fritz2.styling.theme.render
import kotlinx.browser.window
import kotlinx.coroutines.ExperimentalCoroutinesApi

val themes = listOf<ExtendedTheme>(SmallFonts(), LargeFonts())

/* All component names must be singular */
const val welcome_ = "Welcome"
const val gettingStarted_ = "Getting Started"
const val spinner_ = "Spinner"
const val input_ = "InputField"
const val select_ = "SelectField"
const val buttons_ = "Button"
const val file_ = "File"
const val formcontrol_ = "FormControl"
const val flexbox_ = "FlexBox"
const val gridbox_ = "GridBox"
const val checkbox_ = "Checkbox"
const val radio_ = "RadioGroup"
const val switch_ = "Switch"
const val stack_ = "Stack"
const val dropdown_ = "Dropdown"
const val modal_ = "Modal"
const val popover_ = "Popover"
const val styling_ = "Styling"
const val theme_ = "Themes"
const val colors_ = "Colors"
const val icons_ = "Icons"
const val tooltip_ = "Tooltip"
const val responsive_ = "Responsiveness"
const val textarea_ = "TextArea"
const val alert_ = "Alert"
const val menu_ = "Menu"
const val toasts_ = "Toast"
const val datatable_ = "DataTable"
const val slider_ = "Slider"

val router = router(welcome_)

object ThemeStore : RootStore<Int>(0) {
    val selectTheme = handle<Int> { _, index ->
        Theme.use(themes[index])
        index
    }
}

const val highlightBackgroundColor = "#2b303b"

const val welcomeContentStaticCss = """
    background-image: url("background.jpg");
    background-repeat: no-repeat;
    background-attachment: fixed;
    background-size: cover;
"""

const val settingsTableStaticCss = """
        color: rgb(45, 55, 72);
        text-align: left;
        margin-top: 32px;
        width: 100%;
        border-collapse: collapse;
        
        & > tr > th {
            background: #F7FAFC;
            font-weight: 600;
            padding: 0.5rem;
            font-size: 0.875rem;
        }

        & > tr > td {
            padding: 0.5rem;
            border-top-width: 1px;
            border-color: gray200;
            font-size: 0.875rem;
            white-space: normal;
        }
"""

@ExperimentalCoroutinesApi
fun main() {
    staticStyle("settings-table", settingsTableStaticCss)
    val welcomeContent = staticStyle("welcome-content", welcomeContentStaticCss)

    val router = router("")

    val menuStore = storeOf(false)

    val menuBackgroundColor = Theme().colors.neutral

    render(themes.first()) {
        box({
            height { "100%" }
            width { "100%" }
            position { relative {} }
            children("&[data-menu-open] #menu-left") {
                display { flex }
            }
        }) {
            attr("data-menu-open", menuStore.data)
            navBar({
                border { width { "0" } }
                boxShadow { flat }
            }) {
                brand {
                    stackUp {
                        spacing { none }
                        items {
                            a({
                                textDecoration { initial }
                                color { primary.main }
                            }) {
                                href("https://www.fritz2.dev/")
                                target("_blank")

                                icon({
                                    size { "3rem" }
                                    color { primary.main }
                                }) { fromTheme { fritz2 } }

                                span({
                                    margins { left { smaller } }
                                    verticalAlign { sub }
                                    fontSize(sm = { large }, md = { larger })
                                    fontWeight { lighter }
                                }) { +"Components" }
                            }

                            a({
                                display { flex }
                                justifyContent { flexEnd }
                                width { full }
                                margins { top { "-10px".important } }
                                fontSize { tiny }
                            }) {
                                +fritz2Version()
                                href("https://github.com/jwstegemann/fritz2/releases")
                                target("_blank")
                            }
                        }
                    }
                    //FIXME: convert to styles
                    span({
                        css("""
                            display: inline-flex;
                            vertical-align: top;
                            -moz-box-align: center;
                            align-items: center;
                            max-width: 100%;
                            font-weight: 500;
                            min-height: 1.5rem;
                            min-width: 1.5rem;
                            border-radius: 0.375rem;
                            background: none repeat scroll 0% 0%;
                            """.trimIndent()
                        )
                        paddings(
                            sm = { horizontal { "0.25rem" } },
                            md = { horizontal { "0.5rem" } }
                        )
                        fontSize(
                            sm = { smaller },
                            md = { small }
                        )
                        lineHeight(
                            sm = { smaller },
                            md = { small }
                        )
                        background {
                            color { secondary.main }
                        }
                        color { neutral.main }
                        margins {
                            left { small }
                        }
                    }) {
                        +appStatus().replaceFirstChar { it.uppercase() }
                    }
                }

                actions {

                    lineUp({
                        display(sm = { none }, md = { flex })
                        alignItems { center }
                    }) {
                        items {
                            a({
                                fontSize { tiny }
                                color { gray600 }
                                textAlign { center }
                            }) {
                                +"Made with ❤️ using fritz2"
                                href("https://github.com/jwstegemann/fritz2")
                                target("_blank")
                            }

                            linkButton({
                                color { primary.main }
                            }) {
                                variant { ghost }
                                size { large }
                                icon { githubIcon }
                                href("https://github.com/jwstegemann/fritz2-kitchensink")
                                target("_blank")
                            }
//                            navAnchor("Documentation", "https://docs.fritz2.dev/")
//                            navAnchor("API", "https://api.fritz2.dev")
//                            navAnchor("Examples", "https://www.fritz2.dev/examples.html")
//                            navAnchor("Github", "https://github.com/jwstegemann/fritz2")
                        }
                    }
                    clickButton({
                        display(sm = { flex }, md = { none })
                    }) {
                        icon { menu }
                    }.map {
                        window.scrollTo(0.0, 0.0)
                        !menuStore.current
                    } handledBy menuStore.update
                }
            }

            lineUp({
                alignItems { stretch }
                minHeight { "100%" }
                direction(sm = { column }, md = { row })
            }) {
                items {
                    stackUp({
                        margins {
                            top { larger }
                        }
                        padding { "1rem" }
                        minWidth(
                            md = { "220px" },
                            lg = { "240px" }
                        )
                        minHeight { "100%" }
                        display(sm = { none }, md = { flex })
                        wrap { nowrap }
                        direction { column }
                        alignItems { flexStart }
                        paddings {
                            top { "50px" }
                            left { "1.6rem" }
                            right { "1.6rem" }
                        }
                        borders(
                            sm = {
                                bottom { width { "1rem" } }
                            },
                            md = {
                                bottom { width { "0px" } }
                            }
                        )
                        border { color { "light" } }
                        background { color { menuBackgroundColor.main } }
                    }, id = "menu-left")
                    {
                        spacing { tiny }
                        items {

                            p({
                                width { "100%" }
                                margins { top { huge } }
                            }) {
                                menuAnchor(welcome_)
                            }
                            menuAnchor(gettingStarted_)

                            menuHeader("FEATURES")
                            menuAnchor(responsive_)
                            menuAnchor(styling_)
                            menuAnchor(theme_)
                            menuAnchor(colors_)

                            menuHeader("LAYOUT")
                            menuAnchor(flexbox_)
                            menuAnchor(gridbox_)
                            menuAnchor(stack_)

                            menuHeader("FORMS")
                            menuAnchor(buttons_)
                            menuAnchor(input_)
                            menuAnchor(select_)
                            menuAnchor(file_)
                            menuAnchor(checkbox_)
                            menuAnchor(radio_)
                            menuAnchor(textarea_)
                            menuAnchor(switch_)
                            menuAnchor(slider_)
                            menuAnchor(formcontrol_)

                            menuHeader("COMPLEX")
                            menuAnchor(datatable_)

                            menuHeader("OVERLAY")
                            menuAnchor(dropdown_)
                            menuAnchor(modal_)
                            menuAnchor(popover_)
                            menuAnchor(tooltip_)
                            menuAnchor(toasts_)

                            menuHeader("MISC")
                            menuAnchor(alert_)
                            menuAnchor(menu_)
                            menuAnchor(icons_)
                            menuAnchor(spinner_)

                        }
                    }
                    box({
                        paddings(md = {
                            left { large }
                            top { small }
                        })
                        margins {
                            left { "0".important }
                        }
                        width { "100%" }
                        radius { small }
                        background { color { neutral.main } }
                    }) {
                        className(welcomeContent.whenever(router.data) { it == welcome_ }.name)

                        router.data.render { site ->
                            menuStore.update(false)
                            when (site) {
                                gettingStarted_ -> gettingStarted()
                                icons_ -> iconsDemo()
                                spinner_ -> spinnerDemo()
                                input_ -> inputDemo()
                                select_ -> selectDemo()
                                buttons_ -> buttonDemo()
                                file_ -> fileDemo()
                                formcontrol_ -> formControlDemo()
                                flexbox_ -> flexBoxDemo()
                                gridbox_ -> gridBoxDemo()
                                checkbox_ -> checkboxesDemo()
                                radio_ -> radiosDemo()
                                switch_ -> switchDemo()
                                stack_ -> stackDemo()
                                dropdown_ -> dropdownDemo()
                                modal_ -> modalDemo()
                                popover_ -> popoverDemo()
                                tooltip_ -> tooltipDemo()
                                welcome_ -> welcome()
                                styling_ -> stylingDemo()
                                theme_ -> themeDemo()
                                colors_ -> colorDemo()
                                responsive_ -> responsiveDemo()
                                textarea_ -> textareaDemo()
                                alert_ -> alertDemo()
                                menu_ -> menuDemo()
                                toasts_ -> toastDemo()
                                datatable_ -> dataTableDemo()
                                slider_ -> sliderDemo()
                                else -> welcome()
                            }
                        }
                    }
                }
            }
        }
    }
}
