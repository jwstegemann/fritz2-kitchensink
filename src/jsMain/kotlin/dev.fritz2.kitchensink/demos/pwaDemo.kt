package dev.fritz2.kitchensink.demos

import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*


fun RenderContext.pwaDemo(): Div {
    return contentFrame {
        showcaseHeader("Progressive Web App (PWA)")
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