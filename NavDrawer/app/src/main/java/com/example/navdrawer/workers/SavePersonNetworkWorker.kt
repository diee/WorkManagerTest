package com.example.navdrawer.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.navdrawer.utils.SerializerHelper
import com.example.navdrawer.data.Person

const val PERSON_IN = "value_passed"
const val PERSON_OUT = "person_out"
private const val LOG_TAG = "TEST Diego WorkManager"

class SavePersonNetworkWorker(
    ctx: Context,
    params: WorkerParameters
) : Worker(ctx, params) {
    override fun doWork(): Result = try {
        Thread.sleep(5000)
        var objectTest = Person()

        inputData.getString(PERSON_IN)?.let { objectTest = SerializerHelper.deserializeJson(it) }

        Log.v(LOG_TAG, "Success: -  object: $objectTest")
        Result.success(createOutputData(objectTest))
    } catch (e: Throwable) {
        Result.failure()
    }


    private fun createOutputData(person: Person): Data {
        return Data.Builder()
            .putString(PERSON_OUT, SerializerHelper.serializeToJson(person))
            .build()
    }

}