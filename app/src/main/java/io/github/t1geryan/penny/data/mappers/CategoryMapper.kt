package io.github.t1geryan.penny.data.mappers

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.penny.data.database.entities.CategoryEntity

object CategoryMapper {

    fun fromEntityToModel(categoryEntity: CategoryEntity) = Category(
        id = categoryEntity.id,
        name = categoryEntity.name,
        color = categoryEntity.color,
        emoji = categoryEntity.emoji,
        limit = categoryEntity.limit,
        currency = categoryEntity.currency,
    )

    fun fromModelToEntity(category: Category) = CategoryEntity(
        id = category.id,
        name = category.name,
        color = category.color,
        emoji = category.emoji,
        limit = category.limit,
        currency = category.currency,
    )
}
