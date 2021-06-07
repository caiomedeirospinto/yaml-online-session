package io.yaml.online;

import javax.websocket.Session;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketPool {

  private static Map<String, Session> onlineSession = new ConcurrentHashMap<>();

  private WebSocketPool() {}

  public static void createOnlineSession(String userId, Session session) {
    onlineSession.put(userId,session);
    broadcast("User Connected: " + userId);
  }

  public static Map<String,Session> getOnlineSession(){
    return onlineSession;
  }

  public static Session getSesssionByUserId(String userId) {
    return Optional.ofNullable(onlineSession.get(userId)).orElse(null);
  }

  public static void removeSession(String userId) {
    Session session = onlineSession.get(userId);
    if(session == null) {
      return;
    }
    try {
      session.close();
      onlineSession.remove(userId);
      broadcast("User Disconnected: " + userId);
    } catch (IOException e) {
      System.out.println("An error occurred while closing the connection");
    }
  }

  public static void broadcast(String message) {
    onlineSession.values().stream().forEach(s -> {
      s.getAsyncRemote().sendObject(message, result ->  {
        if (result.getException() != null) {
          System.out.println("Unable to send message: " + result.getException());
        }
      });
    });
  }
}