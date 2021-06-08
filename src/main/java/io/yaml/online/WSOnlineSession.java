package io.yaml.online;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import io.yaml.online.beans.OnlineSession;
import io.yaml.online.decoders.MessageDecoder;
import io.yaml.online.encoders.MessageEncoder;

import org.jboss.logging.Logger;

import javax.websocket.Session;

@ServerEndpoint( value = "/online-session/{id}/{username}", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
@ApplicationScoped
public class WSOnlineSession {

  @Inject
  OnlineSessionDB onlineSessionDB;

  Map<String, Session> sessions = new ConcurrentHashMap<>(); 

  private static final Logger logger = Logger.getLogger(WSOnlineSession.class);

  @OnOpen
  public void onOpen(Session session, @PathParam("id") Long sessionId, @PathParam("username") String username) {
    sessions.put(sessionId.toString()+username, session);
    logger.debug("Trying to get session: " + sessionId.toString());
    try {
      OnlineSession onlineSession = onlineSessionDB.findById(sessionId);
      message(session, new Message("init", onlineSession.toJson(), new Date()));
      logger.debug("User connected " + username + " into session " + sessionId.toString());
      broadcast(new Message("joined", username, new Date()));
    } catch (Exception e) {
      logger.error("Session could not be found " + sessionId.toString() + ", error: " + e.getMessage());
      e.printStackTrace();
      message(session, new Message("init", "404", new Date()));
    }
  }

  @OnClose
  public void onClose(Session session, @PathParam("id") Long sessionId, @PathParam("username") String username) {
    logger.debug("User disconnected " + username + " from session " + sessionId.toString());
    sessions.remove(sessionId.toString()+username);
    broadcast(new Message("left", username, new Date()));
  }

  @OnError
  public void onError(Session session, @PathParam("id") String sessionId, @PathParam("username") String username, Throwable throwable) {
    logger.error("User disconnected " + username + " from session " + sessionId.toString() + " by error: " + throwable.getMessage());
    throwable.printStackTrace();
    sessions.remove(sessionId.toString()+username);
    broadcast(new Message("left-on-error", username, new Date()));
  }

  @OnMessage
  public void onMessage(Message message, @PathParam("id") Long sessionId, @PathParam("username") String username) {
    if (message.getKey().equals("changed")) {
      onlineSessionDB.saveItems(sessionId, message.getValue());
      message.setDate(new Date());
      logger.debug("Item updated by " + username + " from session " + sessionId.toString() + ": " + message.getValue());
      broadcast(message);
    }
  }

  private void broadcast(Message message) {
    sessions.values().forEach(s -> {
      s.getAsyncRemote().sendObject(message, result ->  {
        if (result.getException() != null) {
          logger.error("Unable to send message: " + result.getException());
        }
      });
    });
  }

  private void message(Session session, Message message) {
    session.getAsyncRemote().sendObject(message, result ->  {
      if (result.getException() != null) {
        logger.error("Unable to send message: " + result.getException());
      }
    });
  }
}
