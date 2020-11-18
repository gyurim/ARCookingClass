package com.example.arcookingclass.data.maker

import androidx.room.Room

object RecipeGenerator {
    fun generateData(){
        val dummyDataList = mutableListOf<HashMap<String, Any>>()

        dummyDataList.add(
            hashMapOf(
                "name" to "Pajeon",
                "ingredient" to "Pa"
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Tteokbokki",
                "ingredient" to "RiceCake"
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "EggRoll",
                "ingredient" to "Egg"
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "KongGukSu",
                "ingredient" to "Bean"
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Bulgogi",
                "ingredient" to "Beef"
            )
        )

        dummyDataList.add(
            hashMapOf(
                "name" to "Japchae",
                "ingredient" to "Noodle"
            )
        )

    }
}