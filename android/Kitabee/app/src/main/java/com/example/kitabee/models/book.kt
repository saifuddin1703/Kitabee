package com.example.kitabee.models

import com.google.gson.annotations.SerializedName


/**
 _id	int
title	varchar(100)
description	varchar(200)
price	int
author	varchar(100)
edition	varchar(100)
publisher	varchar(100)
title_image	varchar(100)
buyer_id	int
seller_id	int

 */

data class book(
    @SerializedName("_id") val _id : Int = 0,
    @SerializedName("title") val title : String = "",
    @SerializedName("description") val description : String = "",
    @SerializedName("price") val price : Int = 0,
    @SerializedName("author") val author : String = "",
    @SerializedName("edition") val edition : String = "",
    @SerializedName("publisher") val publisher : String = "",
    @SerializedName("title_image") val titleImage : String = "",
    @SerializedName("buyer_id") val buyerId : Int =0,
    @SerializedName("seller_id") val sellerId : Int = 0
)
