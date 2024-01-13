package com.example.lista3.model
import kotlin.random.*

class QestionData {
    private val questionsList = listOf(
        Question("Jaki jest skrótowy zapis języka programowania Python?", "a) PY", "b) PYPL", "c) P", "d) PYT", 4),
        Question("Co oznacza skrót IDE w kontekście programowania?", "a) Integrated Development Environment", "b) Internet Data Exchange", "c) Intermediate Data Execution", "d) Intelligent Design for Electronics", 1),
        Question("Które z poniższych jest językiem programowania?", "a) HTML", "b) CSS", "c) Java", "d) SQL", 3),
        Question("Co to jest Git?", "a) System zarządzania bazą danych", "b) System kontroli wersji", "c) Framework JavaScript", "d) Protokół komunikacyjny", 2),
        Question("Która z poniższych struktur danych jest typem LIFO?", "a) Queue", "b) Stack", "c) Linked List", "d) Tree", 2),
        Question("Jakie jest rozszerzenie plików Kotlin","kot","kn","kt","ko", 3),
        Question("Jaka data to Święta Bożego Narodzenia?","24 grudnia","25 grudnia","11 grudnia","23 grudnia", 2),
        Question("Ptak który nazywa się jak owoc to?","kiwi","rzepa","figa","winogron", 1),
        Question("Na zewnątrz lub inaczej","W stój","W pole","Na pole","Na dwór", 4),
        Question("Jakim zwierzęciem jest znana postać z Czeskiej bajki?","dzikiem","kretem","wilkiem","gęsią", 2),
        Question("Jak nazywa się metalowa końcówka sznurówki?","spójka","nie ma nazwy","aglet","igla", 3),
        Question("Skąd pochodził papież Polak","z Wadowic","z Wrocławia","z Warszawy","z Kędzierzyna", 1),
        Question("Jak nazywa się język którym posługują się Amerykanie","Szkocki","Amerykański","Hiszpański","Angielski", 4),
        Question("Jaki jest skrót przedmiotu do którego to jest zadanie?","WPUMKJ","PUM","RPIS","MFizAK", 2),
        Question("\"Suma natężeń prądów wpływających jest równa sumie natężeń prądów wypływających z węzła.\" to? ","I Prędkość kosmiczna","II Prawo Kirchoffa","I Prawo Kirchoffa","I Prawo Newtona", 3),
        Question("Butterfly to po Polsku?","motyl","masło","nóż do smarowania masła","oliwa", 1),
        Question("Gdzie się myje ręce w łazience?","w umywalce","w zlewie","w pralce","w toalecie", 1),
        Question("Gdzie się myje ręce w kuchni?","w zmywarce","na stole","w umywalce","w zlewie", 4),
        Question("Jakie jest rozszerzenie plików c++","c++","cpp","c2p","cp", 2),
        Question("Ile bitów wynosi jeden bajt?", "a) 4", "b) 8", "c) 16", "d) 32", 2)

    )
    public fun refreshData(amount: Int)
    {
        if(amount > 20)
            return
        val lista = mutableListOf<Question>()
        val doneNr = mutableListOf<Int>()
        for(x in 1 .. amount)
        {
            var completed = false
            while(!completed)
            {
                val choosen = Random.nextInt(0, 20)
                if (!(choosen in doneNr))
                {
                    completed = true
                    doneNr.add(choosen)
                    lista.add(questionsList[choosen])
                }
            }
        }
        questions = lista.toList()
    }
    public var questions = listOf<Question>()
}