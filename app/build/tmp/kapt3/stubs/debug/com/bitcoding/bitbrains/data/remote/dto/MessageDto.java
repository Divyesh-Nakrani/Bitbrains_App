package com.bitcoding.bitbrains.data.remote.dto;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0087\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u0012\b\b\u0002\u0010\t\u001a\u00020\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010!\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010\"\u001a\u0004\u0018\u00010\u0010H\u00c6\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010$\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\bH\u00c6\u0003J\t\u0010\'\u001a\u00020\nH\u00c6\u0003J\u000b\u0010(\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u000b\u0010*\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u008b\u0001\u0010+\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00c6\u0001J\u0013\u0010,\u001a\u00020\n2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010.\u001a\u00020/H\u00d6\u0001J\t\u00100\u001a\u00020\u0003H\u00d6\u0001R\u0013\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u000b\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0015R\u0018\u0010\u0005\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0015R\u0018\u0010\u0004\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0015R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0015R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0015R\u0016\u0010\t\u001a\u00020\n8\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u001bR\u0018\u0010\u000e\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0015R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0018\u0010\r\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0015\u00a8\u00061"}, d2 = {"Lcom/bitcoding/bitbrains/data/remote/dto/MessageDto;", "", "id", "", "graphMessageId", "graphChatId", "chatType", "sender", "Lcom/bitcoding/bitbrains/data/remote/dto/SenderDto;", "isFromMe", "", "body", "importance", "sentAt", "receivedAt", "acknowledgement", "Lcom/bitcoding/bitbrains/data/remote/dto/AcknowledgementDto;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/bitcoding/bitbrains/data/remote/dto/SenderDto;ZLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Lcom/bitcoding/bitbrains/data/remote/dto/AcknowledgementDto;)V", "getAcknowledgement", "()Lcom/bitcoding/bitbrains/data/remote/dto/AcknowledgementDto;", "getBody", "()Ljava/lang/String;", "getChatType", "getGraphChatId", "getGraphMessageId", "getId", "getImportance", "()Z", "getReceivedAt", "getSender", "()Lcom/bitcoding/bitbrains/data/remote/dto/SenderDto;", "getSentAt", "component1", "component10", "component11", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class MessageDto {
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String id = null;
    @com.google.gson.annotations.SerializedName(value = "graph_message_id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String graphMessageId = null;
    @com.google.gson.annotations.SerializedName(value = "graph_chat_id")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String graphChatId = null;
    @com.google.gson.annotations.SerializedName(value = "chat_type")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String chatType = null;
    @org.jetbrains.annotations.Nullable()
    private final com.bitcoding.bitbrains.data.remote.dto.SenderDto sender = null;
    @com.google.gson.annotations.SerializedName(value = "is_from_me")
    private final boolean isFromMe = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String body = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String importance = null;
    @com.google.gson.annotations.SerializedName(value = "sent_at")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String sentAt = null;
    @com.google.gson.annotations.SerializedName(value = "received_at")
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String receivedAt = null;
    @org.jetbrains.annotations.Nullable()
    private final com.bitcoding.bitbrains.data.remote.dto.AcknowledgementDto acknowledgement = null;
    
    public MessageDto(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String graphMessageId, @org.jetbrains.annotations.Nullable()
    java.lang.String graphChatId, @org.jetbrains.annotations.Nullable()
    java.lang.String chatType, @org.jetbrains.annotations.Nullable()
    com.bitcoding.bitbrains.data.remote.dto.SenderDto sender, boolean isFromMe, @org.jetbrains.annotations.Nullable()
    java.lang.String body, @org.jetbrains.annotations.Nullable()
    java.lang.String importance, @org.jetbrains.annotations.Nullable()
    java.lang.String sentAt, @org.jetbrains.annotations.Nullable()
    java.lang.String receivedAt, @org.jetbrains.annotations.Nullable()
    com.bitcoding.bitbrains.data.remote.dto.AcknowledgementDto acknowledgement) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGraphMessageId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGraphChatId() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getChatType() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bitcoding.bitbrains.data.remote.dto.SenderDto getSender() {
        return null;
    }
    
    public final boolean isFromMe() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getBody() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getImportance() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSentAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getReceivedAt() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bitcoding.bitbrains.data.remote.dto.AcknowledgementDto getAcknowledgement() {
        return null;
    }
    
    public MessageDto() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component1() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bitcoding.bitbrains.data.remote.dto.AcknowledgementDto component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final com.bitcoding.bitbrains.data.remote.dto.SenderDto component5() {
        return null;
    }
    
    public final boolean component6() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.remote.dto.MessageDto copy(@org.jetbrains.annotations.Nullable()
    java.lang.String id, @org.jetbrains.annotations.Nullable()
    java.lang.String graphMessageId, @org.jetbrains.annotations.Nullable()
    java.lang.String graphChatId, @org.jetbrains.annotations.Nullable()
    java.lang.String chatType, @org.jetbrains.annotations.Nullable()
    com.bitcoding.bitbrains.data.remote.dto.SenderDto sender, boolean isFromMe, @org.jetbrains.annotations.Nullable()
    java.lang.String body, @org.jetbrains.annotations.Nullable()
    java.lang.String importance, @org.jetbrains.annotations.Nullable()
    java.lang.String sentAt, @org.jetbrains.annotations.Nullable()
    java.lang.String receivedAt, @org.jetbrains.annotations.Nullable()
    com.bitcoding.bitbrains.data.remote.dto.AcknowledgementDto acknowledgement) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}