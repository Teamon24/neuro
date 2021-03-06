package com.home.bot

import com.home.bot.SourceImitation.SourceImitationConfigs.INTERVAL
import com.home.bot.SourceImitation.SourceImitationConfigs.cost
import com.home.bot.SourceImitation.SourceImitationConfigs.durationMillis
import com.home.bot.SourceImitation.SourceImitationConfigs.now
import com.home.utils.Time.SECOND
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import javafx.application.Platform
import org.joda.time.DateTime
import kotlin.math.cos
import kotlin.random.Random

class SourceImitation(private val source: PublishSubject<Point<Long, Double>>) {

    object SourceImitationConfigs {
        val INTERVAL: Long = (0.18 * SECOND).toLong()

        val seconds = 50000
        val N = 10
        val M = 3
        val W = 0.0001
        val SHFT = Math.PI
        val durationMillis = SECOND * seconds

        val now = { DateTime() }

        val cost = { time: Long ->
            val m = Random.nextInt(M)
            N* cos(W * time) + m * cos(W * time - SHFT )
        }
    }


    fun emmitting() {
        try {
            val connectionMoment = DateTime()
            val end = DateTime(connectionMoment.millis + durationMillis)
            while (now() < end) {
                Thread.sleep(INTERVAL)
                Platform.runLater {
                    source.onNext(timeAndCost())
                }
            }
            source.onComplete()
        } catch (e: Exception) {
            source.onError(e)
        }
    }

    private fun timeAndCost(): Point<Long, Double> {
        val time = DateTime().millis
        val cost = cost(time)
        return Point(time, cost)
    }
}