package com.samentic.youtubeclient.core.data.db

import androidx.room.TypeConverter
import java.math.BigInteger

class BigIntegerConverter {
    @TypeConverter
    fun fromBigIntegerToString(value: BigInteger): String {
        return value.toString(10)
    }

    @TypeConverter
    fun toBigIntegerFromString(value: String): BigInteger {
        return BigInteger(value)
    }
}