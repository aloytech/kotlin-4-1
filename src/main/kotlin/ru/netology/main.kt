package ru.netology

const val VKPAY = "Vk Pay"
const val MMCARD = "mastercard & maestro"
const val VISAMIR = "visa & mir"

val lowFeeLimitMM = 300 * 100
val hiFeeLimitMM = 75000 * 100
val feeMM = 0.006
val constAddMM = 20 * 100

val feeVisaMir = 0.0075
val minFeeVisaMir = 35 * 100

val maxDayTransferCard = 150000 * 100
val maxMonthTransferCard = 600000 * 100

val maxTransferVK = 15000 * 100
val maxMonthTransferVK = 40000 * 100

fun main() {
    println(outTransferFee(amount = 200 * 100))
}

fun transferFee(type: String = VKPAY, monthSum: Int = 0, amount: Int): Int {
    return when {
        type == MMCARD && monthSum !in lowFeeLimitMM..hiFeeLimitMM -> (amount * feeMM).toInt() + constAddMM
        type == VISAMIR -> if ((amount * feeVisaMir).toInt() >= minFeeVisaMir) (amount * feeVisaMir).toInt() else minFeeVisaMir
        else -> 0
    }
}

fun outTransferFee(type: String = VKPAY, monthSum: Int = 0, daySum: Int = 0, amount: Int):String {
    when {
        type == VKPAY && amount > maxTransferVK -> {
            return "Сумма перевода Vk превышает установленный лимит"
        }
        type == VKPAY && (monthSum + amount) > maxMonthTransferVK -> {
            return "Сумма перевода Vk превышает лимит в месяц"
        }
        type !== VKPAY && (daySum + amount) > maxDayTransferCard -> {
            return "Сумма перевода по карте превышает дневной лимит"
        }
        type !== VKPAY && (monthSum + amount) > maxMonthTransferCard -> {
            return "Сумма перевода по карте превышает лимит в месяц"
        }
        else -> {
            val transferFee = transferFee(type, monthSum, amount)
            val transferFeeRub = transferFee / 100
            val transferFeeKop = transferFee % 100
            val amountRub = amount / 100
            val amountKop = amount % 100
            return "При переводе $amountRub" + '\u20BD' + " $amountKop коп. " +
                        "комиссия составит $transferFeeRub" + '\u20BD' + " $transferFeeKop коп."
        }
    }
}