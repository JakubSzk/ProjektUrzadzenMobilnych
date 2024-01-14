package com.example.lista4.data

import kotlin.random.*

class Exercise(val content: String, val points: Int)
class Subject(val subjectName: String)
class ExerciseList(val exercises: List<Exercise>, val subject: Subject, val grade: Double, val nrLis: Int)
class Results(val subject: Subject, val avg: Double, val amount: Int)
class Generator
{
    private val subjects = listOf("matematyka", "pum", "fizyka", "elektronika", "algorytmy")
    public var listOfLists = listOf<ExerciseList>()
    public var listOfResults = listOf<Results>()
    public var klikniety = 0
    public fun refreshData(amount: Int)
    {
        val returnable = mutableListOf<ExerciseList>()
        for(x in 1..amount)
        {
            val setOfExercises = mutableListOf<Exercise>()
            for(y in 1 .. Random.nextInt(1, 11))
            {
                setOfExercises.add(Exercise("Exercise nr $y content.", Random.nextInt(1, 11)))
            }
            val sub = subjects[Random.nextInt(0,5)]
            returnable.add(ExerciseList(setOfExercises.toList(),Subject(sub), Random.nextInt(6, 11) * 0.5, returnable.count { it.subject.subjectName == sub } + 1))
        }
        listOfLists = returnable.toList()
        val returnableResults = mutableListOf<Results>()
        for(x in subjects)
        {
            var avg = 0.0
            var amounter = 0
            for(y in listOfLists)
            {
                if(y.subject.subjectName == x)
                {
                    avg += y.grade
                    amounter ++
                }
            }
            if(amounter != 0)
            {
                avg /= amounter
                returnableResults.add(Results(Subject(x), avg, amounter))
            }
        }
        listOfResults = returnableResults.toList()

    }
}