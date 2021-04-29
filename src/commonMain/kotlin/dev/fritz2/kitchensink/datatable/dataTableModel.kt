package dev.fritz2.kitchensink.datatable

import dev.fritz2.lenses.Lenses

@Lenses
data class Person(
    val id: Int,
    val firstname: String,
    val lastname: String,
    val birthday: String,
    val country: String,
    val language: String // Idea: Use List<String> for grouping showcase!
)

val persons = listOf(
    Person(1,"Chelsea","Smith","1986-05-05","India","Kotlin"),
    Person(2,"Mario","Day","1978-05-25","Burundi","Rust"),
    Person(3,"Eric","Archer","1976-11-26","Italy","Clojure"),
    Person(4,"Jessica","Farley","1991-07-31","Tajikistan","Scala"),
    Person(5,"Stephen","Mitchell","1983-08-31","Spain","Clojure"),
    Person(6,"Jeanette","Cobb","1970-11-14","Macao","Rust"),
    Person(7,"Victoria","Davis","1985-04-16","Benin","Frege"),
    Person(8,"Brian","Wall","1978-05-06","Moldova","Frege"),
    Person(9,"Jennifer","Moore","1976-07-11","Romania","Frege"),
    Person(10,"Amber","Dominguez","1996-04-12","Georgia","Scala"),
    Person(11,"Ariel","Gordon","1970-02-11","Seychelles","Frege"),
    Person(12,"William","Foster","1977-10-19","Gibraltar","Java"),
    Person(13,"Donald","Weaver","1971-01-04","Jamaica","Rust"),
    Person(14,"Rhonda","White","1979-07-30","Cape Verde","Kotlin"),
    Person(15,"Brett","Anderson","1973-10-11","Nepal","Frege"),
    Person(16,"Kathy","Wright","1990-05-17","Greenland","Kotlin"),
    Person(17,"Robert","Dickson","1988-11-20","Brazil","Rust"),
    Person(18,"Ian","Gilbert","1985-12-24","Namibia","Python"),
    Person(19,"James","Armstrong","1986-07-03","Eritrea","Frege"),
    Person(20,"Luke","Lin","1979-10-24","Gabon","Java"),
    Person(21,"Betty","Ford","2001-01-16","Myanmar","Java"),
    Person(22,"Nicole","Palmer","1991-05-25","Guadeloupe","Scala"),
    Person(23,"Mandy","Gonzales","1996-01-30","Tuvalu","Kotlin"),
    Person(24,"Kenneth","Daniels","1974-01-07","Brazil","Rust"),
    Person(25,"Donna","Combs","1970-12-06","Denmark","Frege"),
    Person(26,"Tammy","Martinez","1981-03-12","Costa Rica","Python"),
    Person(27,"Bobby","Kent","1996-12-05","Swaziland","Java"),
    Person(28,"Tanner","Sanchez","1979-08-25","Australia","Java"),
    Person(29,"Kathleen","Franklin","2001-09-04","Mauritius","Frege"),
    Person(30,"Judith","Villarreal","1993-03-29","Madagascar","Clojure"),
    Person(31,"Lisa","Morton","1993-04-03","Kazakhstan","Clojure"),
    Person(32,"Maurice","Guerra","1987-01-24","Venezuela","Scala"),
    Person(33,"Elizabeth","Jackson","1989-03-25","Tajikistan","Frege"),
    Person(34,"Paula","Lopez","1976-04-11","Taiwan","Scala"),
    Person(35,"Briana","Stuart","1979-12-05","Timor-Leste","Kotlin"),
    Person(36,"Adrian","Osborne","1975-04-22","Cambodia","Frege"),
    Person(37,"Patricia","Morales","1981-07-31","Singapore","Rust"),
    Person(38,"Kim","Cross","1975-07-29","Lithuania","Scala"),
    Person(39,"Rachael","Wagner","2000-06-16","Kuwait","Java"),
    Person(40,"Brittney","Pierce","1986-12-21","Tajikistan","Scala"),
    Person(41,"Timothy","Spence","1992-02-23","Martinique","Rust"),
    Person(42,"Aaron","Mccormick","1983-03-06","Uzbekistan","Java"),
    Person(43,"Curtis","Fields","1989-08-25","Guam","Scala"),
    Person(44,"Shelby","Hernandez","1996-05-27","Vanuatu","Python"),
    Person(45,"Jacob","Fox","1977-10-10","Croatia","Rust"),
    Person(46,"Jesse","Scott","1979-10-06","Tokelau","Frege"),
    Person(47,"Kaitlyn","Beck","1972-09-16","Qatar","Frege"),
    Person(48,"Andre","Edwards","1990-12-04","Philippines","Python"),
    Person(49,"Grant","Brock","1977-06-14","Jamaica","Java"),
    Person(50,"Jason","Griffin","1972-07-08","Seychelles","Rust"),
    Person(51,"Crystal","Nelson","1986-05-31","Yemen","Java"),
    Person(52,"Susan","Howard","1999-12-28","Mayotte","Python"),
    Person(53,"Victor","Martin","1977-09-27","Namibia","Frege"),
    Person(54,"Carl","Bridges","1983-03-16","Puerto Rico","Rust"),
    Person(55,"Jose","Dennis","1980-02-08","Afghanistan","Kotlin"),
    Person(56,"Angela","Simpson","1980-08-03","Monaco","Rust"),
    Person(57,"Alan","Massey","1974-12-09","Eritrea","Rust"),
    Person(58,"Sheri","Horne","1989-01-12","Nauru","Frege"),
    Person(59,"Matthew","Robinson","1996-02-25","Rwanda","Scala"),
    Person(60,"Hannah","Whitaker","1981-07-28","Haiti","Java"),
    Person(61,"Tara","Jimenez","1979-11-10","Japan","Java"),
    Person(62,"Erica","Ballard","1981-05-03","Afghanistan","Java"),
    Person(63,"Frances","Newton","2001-12-07","Morocco","Java"),
    Person(64,"Dana","Barber","2000-06-28","Niue","Scala"),
    Person(65,"Jean","Johnston","1998-08-12","India","Java"),
    Person(66,"Samuel","Bates","1972-03-15","Hungary","Clojure"),
    Person(67,"Mary","Gardner","1988-07-25","Yemen","Java"),
    Person(68,"Isaac","Jones","1989-11-20","Bhutan","Java"),
    Person(69,"Alicia","Joseph","1992-06-22","Bermuda","Rust"),
    Person(70,"Tracy","Chen","1994-01-13","Swaziland","Java"),
    Person(71,"Courtney","Alexander","1973-04-03","Mauritania","Rust"),
    Person(72,"Sheryl","Meyer","1985-12-17","Singapore","Kotlin"),
    Person(73,"Deborah","Webster","1993-12-09","Luxembourg","Clojure"),
    Person(74,"Sandra","Fletcher","1995-01-16","Greenland","Python"),
    Person(75,"Michelle","Patel","1997-04-06","Hungary","Clojure"),
    Person(76,"Amanda","Blevins","1974-01-28","Cambodia","Frege"),
    Person(77,"Alison","Graham","1986-11-28","Turkey","Python"),
    Person(78,"Julie","Villanueva","1989-12-04","Bolivia","Rust"),
    Person(79,"Whitney","Solis","1995-01-06","Morocco","Scala"),
    Person(80,"Barbara","Pratt","1983-08-27","Singapore","Scala"),
    Person(81,"Jared","Schmitt","1977-07-07","Guam","Kotlin"),
    Person(82,"Dylan","Clark","1985-12-08","Montenegro","Kotlin"),
    Person(83,"Johnny","Serrano","1985-09-10","Mayotte","Python"),
    Person(84,"Monica","Stanley","1977-11-29","Tunisia","Kotlin"),
    Person(85,"Dawn","Becker","1978-04-08","Armenia","Java"),
    Person(86,"Blake","Long","1977-02-22","Nepal","Java"),
    Person(87,"Bonnie","Lambert","1983-11-12","Dominica","Scala"),
    Person(88,"Lori","Giles","1979-02-06","China","Frege"),
    Person(89,"Allen","Bryant","1970-01-22","Ukraine","Clojure"),
    Person(90,"Shane","Bradley","1983-07-16","Belgium","Python"),
    Person(91,"Martha","Huff","1988-07-28","Finland","Rust"),
    Person(92,"Reginald","Drake","1982-05-22","Kazakhstan","Rust"),
    Person(93,"Karen","Bailey","1986-06-20","Guyana","Clojure"),
    Person(94,"Brandy","Morrow","1984-05-03","Cape Verde","Java"),
    Person(95,"Cheryl","Perry","2001-04-09","Guyana","Java"),
    Person(96,"Connie","Pope","1986-04-18","Timor-Leste","Rust"),
    Person(97,"Darlene","Curtis","1989-05-09","Tokelau","Rust"),
    Person(98,"Nancy","Stone","1992-07-10","Tajikistan","Rust"),
    Person(99,"Adrienne","Cameron","1974-02-17","Norway","Java"),
    Person(100,"Katie","Lawson","1999-07-18","Brazil","Kotlin"),
)