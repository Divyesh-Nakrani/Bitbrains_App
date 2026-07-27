package com.bitcoding.bitbrains.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager

/**
 * Plays an alarm tone on loop and vibrates hard until stop() is called (i.e.
 * until the employee taps a button).
 *
 * Loudness strategy — a critical alert must be impossible to miss:
 *  - Play on the ALARM stream (USAGE_ALARM). The alarm stream keeps sounding when
 *    the phone is on silent/vibrate, unlike ring/notification.
 *  - Force the alarm stream to MAX volume for the duration, then restore the
 *    user's previous level on stop(). This is the real fix for "too quiet" — the
 *    MediaPlayer's own volume is only relative to the stream volume, so a low
 *    system alarm level makes everything quiet no matter what.
 *  - Grab audio focus so any other audio ducks out of the way.
 */
class AlertSoundPlayer(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private var focusRequest: AudioFocusRequest? = null
    private var previousAlarmVolume: Int? = null

    private val audioManager: AudioManager
        get() = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private val alarmAttributes: AudioAttributes =
        AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

    fun start() {
        stop() // guard against double-start

        maximizeAlarmVolume()
        requestAudioFocus()

        // Prefer a real alarm tone; fall back to ringtone, then notification.
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(alarmAttributes)
            setDataSource(context, soundUri)
            isLooping = true
            setVolume(1f, 1f)
            prepare()
            start()
        }

        startVibration()
    }

    fun stop() {
        mediaPlayer?.let {
            runCatching { if (it.isPlaying) it.stop() }
            it.release()
        }
        mediaPlayer = null
        getVibrator().cancel()
        abandonAudioFocus()
        restoreAlarmVolume()
    }

    /** Push the ALARM stream to its maximum, remembering the old level to restore. */
    private fun maximizeAlarmVolume() {
        runCatching {
            val am = audioManager
            val max = am.getStreamMaxVolume(AudioManager.STREAM_ALARM)
            if (previousAlarmVolume == null) {
                previousAlarmVolume = am.getStreamVolume(AudioManager.STREAM_ALARM)
            }
            am.setStreamVolume(AudioManager.STREAM_ALARM, max, 0)
        }
    }

    private fun restoreAlarmVolume() {
        val prev = previousAlarmVolume ?: return
        runCatching {
            audioManager.setStreamVolume(AudioManager.STREAM_ALARM, prev, 0)
        }
        previousAlarmVolume = null
    }

    private fun startVibration() {
        val vibrator = getVibrator()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Full-amplitude buzz: 600ms on (255), 400ms off — repeats.
            val timings = longArrayOf(0, 600, 400)
            val amplitudes = intArrayOf(0, 255, 0)
            val effect = VibrationEffect.createWaveform(timings, amplitudes, 0)
            vibrator.vibrate(effect, alarmAttributes)
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 600, 400), 0)
        }
    }

    private fun requestAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val request = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(alarmAttributes)
                .build()
            focusRequest = request
            audioManager.requestAudioFocus(request)
        } else {
            @Suppress("DEPRECATION")
            audioManager.requestAudioFocus(
                null, AudioManager.STREAM_ALARM, AudioManager.AUDIOFOCUS_GAIN
            )
        }
    }

    private fun abandonAudioFocus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            focusRequest?.let { audioManager.abandonAudioFocusRequest(it) }
            focusRequest = null
        } else {
            @Suppress("DEPRECATION")
            audioManager.abandonAudioFocus(null)
        }
    }

    private fun getVibrator(): Vibrator {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val manager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            manager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
    }
}
