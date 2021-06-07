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

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.yaml.online.beans.OnlineSession;
import io.yaml.online.decoders.MessageDecoder;
import io.yaml.online.encoders.MessageEncoder;
import io.yaml.online.repositories.OnlineSessionRepository;

import javax.websocket.Session;

@RegisterForReflection
@ServerEndpoint( value = "/online-session/{id}/{username}", encoders = { MessageEncoder.class }, decoders = { MessageDecoder.class })
@ApplicationScoped
public class WSOnlineSession {

  @Inject
  OnlineSessionRepository repository;

  Map<String, Session> sessions = new ConcurrentHashMap<>(); 

  @OnOpen
  public void onOpen(Session session, @PathParam("id") String sessionId, @PathParam("username") String username) {
    sessions.put(sessionId+username, session);
    OnlineSession onlineSession = repository.findById(Long.parseLong(sessionId));
    message(session, new Message("init", onlineSession.toJson(), new Date()));
    broadcast(new Message("joined", username, new Date()));
  }

  @OnClose
  public void onClose(Session session, @PathParam("id") String sessionId, @PathParam("username") String username) {
    sessions.remove(sessionId+username);
    broadcast(new Message("left", username, new Date()));
  }

  @OnError
  public void onError(Session session, @PathParam("id") String sessionId, @PathParam("username") String username, Throwable throwable) {
    sessions.remove(sessionId+username);
    broadcast(new Message("left-on-error", username, new Date()));
  }

  @OnMessage
  public void onMessage(Message message, @PathParam("id") String sessionId, @PathParam("username") String username) {
    OnlineSession onlineSession = repository.findById(Long.parseLong(sessionId));
    onlineSession.getProcesar().setItems(message.getValue());
    repository.persist(onlineSession);
    repository.flush();
    message.setDate(new Date());
    broadcast(message);
  }

  private void broadcast(Message message) {
    sessions.values().forEach(s -> {
      s.getAsyncRemote().sendObject(message, result ->  {
        if (result.getException() != null) {
          System.out.println("Unable to send message: " + result.getException());
        }
      });
    });
  }

  private void message(Session session, Message message) {
    session.getAsyncRemote().sendObject(message, result ->  {
      if (result.getException() != null) {
        System.out.println("Unable to send message: " + result.getException());
      }
    });
  }
}
