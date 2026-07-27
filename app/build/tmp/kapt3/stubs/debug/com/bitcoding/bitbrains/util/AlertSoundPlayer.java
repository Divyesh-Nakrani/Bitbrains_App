package com.bitcoding.bitbrains.util;

/**
 * Plays an alarm tone on loop and vibrates hard until stop() is called (i.e.
 * until the employee taps a button).
 *
 * Loudness strategy — a critical alert must be impossible to miss:
 * - Play on the ALARM stream (USAGE_ALARM). The alarm stream keeps sounding when
 *   the phone is on silent/vibrate, unlike ring/notification.
 * - Force the alarm stream to MAX volume for the duration, then restore the
 *   user's previous level on stop(). This is the real fix for "too quiet" — the
 *   MediaPlayer's own volume is only relative to the stream volume, so a low
 *   system alarm level makes everything quiet no matter what.
 * - Grab audio focus so any other audio ducks out of the way.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0002J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\b\u0010\u0018\u001a\u00020\u0013H\u0002J\u0006\u0010\u0019\u001a\u00020\u0013J\b\u0010\u001a\u001a\u00020\u0013H\u0002J\u0006\u0010\u001b\u001a\u00020\u0013R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0011\u00a8\u0006\u001c"}, d2 = {"Lcom/bitcoding/bitbrains/util/AlertSoundPlayer;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "alarmAttributes", "Landroid/media/AudioAttributes;", "audioManager", "Landroid/media/AudioManager;", "getAudioManager", "()Landroid/media/AudioManager;", "focusRequest", "Landroid/media/AudioFocusRequest;", "mediaPlayer", "Landroid/media/MediaPlayer;", "previousAlarmVolume", "", "Ljava/lang/Integer;", "abandonAudioFocus", "", "getVibrator", "Landroid/os/Vibrator;", "maximizeAlarmVolume", "requestAudioFocus", "restoreAlarmVolume", "start", "startVibration", "stop", "app_debug"})
public final class AlertSoundPlayer {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.Nullable()
    private android.media.MediaPlayer mediaPlayer;
    @org.jetbrains.annotations.Nullable()
    private android.media.AudioFocusRequest focusRequest;
    @org.jetbrains.annotations.Nullable()
    private java.lang.Integer previousAlarmVolume;
    @org.jetbrains.annotations.NotNull()
    private final android.media.AudioAttributes alarmAttributes = null;
    
    public AlertSoundPlayer(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final android.media.AudioManager getAudioManager() {
        return null;
    }
    
    public final void start() {
    }
    
    public final void stop() {
    }
    
    /**
     * Push the ALARM stream to its maximum, remembering the old level to restore.
     */
    private final void maximizeAlarmVolume() {
    }
    
    private final void restoreAlarmVolume() {
    }
    
    private final void startVibration() {
    }
    
    private final void requestAudioFocus() {
    }
    
    private final void abandonAudioFocus() {
    }
    
    private final android.os.Vibrator getVibrator() {
        return null;
    }
}