package com.example.products.mapper

import com.example.products.model.Product
import com.example.products.model.Rating
import com.example.products.module.ProductDto
import com.example.products.module.RatingDto


internal fun ProductDto.mapToProduct(): Product {
    return Product(
        id = this.id,
        title = this.title,
        price = this.price,
        description = this.description,
        category = this.category,
        image = this.image,
        rating = this.ratingDto?.mapToRating()
    )
}

internal fun RatingDto.mapToRating(): Rating {
    return Rating(
        rate = this.rate,
        count = this.count
    )
}