package com.bitcoding.bitbrains.data.remote;

/**
 * Every endpoint the app touches (MOBILE_APP_INTEGRATION.md §6).
 * NOTE: /login is intentionally absent — it is a browser 302 and must only be
 * opened in a Chrome Custom Tab, never via Retrofit (§6.3).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000j\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J2\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00032\b\b\u0003\u0010\f\u001a\u00020\r2\b\b\u0003\u0010\u000e\u001a\u00020\u000f2\b\b\u0003\u0010\u0010\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u0011J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J(\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u00032\b\b\u0001\u0010\u0016\u001a\u00020\r2\b\b\u0001\u0010\u0017\u001a\u00020\u0018H\u00a7@\u00a2\u0006\u0002\u0010\u0019J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u0003H\u00a7@\u00a2\u0006\u0002\u0010\u0005J\u001e\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00032\b\b\u0001\u0010\u0017\u001a\u00020\u001eH\u00a7@\u00a2\u0006\u0002\u0010\u001fJ\u001e\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u00032\b\b\u0001\u0010\u0017\u001a\u00020\"H\u00a7@\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, d2 = {"Lcom/bitcoding/bitbrains/data/remote/ApiService;", "", "authStatus", "Lretrofit2/Response;", "Lcom/bitcoding/bitbrains/data/remote/dto/AuthStatusResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "health", "Lcom/bitcoding/bitbrains/data/remote/dto/HealthResponse;", "listDevices", "Lcom/bitcoding/bitbrains/data/remote/dto/DevicesResponse;", "listMessages", "Lcom/bitcoding/bitbrains/data/remote/dto/MessagesResponse;", "ackStatus", "", "limit", "", "offset", "(Ljava/lang/String;IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "", "postMessageAction", "Lcom/bitcoding/bitbrains/data/remote/dto/MessageActionResponse;", "messageId", "body", "Lcom/bitcoding/bitbrains/data/remote/dto/MessageActionRequest;", "(Ljava/lang/String;Lcom/bitcoding/bitbrains/data/remote/dto/MessageActionRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "profile", "Lcom/bitcoding/bitbrains/data/remote/dto/ProfileResponse;", "registerDevice", "Lcom/bitcoding/bitbrains/data/remote/dto/RegisterDeviceResponse;", "Lcom/bitcoding/bitbrains/data/remote/dto/RegisterDeviceRequest;", "(Lcom/bitcoding/bitbrains/data/remote/dto/RegisterDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "unregisterDevice", "Lcom/bitcoding/bitbrains/data/remote/dto/UnregisterDeviceResponse;", "Lcom/bitcoding/bitbrains/data/remote/dto/UnregisterDeviceRequest;", "(Lcom/bitcoding/bitbrains/data/remote/dto/UnregisterDeviceRequest;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface ApiService {
    
    @retrofit2.http.GET(value = "health")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object health(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.HealthResponse>> $completion);
    
    @retrofit2.http.GET(value = "auth/status")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object authStatus(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.AuthStatusResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/devices/register")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object registerDevice(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/devices/unregister")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object unregisterDevice(@retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.dto.UnregisterDeviceRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.UnregisterDeviceResponse>> $completion);
    
    @retrofit2.http.GET(value = "api/devices")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listDevices(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.DevicesResponse>> $completion);
    
    @retrofit2.http.POST(value = "api/messages/{messageId}/action")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object postMessageAction(@retrofit2.http.Path(value = "messageId")
    @org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @retrofit2.http.Body()
    @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.dto.MessageActionRequest body, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.MessageActionResponse>> $completion);
    
    @retrofit2.http.GET(value = "messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object listMessages(@retrofit2.http.Query(value = "ack_status")
    @org.jetbrains.annotations.NotNull()
    java.lang.String ackStatus, @retrofit2.http.Query(value = "limit")
    int limit, @retrofit2.http.Query(value = "offset")
    int offset, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.MessagesResponse>> $completion);
    
    @retrofit2.http.GET(value = "profile")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object profile(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<com.bitcoding.bitbrains.data.remote.dto.ProfileResponse>> $completion);
    
    @retrofit2.http.POST(value = "logout")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object logout(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super retrofit2.Response<kotlin.Unit>> $completion);
    
    /**
     * Every endpoint the app touches (MOBILE_APP_INTEGRATION.md §6).
     * NOTE: /login is intentionally absent — it is a browser 302 and must only be
     * opened in a Chrome Custom Tab, never via Retrofit (§6.3).
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}