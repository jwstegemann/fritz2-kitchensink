package dev.fritz2.kitchensink

import dev.fritz2.binding.RootStore
import dev.fritz2.components.*
import dev.fritz2.components.appFrame.registerServiceWorker
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.demos.*
import dev.fritz2.routing.router
import dev.fritz2.styling.*
import dev.fritz2.styling.theme.Theme
import dev.fritz2.styling.theme.render
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

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
const val card_ = "Card"
const val menu_ = "Menu"
const val paper_ = "Paper"
const val toasts_ = "Toast"
const val datatable_ = "DataTable"
const val slider_ = "Slider"
const val appFrame_ = "AppFrame"
const val pwa_ = "PWA"
const val typeAhead_ = "TypeAhead"

val router = router(welcome_)

object ThemeStore : RootStore<Int>(0) {
    val selectTheme = handle<Int> { _, index ->
        Theme.use(themes[index])
        index
    }
}

const val highlightBackgroundColor = "#2b303b"

const val welcomeContentStaticCss = """
    background-image: url("img/background.jpg");
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
    registerServiceWorker()

    staticStyle("settings-table", settingsTableStaticCss)
    val welcomeContent = staticStyle("welcome-content", welcomeContentStaticCss)

    val router = router("")

    render(themes.first()) {
        appFrame {
            brand({
                minWidth { "18rem" }
            }) {
                stackUp {
                    spacing { none }
                    items {
                        a {
                            href("https://www.fritz2.dev/")
                            target("_blank")

                            icon({
                                size { "3rem" }
                                paddings { top { "5px" } }
                            }) { fromTheme { fritz2 } }

                            span({
                                margins { left { smaller } }
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
                        linkButton {
                            variant { ghost }
                            type { primary.inverted() }
                            text("Made with ❤️ using fritz2")
                            href("https://github.com/jwstegemann/fritz2")
                            target("_blank")
                        }

                        linkButton {
                            variant { ghost }
                            type { primary.inverted() }
                            size { large }
                            icon { githubIcon }
                            href("https://github.com/jwstegemann/fritz2-kitchensink")
                            target("_blank")
                        }
                    }
                }
            }
            navigation {

                menu {

                    header("KITCHEN SINK")
                    menuAnchor(welcome_)
                    menuAnchor(gettingStarted_)

                    header("FEATURES")
                    menuAnchor(responsive_)
                    menuAnchor(styling_)
                    menuAnchor(theme_)
                    menuAnchor(colors_)
                    menuAnchor(pwa_)

                    header("LAYOUT")
                    menuAnchor(appFrame_)
                    menuAnchor(flexbox_)
                    menuAnchor(gridbox_)
                    menuAnchor(stack_)

                    header("FORMS")
                    menuAnchor(buttons_)
                    menuAnchor(input_)
                    menuAnchor(select_)
                    menuAnchor(typeAhead_)
                    menuAnchor(file_)
                    menuAnchor(checkbox_)
                    menuAnchor(radio_)
                    menuAnchor(textarea_)
                    menuAnchor(switch_)
                    menuAnchor(slider_)
                    menuAnchor(formcontrol_)

                    header("COMPLEX")
                    menuAnchor(datatable_)

                    header("OVERLAY")
                    menuAnchor(dropdown_)
                    menuAnchor(modal_)
                    menuAnchor(popover_)
                    menuAnchor(tooltip_)
                    menuAnchor(toasts_)

                    header("MISC")
                    menuAnchor(alert_)
                    menuAnchor(paper_)
                    menuAnchor(card_)
                    menuAnchor(menu_)
                    menuAnchor(icons_)
                    menuAnchor(spinner_)
                }
            }
            content {
                className(welcomeContent.whenever(router.data) { it == welcome_ }.name)
                router.data.map{ domNode.scrollTo(0.0, 0.0) } handledBy this@appFrame.closeSidebar
                router.data.render { site ->
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
                        paper_ -> paperDemo()
                        card_ -> cardDemo()
                        menu_ -> menuDemo()
                        toasts_ -> toastDemo()
                        datatable_ -> dataTableDemo()
                        slider_ -> sliderDemo()
                        appFrame_ -> appFrameDemo()
                        pwa_ -> pwaDemo()
                        typeAhead_ -> typeAheadDemo()
                        else -> welcome()
                    }
                }
            }
        }
    }
}
