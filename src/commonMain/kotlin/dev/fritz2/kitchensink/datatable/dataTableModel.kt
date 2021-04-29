package dev.fritz2.kitchensink.datatable

import dev.fritz2.lenses.Lenses
import kotlinx.datetime.LocalDate

@Lenses
data class Person(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val country: String,
    val language: List<String>
)

val persons = listOf(
    Person(1,"Dana","Dorsey","1985-08-06","Argentina",listOf("Rust", "Kotlin")),
    Person(2,"Jillian","Davis","1990-10-18","Pakistan",listOf("Python", "Java")),
    Person(3,"Richard","Harrison","1990-09-29","Equatorial Guinea",listOf("Kotlin", "Python", "Frege")),
    Person(4,"Scott","Liu","1994-04-19","Serbia",listOf("Python")),
    Person(5,"Craig","Deleon","2001-10-04","Venezuela",listOf("Kotlin")),
    Person(6,"Nicole","Marshall","1986-08-12","Pitcairn Islands",listOf("Scala", "Kotlin")),
    Person(7,"Lisa","Maldonado","1974-10-10","Qatar",listOf("Rust", "Clojure")),
    Person(8,"Matthew","Dominguez","1982-12-11","Marshall Islands",listOf("Rust")),
    Person(9,"Jonathan","Watts","2000-05-24","Tonga",listOf("Frege")),
    Person(10,"Joshua","Martinez","1987-05-13","Eritrea",listOf("Rust", "Kotlin")),
    Person(11,"Heather","Barrett","1997-12-02","Lesotho",listOf("Kotlin", "Java")),
    Person(12,"Tiffany","Williams","1998-10-03","Aruba",listOf("Clojure", "Kotlin", "Java")),
    Person(13,"Debra","Lamb","1980-12-18","Albania",listOf("Kotlin")),
    Person(14,"Ashley","Wilson","1992-07-30","Niger",listOf("Clojure", "Kotlin")),
    Person(15,"John","Black","2000-08-28","Trinidad and Tobago",listOf("Python", "Kotlin", "Java")),
    Person(16,"Jose","Henderson","1975-02-11","Liechtenstein",listOf("Rust")),
    Person(17,"Anthony","Stout","1992-07-17","Saint Lucia",listOf("Rust")),
    Person(18,"Frederick","Kelly","1995-04-11","Lebanon",listOf("Clojure", "Python")),
    Person(19,"Bradley","Torres","1985-10-28","Iran",listOf("Clojure")),
    Person(20,"David","Wu","1995-11-27","Poland",listOf("Java")),
    Person(21,"Alyssa","Brown","1999-04-29","Jordan",listOf("Scala", "Python")),
    Person(22,"Felicia","Miller","1977-04-22","Angola",listOf("Clojure", "Java")),
    Person(23,"Carolyn","Cooper","2001-12-03","India",listOf("Clojure", "Scala")),
    Person(24,"Jennifer","Warren","1972-03-21","Cambodia",listOf("Rust")),
    Person(25,"Laura","Rodriguez","1994-01-16","Nicaragua",listOf("Clojure")),
    Person(26,"Laurie","Wilcox","1996-11-27","Angola",listOf("Kotlin", "Python", "Java")),
    Person(27,"Mercedes","Avila","1990-02-24","Uzbekistan",listOf("Clojure", "Python")),
    Person(28,"Shawn","Thomas","2001-05-13","Nicaragua",listOf("Rust", "Python", "Frege")),
    Person(29,"Amanda","Casey","1975-10-29","Western Sahara",listOf("Clojure", "Python")),
    Person(30,"Aaron","Eaton","1999-01-07","Australia",listOf("Kotlin", "Frege")),
    Person(31,"Karen","Washington","1995-03-28","Gibraltar",listOf("Java", "Frege", "Kotlin")),
    Person(32,"Christina","Ewing","1989-02-28","Sweden",listOf("Frege")),
    Person(33,"Theresa","Floyd","1996-07-03","Kenya",listOf("Python", "Rust")),
    Person(34,"Oscar","Price","1974-03-13","Kyrgyz Republic",listOf("Java")),
    Person(35,"Seth","Smith","1975-04-07","Kazakhstan",listOf("Java", "Scala")),
    Person(36,"Kylie","Thompson","1974-12-28","Kuwait",listOf("Scala", "Java", "Clojure")),
    Person(37,"Jason","Donaldson","1988-10-10","Cook Islands",listOf("Python", "Rust", "Frege")),
    Person(38,"Sonia","Garcia","1976-08-03","Belarus",listOf("Kotlin", "Java")),
    Person(39,"Kathleen","Morgan","1975-04-14","Poland",listOf("Rust", "Clojure", "Kotlin")),
    Person(40,"Benjamin","Lewis","1993-07-13","Saint Barthelemy",listOf("Frege", "Rust", "Python")),
    Person(41,"William","Leach","1980-06-25","Serbia",listOf("Scala", "Clojure")),
    Person(42,"Adam","Bishop","1973-03-06","Iran",listOf("Java", "Python")),
    Person(43,"Joseph","Reeves","1980-09-13","Saint Martin",listOf("Rust", "Clojure")),
    Person(44,"Russell","Hart","1971-08-29","Slovenia",listOf("Python", "Clojure")),
    Person(45,"Jared","Harris","1973-06-19","Thailand",listOf("Python")),
    Person(46,"Chelsea","Anthony","1970-11-16","Papua New Guinea",listOf("Java")),
    Person(47,"Stanley","Owens","1991-06-28","Kiribati",listOf("Frege", "Java", "Kotlin")),
    Person(48,"Rachel","Richardson","1971-01-27","Mauritania",listOf("Java")),
    Person(49,"Margaret","Watson","1996-07-05","Zambia",listOf("Clojure", "Frege", "Kotlin")),
    Person(50,"Albert","Bautista","1978-11-04","San Marino",listOf("Java")),
    Person(51,"Daniel","Hill","1993-07-28","Belarus",listOf("Kotlin", "Frege")),
    Person(52,"Raymond","Harper","1981-03-15","Argentina",listOf("Frege", "Rust")),
    Person(53,"Nicholas","Cohen","1992-02-24","Turkmenistan",listOf("Java", "Frege")),
    Person(54,"Timothy","Wright","1988-08-03","Senegal",listOf("Java")),
    Person(55,"Kevin","Castillo","1971-12-26","French Guiana",listOf("Kotlin", "Scala", "Clojure")),
    Person(56,"Dakota","Stephenson","1973-04-04","Anguilla",listOf("Scala", "Java", "Python")),
    Person(57,"Jesus","Phelps","1983-10-21","Trinidad and Tobago",listOf("Python", "Frege")),
    Person(58,"Johnny","Glover","1970-06-26","Lithuania",listOf("Frege")),
    Person(59,"Jesse","Campbell","1997-02-14","Ireland",listOf("Java")),
    Person(60,"Cynthia","Roach","2000-02-03","Taiwan",listOf("Kotlin", "Clojure", "Frege")),
    Person(61,"Vicki","Stokes","1988-05-23","Equatorial Guinea",listOf("Scala", "Python")),
    Person(62,"Alexandra","Burns","1995-01-05","Latvia",listOf("Python")),
    Person(63,"Edward","Wong","1986-02-13","New Caledonia",listOf("Rust", "Frege")),
    Person(64,"Ivan","Lee","1995-08-22","Netherlands",listOf("Clojure", "Python", "Kotlin")),
    Person(65,"Henry","Boyle","1988-11-21","Saint Barthelemy",listOf("Java", "Scala", "Frege")),
    Person(66,"Emily","Cook","1971-11-02","Tanzania",listOf("Clojure", "Frege")),
    Person(67,"Leonard","Clark","2001-03-02","Peru",listOf("Kotlin", "Rust", "Frege")),
    Person(68,"Barbara","Conley","1994-08-26","American Samoa",listOf("Rust", "Scala")),
    Person(69,"Nancy","Olson","1979-07-16","Burkina Faso",listOf("Clojure", "Frege")),
    Person(70,"Shannon","Fitzgerald","1973-10-01","Algeria",listOf("Clojure", "Java", "Frege")),
    Person(71,"Alexis","Robinson","1984-06-17","Saint Helena",listOf("Kotlin")),
    Person(72,"Janet","Ramsey","1989-08-19","Western Sahara",listOf("Rust", "Python")),
    Person(73,"Sarah","Hunter","1990-02-21","Italy",listOf("Java", "Clojure", "Python")),
    Person(74,"Kara","Woodward","1978-09-17","Georgia",listOf("Rust")),
    Person(75,"Michelle","Salazar","1986-07-04","Zambia",listOf("Kotlin")),
    Person(76,"Stephanie","Foster","1980-07-13","Bolivia",listOf("Python")),
    Person(77,"Renee","Hensley","1992-02-07","Solomon Islands",listOf("Kotlin", "Scala", "Python")),
    Person(78,"Jacqueline","Sanders","1997-05-08","Tokelau",listOf("Kotlin")),
    Person(79,"Brett","Kelley","1989-05-15","Pakistan",listOf("Rust")),
    Person(80,"Robin","Rhodes","1970-01-13","Albania",listOf("Python", "Rust", "Frege")),
    Person(81,"Jamie","Zimmerman","1983-04-25","Bhutan",listOf("Scala", "Clojure")),
    Person(82,"Jeffery","Melton","1987-09-19","Georgia",listOf("Rust", "Scala")),
    Person(83,"Tina","Butler","1987-01-04","Turkmenistan",listOf("Scala", "Rust", "Java")),
    Person(84,"Kelsey","Hoffman","1985-06-06","Bulgaria",listOf("Java")),
    Person(85,"Curtis","Parks","1984-08-14","Saudi Arabia",listOf("Rust")),
    Person(86,"Alec","Peters","1981-10-14","Mali",listOf("Frege")),
    Person(87,"Yvonne","Mitchell","1970-12-25","Macedonia",listOf("Kotlin", "Python")),
    Person(88,"Ernest","Fowler","1990-01-13","Estonia",listOf("Scala")),
    Person(89,"Roberta","Rasmussen","1975-04-28","Bahamas",listOf("Rust", "Frege", "Clojure")),
    Person(90,"Katherine","Lloyd","1981-10-01","Algeria",listOf("Scala")),
    Person(91,"Brian","Bush","1979-01-05","Denmark",listOf("Kotlin", "Frege")),
    Person(92,"Isaiah","Bullock","1980-04-08","Comoros",listOf("Python", "Clojure")),
    Person(93,"Pamela","Hicks","1982-03-31","Greenland",listOf("Java")),
    Person(94,"Kyle","Cummings","1980-03-29","Philippines",listOf("Java")),
    Person(95,"Derrick","Dunn","1986-05-20","Finland",listOf("Scala", "Clojure")),
    Person(96,"Tyler","Pham","1986-09-03","Mexico",listOf("Scala", "Frege")),
    Person(97,"Greg","Gibson","1971-11-24","Slovenia",listOf("Frege")),
    Person(98,"Louis","Adams","1993-08-16","San Marino",listOf("Rust", "Scala")),
    Person(99,"Max","Osborn","1978-12-03","Myanmar",listOf("Clojure", "Java")),
    Person(100,"Veronica","Ortega","1982-08-25","India",listOf("Rust", "Kotlin", "Clojure")),
)

@Lenses
data class FinalPerson(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: LocalDate,
    val country: String,
    val languages: List<String>
) {
    fun name() = "$firstname $lastname"
    // TODO: Examine, why Lens generation fails again
    /*
    val name: String
        get() = "$firstname $lastname"

     */

    fun joinedLanguages() = languages.joinToString(", ")
}

val finalPersons = persons.map {
    FinalPerson(
        it.id,
        it.firstname,
        it.lastname,
        LocalDate.parse(it.birthday),
        it.country,
        it.language
    )
}