package dev.fritz2.kitchensink.base

import kotlinx.browser.document

fun fritz2Version(): String {
    val version = document.asDynamic().fritz2Version
    return if(version != undefined) "v${version}" else ""
}