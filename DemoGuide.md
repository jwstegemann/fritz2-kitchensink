# Demo Page Guidelines

This is a short guide to help our devs create demo pages for fritz2 components.

## Language
The fritz2 API, documentation, and demo use American English. Example for differences to British English:
* Some ou become o: flavour --> flavor
* Some words ending in -ence end in -ense: defence --> defense

## Case and Naming
The component demo menu on the left uses singular, PascalCase names for each component, where the PascalCase 
reflects the component's name in the API:

* Checkbox
* TextArea
* FormControl

The showcase header on each demo page should match this link text. The subsections on the page should all start with 
capital letters ("Disabling A Button").

## Page structuring
Each demo page should start with the basic usage of the component, meaning the simplest way to use it, 
followed by possibly store/no-store variants. Next, features which are shared by most components should be demonstrated. Generally, 
start with common features and get more specific and special towards the bottom of the page:

* Usage
* Stores / Event Handling
* Variants
* Sizes
* Special component features
* Rarely used special features

## Specific code examples, short comments
When coding examples to show off a component, a good general rule is to omit lines of code that are not necessary to
showcasing the point of the current subsection. For example, when you are showing the sizes of a switch in a subsection 
called Sizes, do not add custom styling or any other component features to that demonstration. Create a subsection for 
every feature you want to cover (except for custom styling, see below).

If you need code to make your example work which does not directly contribute to the example, like the creation of some stores and substores,
you could try and omit it in the example, but notify the user that you did:
```kotlin

        paragraph {
            +"Please note that the creation of stores was omitted in some of the examples to keep the source fragments short."
        }

        val demoItems = listOf("item 1", "item 2", "item 3") // all these lines do not show in example code
        val usageCheckboxStore = storeOf(true)
        val usageCheckboxGroupStore = RootStore(listOf("item 2", "item 3"))
        val customCheckboxStore1 = storeOf(false)
        val customCheckboxStore2 = storeOf(true)
        val customCheckboxStore3 = storeOf(false)
        val sizesCheckboxStore1 = storeOf(false)
        val sizesCheckboxStore2 = storeOf(true)
        val sizesCheckboxStore3 = storeOf(true)

        showcaseSection("Usage")
        paragraph {
            +"Single checkboxes simply need a Flow of Boolean representing their state, passed via the "
            c("checked")
            +" function. If you want to connect a handler to the state changes, use the event context."
        }
        
        // ...
        
        playground {
            source(
                """
                checkbox {
                    label("A single Checkbox")
                    checked { store.data }
                    events {
                        changes.states() handledBy store.update
                    }
                }
                """
            )
        }
```

Also, please keep the example comments short - comment only when it's necessary and adds non-trivial information. The example 
comments are displayed in bright green and quickly make the code look crowded.

There is a dedicated page for the Styling of components (stylingDemo.kt), so the component demo pages generally do not need 
sections on how to use the styling parameter to change the component's appearance. In some cases, styling code is needed 
to make another point like for this checkbox component function demonstration:

```kotlin
        showcaseSection("Customizing")
        paragraph {
            +"You can customize the checked styles, unchecked styles, and the component label. The unchecked"
            +" styles go directly into the styling parameter, while you need to use the component functions "
            c("checkedStyle")
            +" and "
            c("labelStyle")
            +" for their respective changes in appearance."
        }

        componentFrame {
            checkbox {
                label("Custom label style: larger margin")
                labelStyle { { margins { left { larger } } } } // component function for label style
                // ...
            }
            checkbox({
                background { color { "tomato" } } // demonstrate difference to component functions
            }) {
                label("Changed unchecked background color")
                // ...
            }
            
            // ...
        }
```



## Omit default values in example code
When showcasing a component and its source code, list the possible property values and their default, but omit the default in the code and example code since the point of
a default value is not having to specify it. 
```kotlin
showcaseSection("Thickness")
paragraph {
    +"You can change the line width of the spinner using the thickness property. Choose between "
    c("thin")
    +", "
    c("normal")
    +" (default), and "
    c("fat")
    +", or use the styling parameter to define a custom value."
}

componentFrame {
    spinner {}
    p { +"Normal thickness (default)" }
}
```

## Formatting API Identifiers
When using API identifiers in a descriptive text such as theme values (small, normal, large, ...) or function names 
(disabled, lighter, ...), they can be formatted using the c() function. Remember to insert a space before and after. 
However, do not overuse this format - not every
mention of something loosely code related warrants the extra markup. You are also ot required to use the component's name 
every time you mention it (InputField --> input field). 

```kotlin
showcaseSection("Usage")
paragraph {
    +"A basic input field can be created without a store, but you have to manually connect"
    +" the handlers in this case. Every input offers the sub context" 
    c(" base") // format base context as code
    +", where you can access the underlying input's properties." // don't format input
}
```
![Formatting API Identifiers]("https://components.fritz2.dev/demoguide001.png")


