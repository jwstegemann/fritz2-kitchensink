package dev.fritz2.kitchensink.datatable

import dev.fritz2.lenses.Lenses
import kotlinx.datetime.LocalDate

@Lenses
data class Person(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val language: List<String>
)

val persons = listOf(
    Person(1,"Edward","Daniels","1999-05-21",listOf("Kotlin", "Rust", "Python")),
    Person(2,"Michelle","Andrade","1970-02-19",listOf("Python")),
    Person(3,"Kathryn","Jones","1986-09-28",listOf("Clojure", "Scala")),
    Person(4,"Gerald","Cruz","1986-01-19",listOf("Scala")),
    Person(5,"Jenna","Parker","1971-06-21",listOf("Frege", "Clojure", "Kotlin")),
    Person(6,"Paula","Cannon","1994-01-27",listOf("Java")),
    Person(7,"Dylan","Griffith","1979-09-23",listOf("Python", "Scala")),
    Person(8,"Nathan","Guerrero","1987-09-07",listOf("Java")),
    Person(9,"John","Zimmerman","1987-03-24",listOf("Java", "Kotlin")),
    Person(10,"Diana","Booth","1984-01-13",listOf("Clojure", "Python")),
    Person(11,"Amanda","Munoz","1993-07-22",listOf("Java", "Clojure", "Frege")),
    Person(12,"Randy","Dodson","1975-10-31",listOf("Clojure", "Frege")),
    Person(13,"Christopher","Haas","1981-01-26",listOf("Kotlin")),
    Person(14,"Matthew","Phillips","1992-03-18",listOf("Python", "Scala")),
    Person(15,"Jennifer","Washington","1996-05-27",listOf("Clojure", "Rust")),
    Person(16,"Stephanie","Hernandez","1988-05-19",listOf("Frege")),
    Person(17,"Mark","Bullock","2000-08-07",listOf("Kotlin")),
    Person(18,"Carrie","King","1996-05-15",listOf("Clojure", "Kotlin", "Rust")),
    Person(19,"Jessica","Meadows","1976-05-18",listOf("Frege", "Java")),
    Person(20,"Jonathan","Jimenez","1998-10-13",listOf("Scala", "Frege")),
    Person(21,"Maria","Morgan","1979-07-08",listOf("Frege")),
    Person(22,"James","Jenkins","1980-05-29",listOf("Python")),
    Person(23,"Kathleen","Whitney","2000-10-02",listOf("Scala")),
    Person(24,"Kenneth","Drake","1998-10-10",listOf("Kotlin")),
    Person(25,"April","Gray","1998-04-26",listOf("Python", "Clojure")),
    Person(26,"Craig","Johnson","1992-07-25",listOf("Python", "Rust")),
    Person(27,"William","Thomas","1970-04-02",listOf("Python")),
    Person(28,"Sylvia","Scott","1989-07-06",listOf("Python")),
    Person(29,"Larry","Smith","1978-09-27",listOf("Python")),
    Person(30,"Scott","Schultz","1992-08-23",listOf("Python", "Kotlin", "Rust")),
    Person(31,"Jeremiah","Miller","1985-09-18",listOf("Python", "Kotlin", "Scala")),
    Person(32,"Sonya","Cooper","2001-02-20",listOf("Scala")),
    Person(33,"Kristi","Bryant","1989-07-12",listOf("Python")),
    Person(34,"Mary","Alvarez","1984-06-10",listOf("Rust")),
    Person(35,"Raymond","Olson","2001-11-24",listOf("Java", "Scala")),
    Person(36,"Andrew","Pearson","1984-05-14",listOf("Frege", "Clojure")),
    Person(37,"Ashley","Fowler","1984-01-27",listOf("Scala", "Rust", "Kotlin")),
    Person(38,"Madison","Williams","1979-03-14",listOf("Java")),
    Person(39,"Michael","Francis","1974-04-24",listOf("Rust")),
    Person(40,"Alfred","Carey","2000-12-14",listOf("Scala", "Rust")),
    Person(41,"Billy","Ochoa","1994-06-22",listOf("Frege")),
    Person(42,"Corey","Manning","1987-03-10",listOf("Frege", "Java", "Clojure")),
    Person(43,"Veronica","Maldonado","1982-11-28",listOf("Rust")),
    Person(44,"Leslie","Riggs","2000-04-07",listOf("Java", "Frege", "Scala")),
    Person(45,"Brian","Ortiz","1986-03-07",listOf("Clojure", "Frege")),
    Person(46,"Richard","Mendoza","1974-11-25",listOf("Kotlin", "Clojure", "Frege")),
    Person(47,"Bonnie","Bennett","1981-02-18",listOf("Python", "Clojure")),
    Person(48,"Sean","Russell","1980-10-01",listOf("Kotlin")),
    Person(49,"Jack","Mathis","1984-12-03",listOf("Clojure", "Scala", "Kotlin")),
    Person(50,"Brooke","Roberts","1975-10-10",listOf("Clojure", "Python")),
    Person(51,"Barry","Anderson","1972-05-26",listOf("Python", "Rust", "Java")),
    Person(52,"Travis","Martinez","1983-07-18",listOf("Rust")),
    Person(53,"Tim","Fischer","1988-03-23",listOf("Scala")),
    Person(54,"Brandon","Rogers","1997-07-18",listOf("Java")),
    Person(55,"Darrell","Richards","1998-10-20",listOf("Java", "Clojure")),
    Person(56,"Bradley","Vargas","1990-05-07",listOf("Java")),
    Person(57,"Thomas","Meyers","1973-02-15",listOf("Java")),
    Person(58,"Lisa","Jackson","1973-07-15",listOf("Python", "Kotlin")),
    Person(59,"Danielle","Compton","1995-07-23",listOf("Rust", "Java", "Python")),
    Person(60,"Sabrina","Zhang","2001-01-18",listOf("Python")),
    Person(61,"Keith","Mack","1993-12-13",listOf("Python", "Clojure")),
    Person(62,"Jill","Stewart","1994-06-22",listOf("Java")),
    Person(63,"Jose","Lynch","1990-10-06",listOf("Clojure", "Frege")),
    Person(64,"Allison","Berry","1988-04-27",listOf("Scala")),
    Person(65,"Heather","Castillo","1972-03-19",listOf("Rust", "Scala")),
    Person(66,"Joshua","Wright","1999-10-17",listOf("Python")),
    Person(67,"Henry","Sanchez","1997-08-25",listOf("Scala", "Frege")),
    Person(68,"Courtney","Mullen","1994-01-09",listOf("Clojure", "Kotlin")),
    Person(69,"Johnny","Ross","1992-05-05",listOf("Clojure")),
    Person(70,"Trevor","Garcia","1980-05-10",listOf("Java")),
    Person(71,"Zachary","Burgess","1974-06-18",listOf("Frege", "Kotlin", "Scala")),
    Person(72,"Cory","Shelton","1999-11-01",listOf("Kotlin", "Clojure", "Frege")),
    Person(73,"Tyler","Cobb","1979-10-13",listOf("Java", "Frege", "Clojure")),
    Person(74,"Walter","Salinas","1983-08-24",listOf("Clojure", "Python")),
    Person(75,"Erica","Lawrence","1987-01-08",listOf("Rust")),
    Person(76,"Jacob","Hill","1975-09-23",listOf("Kotlin", "Frege")),
    Person(77,"Shari","Browning","1997-11-10",listOf("Clojure")),
    Person(78,"Mariah","Owens","1993-07-30",listOf("Kotlin", "Java")),
    Person(79,"Chad","White","1974-12-15",listOf("Scala", "Rust")),
    Person(80,"Bryan","Duran","1982-04-08",listOf("Clojure", "Python")),
    Person(81,"Nicholas","Stokes","1995-05-28",listOf("Python", "Scala")),
    Person(82,"Margaret","Burch","1998-02-24",listOf("Java")),
    Person(83,"Shannon","Stevens","1993-12-05",listOf("Kotlin", "Java", "Rust")),
    Person(84,"Justin","Contreras","1985-02-17",listOf("Java", "Frege")),
    Person(85,"Phillip","Norris","1986-01-21",listOf("Rust", "Clojure")),
    Person(86,"Colleen","Tran","1985-01-02",listOf("Kotlin", "Python", "Scala")),
    Person(87,"Caroline","Flynn","1999-08-29",listOf("Clojure", "Rust", "Java")),
    Person(88,"Peter","Hamilton","1980-03-06",listOf("Python")),
    Person(89,"Robin","Singh","2000-09-11",listOf("Python")),
    Person(90,"Martin","Yu","1989-02-15",listOf("Scala", "Python", "Java")),
    Person(91,"Christina","Murray","1993-03-08",listOf("Java", "Scala", "Frege")),
    Person(92,"Rebecca","Allen","1994-11-11",listOf("Rust")),
    Person(93,"Michaela","Kirk","1979-11-27",listOf("Java", "Kotlin")),
    Person(94,"Morgan","Griffin","1993-11-05",listOf("Clojure", "Java")),
    Person(95,"Christine","Dunn","2001-12-23",listOf("Java", "Python")),
    Person(96,"Jeffrey","Patel","1978-11-26",listOf("Frege")),
    Person(97,"Jodi","May","1992-02-18",listOf("Python", "Java")),
    Person(98,"Alexandra","Dickson","1984-05-26",listOf("Frege", "Java", "Clojure")),
    Person(99,"Terri","Holloway","1988-07-05",listOf("Kotlin", "Java", "Frege")),
    Person(100,"Sydney","Hudson","2001-11-23",listOf("Scala", "Java")),)

@Lenses
data class FinalPerson(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: LocalDate,
    val languages: List<String>
) {
//    fun name() = "$firstname $lastname"
    val name = "$firstname $lastname"
    val joinedLanguages = languages.joinToString(", ")
}

val finalPersons = persons.map {
    FinalPerson(
        it.id,
        it.firstname,
        it.lastname,
        LocalDate.parse(it.birthday),
        it.language
    )
}