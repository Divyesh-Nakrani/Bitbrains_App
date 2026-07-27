package com.bitcoding.bitbrains.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AlertDao_Impl implements AlertDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AlertEntity> __insertionAdapterOfAlertEntity;

  private final SharedSQLiteStatement __preparedStmtOfSetAction;

  private final SharedSQLiteStatement __preparedStmtOfMarkSynced;

  public AlertDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlertEntity = new EntityInsertionAdapter<AlertEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR IGNORE INTO `alerts` (`messageId`,`event`,`senderName`,`priority`,`messageText`,`chatId`,`chatType`,`cooldownSeconds`,`sentAt`,`receivedAt`,`escalatedAt`,`receivedAtLocal`,`action`,`actionSynced`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final AlertEntity entity) {
        if (entity.getMessageId() == null) {
          statement.bindNull(1);
        } else {
          statement.bindString(1, entity.getMessageId());
        }
        if (entity.getEvent() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getEvent());
        }
        if (entity.getSenderName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getSenderName());
        }
        if (entity.getPriority() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getPriority());
        }
        if (entity.getMessageText() == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.getMessageText());
        }
        if (entity.getChatId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getChatId());
        }
        if (entity.getChatType() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getChatType());
        }
        if (entity.getCooldownSeconds() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getCooldownSeconds());
        }
        if (entity.getSentAt() == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.getSentAt());
        }
        if (entity.getReceivedAt() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getReceivedAt());
        }
        if (entity.getEscalatedAt() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getEscalatedAt());
        }
        statement.bindLong(12, entity.getReceivedAtLocal());
        if (entity.getAction() == null) {
          statement.bindNull(13);
        } else {
          statement.bindString(13, entity.getAction());
        }
        final int _tmp = entity.getActionSynced() ? 1 : 0;
        statement.bindLong(14, _tmp);
      }
    };
    this.__preparedStmtOfSetAction = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alerts SET action = ?, actionSynced = ? WHERE messageId = ?";
        return _query;
      }
    };
    this.__preparedStmtOfMarkSynced = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE alerts SET actionSynced = 1 WHERE messageId = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final AlertEntity alert, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfAlertEntity.insertAndReturnId(alert);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object setAction(final String messageId, final String action, final boolean synced,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfSetAction.acquire();
        int _argIndex = 1;
        if (action == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, action);
        }
        _argIndex = 2;
        final int _tmp = synced ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 3;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfSetAction.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object markSynced(final String messageId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfMarkSynced.acquire();
        int _argIndex = 1;
        if (messageId == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, messageId);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfMarkSynced.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object findById(final String messageId,
      final Continuation<? super AlertEntity> $completion) {
    final String _sql = "SELECT * FROM alerts WHERE messageId = ? LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (messageId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, messageId);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<AlertEntity>() {
      @Override
      @Nullable
      public AlertEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfMessageText = CursorUtil.getColumnIndexOrThrow(_cursor, "messageText");
          final int _cursorIndexOfChatId = CursorUtil.getColumnIndexOrThrow(_cursor, "chatId");
          final int _cursorIndexOfChatType = CursorUtil.getColumnIndexOrThrow(_cursor, "chatType");
          final int _cursorIndexOfCooldownSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "cooldownSeconds");
          final int _cursorIndexOfSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "sentAt");
          final int _cursorIndexOfReceivedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "receivedAt");
          final int _cursorIndexOfEscalatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "escalatedAt");
          final int _cursorIndexOfReceivedAtLocal = CursorUtil.getColumnIndexOrThrow(_cursor, "receivedAtLocal");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfActionSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "actionSynced");
          final AlertEntity _result;
          if (_cursor.moveToFirst()) {
            final String _tmpMessageId;
            if (_cursor.isNull(_cursorIndexOfMessageId)) {
              _tmpMessageId = null;
            } else {
              _tmpMessageId = _cursor.getString(_cursorIndexOfMessageId);
            }
            final String _tmpEvent;
            if (_cursor.isNull(_cursorIndexOfEvent)) {
              _tmpEvent = null;
            } else {
              _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
            }
            final String _tmpSenderName;
            if (_cursor.isNull(_cursorIndexOfSenderName)) {
              _tmpSenderName = null;
            } else {
              _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            }
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            final String _tmpMessageText;
            if (_cursor.isNull(_cursorIndexOfMessageText)) {
              _tmpMessageText = null;
            } else {
              _tmpMessageText = _cursor.getString(_cursorIndexOfMessageText);
            }
            final String _tmpChatId;
            if (_cursor.isNull(_cursorIndexOfChatId)) {
              _tmpChatId = null;
            } else {
              _tmpChatId = _cursor.getString(_cursorIndexOfChatId);
            }
            final String _tmpChatType;
            if (_cursor.isNull(_cursorIndexOfChatType)) {
              _tmpChatType = null;
            } else {
              _tmpChatType = _cursor.getString(_cursorIndexOfChatType);
            }
            final String _tmpCooldownSeconds;
            if (_cursor.isNull(_cursorIndexOfCooldownSeconds)) {
              _tmpCooldownSeconds = null;
            } else {
              _tmpCooldownSeconds = _cursor.getString(_cursorIndexOfCooldownSeconds);
            }
            final String _tmpSentAt;
            if (_cursor.isNull(_cursorIndexOfSentAt)) {
              _tmpSentAt = null;
            } else {
              _tmpSentAt = _cursor.getString(_cursorIndexOfSentAt);
            }
            final String _tmpReceivedAt;
            if (_cursor.isNull(_cursorIndexOfReceivedAt)) {
              _tmpReceivedAt = null;
            } else {
              _tmpReceivedAt = _cursor.getString(_cursorIndexOfReceivedAt);
            }
            final String _tmpEscalatedAt;
            if (_cursor.isNull(_cursorIndexOfEscalatedAt)) {
              _tmpEscalatedAt = null;
            } else {
              _tmpEscalatedAt = _cursor.getString(_cursorIndexOfEscalatedAt);
            }
            final long _tmpReceivedAtLocal;
            _tmpReceivedAtLocal = _cursor.getLong(_cursorIndexOfReceivedAtLocal);
            final String _tmpAction;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmpAction = null;
            } else {
              _tmpAction = _cursor.getString(_cursorIndexOfAction);
            }
            final boolean _tmpActionSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActionSynced);
            _tmpActionSynced = _tmp != 0;
            _result = new AlertEntity(_tmpMessageId,_tmpEvent,_tmpSenderName,_tmpPriority,_tmpMessageText,_tmpChatId,_tmpChatType,_tmpCooldownSeconds,_tmpSentAt,_tmpReceivedAt,_tmpEscalatedAt,_tmpReceivedAtLocal,_tmpAction,_tmpActionSynced);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object recent(final int limit, final Continuation<? super List<AlertEntity>> $completion) {
    final String _sql = "SELECT * FROM alerts ORDER BY receivedAtLocal DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<AlertEntity>>() {
      @Override
      @NonNull
      public List<AlertEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMessageId = CursorUtil.getColumnIndexOrThrow(_cursor, "messageId");
          final int _cursorIndexOfEvent = CursorUtil.getColumnIndexOrThrow(_cursor, "event");
          final int _cursorIndexOfSenderName = CursorUtil.getColumnIndexOrThrow(_cursor, "senderName");
          final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "priority");
          final int _cursorIndexOfMessageText = CursorUtil.getColumnIndexOrThrow(_cursor, "messageText");
          final int _cursorIndexOfChatId = CursorUtil.getColumnIndexOrThrow(_cursor, "chatId");
          final int _cursorIndexOfChatType = CursorUtil.getColumnIndexOrThrow(_cursor, "chatType");
          final int _cursorIndexOfCooldownSeconds = CursorUtil.getColumnIndexOrThrow(_cursor, "cooldownSeconds");
          final int _cursorIndexOfSentAt = CursorUtil.getColumnIndexOrThrow(_cursor, "sentAt");
          final int _cursorIndexOfReceivedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "receivedAt");
          final int _cursorIndexOfEscalatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "escalatedAt");
          final int _cursorIndexOfReceivedAtLocal = CursorUtil.getColumnIndexOrThrow(_cursor, "receivedAtLocal");
          final int _cursorIndexOfAction = CursorUtil.getColumnIndexOrThrow(_cursor, "action");
          final int _cursorIndexOfActionSynced = CursorUtil.getColumnIndexOrThrow(_cursor, "actionSynced");
          final List<AlertEntity> _result = new ArrayList<AlertEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final AlertEntity _item;
            final String _tmpMessageId;
            if (_cursor.isNull(_cursorIndexOfMessageId)) {
              _tmpMessageId = null;
            } else {
              _tmpMessageId = _cursor.getString(_cursorIndexOfMessageId);
            }
            final String _tmpEvent;
            if (_cursor.isNull(_cursorIndexOfEvent)) {
              _tmpEvent = null;
            } else {
              _tmpEvent = _cursor.getString(_cursorIndexOfEvent);
            }
            final String _tmpSenderName;
            if (_cursor.isNull(_cursorIndexOfSenderName)) {
              _tmpSenderName = null;
            } else {
              _tmpSenderName = _cursor.getString(_cursorIndexOfSenderName);
            }
            final String _tmpPriority;
            if (_cursor.isNull(_cursorIndexOfPriority)) {
              _tmpPriority = null;
            } else {
              _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
            }
            final String _tmpMessageText;
            if (_cursor.isNull(_cursorIndexOfMessageText)) {
              _tmpMessageText = null;
            } else {
              _tmpMessageText = _cursor.getString(_cursorIndexOfMessageText);
            }
            final String _tmpChatId;
            if (_cursor.isNull(_cursorIndexOfChatId)) {
              _tmpChatId = null;
            } else {
              _tmpChatId = _cursor.getString(_cursorIndexOfChatId);
            }
            final String _tmpChatType;
            if (_cursor.isNull(_cursorIndexOfChatType)) {
              _tmpChatType = null;
            } else {
              _tmpChatType = _cursor.getString(_cursorIndexOfChatType);
            }
            final String _tmpCooldownSeconds;
            if (_cursor.isNull(_cursorIndexOfCooldownSeconds)) {
              _tmpCooldownSeconds = null;
            } else {
              _tmpCooldownSeconds = _cursor.getString(_cursorIndexOfCooldownSeconds);
            }
            final String _tmpSentAt;
            if (_cursor.isNull(_cursorIndexOfSentAt)) {
              _tmpSentAt = null;
            } else {
              _tmpSentAt = _cursor.getString(_cursorIndexOfSentAt);
            }
            final String _tmpReceivedAt;
            if (_cursor.isNull(_cursorIndexOfReceivedAt)) {
              _tmpReceivedAt = null;
            } else {
              _tmpReceivedAt = _cursor.getString(_cursorIndexOfReceivedAt);
            }
            final String _tmpEscalatedAt;
            if (_cursor.isNull(_cursorIndexOfEscalatedAt)) {
              _tmpEscalatedAt = null;
            } else {
              _tmpEscalatedAt = _cursor.getString(_cursorIndexOfEscalatedAt);
            }
            final long _tmpReceivedAtLocal;
            _tmpReceivedAtLocal = _cursor.getLong(_cursorIndexOfReceivedAtLocal);
            final String _tmpAction;
            if (_cursor.isNull(_cursorIndexOfAction)) {
              _tmpAction = null;
            } else {
              _tmpAction = _cursor.getString(_cursorIndexOfAction);
            }
            final boolean _tmpActionSynced;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfActionSynced);
            _tmpActionSynced = _tmp != 0;
            _item = new AlertEntity(_tmpMessageId,_tmpEvent,_tmpSenderName,_tmpPriority,_tmpMessageText,_tmpChatId,_tmpChatType,_tmpCooldownSeconds,_tmpSentAt,_tmpReceivedAt,_tmpEscalatedAt,_tmpReceivedAtLocal,_tmpAction,_tmpActionSynced);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
