package io.github.t1geryan.domain.exceptions

import io.github.t1geryan.domain.models.CategoryId

class CategoryHasRelationsException(
    val categoryId: CategoryId,
) : AppException("Category can't be deleted since it has related transactions")