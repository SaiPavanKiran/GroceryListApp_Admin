package com.rspk.adminapp.constants

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

val mappedTypeToSubType =
    mapOf(
        "Fruits" to
                listOf("pome fruits", "citrus fruits", "berries", "stone fruits", "melons", "tropical fruits", "exotic fruits"),
        "Vegetables" to
                listOf("alliums", "leafy vegetables", "root vegetables", "cruciferous vegetables", "marrow vegetables", "legumes", "gourds", "herbs"),
        "Dairy" to
                listOf("milk products", "cheese varieties", "yogurts", "creams", "butters", "dairy alternatives"),
        "Bakery" to
               listOf("bread varieties", "buns & rolls", "pastries", "muffins", "cakes", "pies & tarts", "cookies"),
        "Beverages" to
               listOf("cool drinks", "sodas", "juices", "teas", "coffees", "smoothies", "energy drinks","water"),
        "Snacks" to
               listOf("chips", "nuts & seeds", "popcorn", "fruit snacks", "granola bars", "pretzels", "candy", "cookies"),
        "Cleaning" to
                listOf("surface cleaners", "dish cleaners", "laundry supplies", "glass cleaners", "bathroom cleaners", "disinfectants", "tools & accessories"),
        "Nuts,Pulses" to
                listOf("tree nuts", "beans", "lentils", "peas", "soybeans", "chickpeas", "peanuts"),
        "Spices" to
                listOf("ground spices", "whole spices", "herbs & seasonings", "spice mixes", "pepper varieties", "curry blends", "ginger & garlic"),
        "Flours" to
                listOf("wheat flours", "gluten-free flours", "nut flours", "corn flours", "rice flours", "specialty flours"),
        "Pooja" to
                listOf("incense", "ghee", "flowers", "camphor", "puja powders", "sacred threads", "offerings", "holy water"),
        "Others" to
                listOf("fatty fish", "cooking oils", "vinegar", "pasta", "rice", "sauces", "dressings", "soup mixes")

    )


val type_Item_List =
    listOf(
        "Fruits",
        "Vegetables",
        "Dairy",
        "Bakery",
        "Beverages",
        "Snacks",
        "Cleaning",
        "Nuts,Pulses",
        "Spices",
        "Flours",
        "Pooja",
        "Others"
    )

val quantitiesList =
    listOf("kg", "ltr", "packets", "bottles", "pcs")

val starCount =
    listOf("0","0.5","1","1.5","2","2.5","3","3.5","4","4.5","5")
