package com.deneb.david.astroapp.data.model

/**
 * Created by alnit on 30/01/2018.
 */
data class ImageOfTheDay(
        val date:String,
        val image:String,
        val resourceUri:String
)
data class ImagesOfTheDay(val images:List<ImagesOfTheDay>)
