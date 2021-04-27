package dev.fritz2.kitchensink

import dev.fritz2.lenses.Lenses

@Lenses
data class Address(
    val main: String,
    val country: String
)

@Lenses
data class Developer(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val address: Address
)

val developers = listOf(
    Developer(1, "John", "Doe", "24.12.1990", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(2, "Jane", "Doe", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(3, "Foo", "Bar", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(4, "Bar", "Foo", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(5, "Baz", "Bar", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(6, "FooBar", "Baz", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    Developer(7, "FizzBuzz", "Fizz", "01.06.1991", Address("Main Street 123, 12345 Berlin", "Germany")),
    )