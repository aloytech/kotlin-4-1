package ru.netology

import org.junit.Test

import org.junit.Assert.*

class MainKtTest {

    @Test
    fun transferFeeForVkIsZeroWithDefaultParams() {
        //arrange
        val amount = 200 * 100
        val expectedFee = 1
        //act
        val actualFee = transferFee(amount = amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun transferFeeForMMCardBelowFreeRangeWithDefaultParam() {
        //arrange
        val type = MMCARD
        val amount = 200 * 100
        val expectedFee = (amount * feeMM).toInt() + constAddMM
        //act
        val actualFee = transferFee(type = type, amount = amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun transferFeeForMMCardAboveFreeRangeWithDefaultParam() {
        //arrange
        val type = MMCARD
        val amount = 1000 * 100
        val monthSum = hiFeeLimitMM + 1000 * 100
        val expectedFee = (amount * feeMM).toInt() + constAddMM
        //act
        val actualFee = transferFee(type, monthSum, amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun transferFeeForMMCardWithinFreeRangeIsZero() {
        //arrange
        val type = MMCARD
        val amount = 200 * 100
        val monthSum = hiFeeLimitMM - 1000 * 100
        val expectedFee = 0
        //act
        val actualFee = transferFee(type, monthSum, amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun transferFeeForVisaMirAboveMinLimit() {
        //arrange
        val type = VISAMIR
        val amount = 200 * 100
        val monthSum = 20000 * 100
        val expectedFee = minFeeVisaMir
        //act
        val actualFee = transferFee(type, monthSum, amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun transferFeeForVisaMirNormalFee() {
        //arrange
        val type = VISAMIR
        val amount = 20000 * 100
        val monthSum = 20000 * 100
        val expectedFee = (amount * feeVisaMir).toInt()
        //act
        val actualFee = transferFee(type, monthSum, amount)
        //assert
        assertEquals(expectedFee, actualFee)
    }

    @Test
    fun outTransferFeeVkAboveDayLimitWithDefaultParams() {
        //arrange
        val amount = 20000 * 100
        val expectedOut = "Сумма перевода Vk превышает установленный лимит"
        //act
        val actualOut = outTransferFee(amount = amount)
        //assert
        assertEquals(expectedOut, actualOut)
    }

    @Test
    fun outTransferFeeVkAboveMonthLimitWithDefaultParams() {
        //arrange
        val amount = 10000 * 100
        val monthSum = maxMonthTransferCard - 5000 * 100
        val expectedOut = "Сумма перевода Vk превышает лимит в месяц"
        //act
        val actualOut = outTransferFee(monthSum = monthSum, amount = amount)
        //assert
        assertEquals(expectedOut, actualOut)
    }

    @Test
    fun outTransferFeeCardAboveDayLimit() {
        //arrange
        val type = MMCARD
        val amount = 10000 * 100
        val monthSum = 35000 * 100
        val daySum = maxDayTransferCard - 5000 * 100
        val expectedOut = "Сумма перевода по карте превышает дневной лимит"
        //act
        val actualOut = outTransferFee(type, monthSum, daySum, amount)
        //assert
        assertEquals(expectedOut, actualOut)
    }

    @Test
    fun outTransferFeeCardAboveMonthLimit() {
        //arrange
        val type = VISAMIR
        val amount = 10000 * 100
        val monthSum = maxMonthTransferCard - 5000 * 100
        val daySum = 5000 * 100
        val expectedOut = "Сумма перевода по карте превышает лимит в меся"
        //act
        val actualOut = outTransferFee(type, monthSum, daySum, amount)
        //assert
        assertEquals(expectedOut, actualOut)
    }
}