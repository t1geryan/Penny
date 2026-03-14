package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Amount
import javax.inject.Inject

interface ValidateAmountUseCase {

    operator fun invoke(amount: Amount): Boolean
}

class ValidateAmountUseCaseImpl @Inject constructor(): ValidateAmountUseCase {

    override fun invoke(amount: Amount): Boolean {
        return amount.valueInCurrency != 0.0f
    }
}
