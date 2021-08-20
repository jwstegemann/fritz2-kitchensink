package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.remote.getBody
import dev.fritz2.remote.http

fun RenderContext.appFrameDemo(): Div {

    val sourceCode = storeOf("loading...")
    sourceCode.handle {
        runCatching {
            http("https://raw.githubusercontent.com/jamowei/appFrame-demo/master/src/main/kotlin/appFrame.kt")
                .get().getBody()
        }.onFailure { console.error(it) }
        .getOrDefault("error getting sourcecode")
    }()

    return contentFrame {
        showcaseHeader("AppFrame")

        paragraph {
            +"The "
            c("appFrame")
            +" component offers a complete app frame for your application. It is mobile-first optimized"
            +" (full-responsive) and offers different areas to put your elements in."
        }

        showcaseSection("Structure")
        paragraph {
            +"The "
            c("appFrame")
            +" component provides the following areas:"
            ul {
                li {
                    c("brand")
                    +" - place to put your app logo and name in"
                }
                li {
                    c("header")
                    +" - title of current page or leaf blank"
                }
                li {
                    c("actions")
                    +" - place global actions (e.g. buttons) here"
                }
                li {
                    c("content")
                    +" - main content of your app; typically controlled by your "
                    c("router")
                }
                li {
                    c("navigation")
                    +" - place your routes to the pages here (e.g. use "
                    c("menu")
                    +" component therefore)"
                }
                li {
                    c("complementary")
                    +" - general control or info under "
                    c("navigation")
                    +" section (e.g. log-out button)"
                }
                li {
                    c("tablist")
                    +" - list of buttons shown beneath "
                    c("content")
                    +" area"
                }
            }
        }

        paragraph {
            +"The following picture shows where this areas are located inside the "
            c("appFrame")
            +"."
            img {
                src("img/appFrame_areas.png")
                alt("appFrame areas")
            }
        }

        showcaseSection("Usage")
        paragraph {
            +"This "
            externalLink("example", "https://jamowei.github.io/appFrame-demo/#main")
            +" shows the basic usage of the "
            c("appFrame")
            +" in its simplest form."
            +"Click on the link to open it and see what's happen when you resize your window."
        }
        paragraph {
            +"The sourcecode is hosted on "
            externalLink("Github", "https://github.com/jamowei/appFrame-demo")
            +"."
        }
        componentFrame {
            iframe {
                src("https://jamowei.github.io/appFrame-demo/#main")
                height("400px")
                width("100%")
            }
        }
        highlight {
            source(sourceCode.data)
        }

        showcaseSection("Progressive Web App (PWA)")
        paragraph {
            +"If you want to use your app as an "
            externalLink("PWA", "https://web.dev/what-are-pwas/")
            +" the following steps are needed:"
        }
        paragraph {
            +"Generate a "
            c("manifest.json")
            +" file for your app and corresponding icons. You can use the tool from "
            externalLink("firebase", "https://app-manifest.firebaseapp.com/")
            +" for it."

        }
    }
}