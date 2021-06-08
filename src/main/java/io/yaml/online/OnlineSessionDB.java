package io.yaml.online;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;

import io.quarkus.runtime.annotations.RegisterForReflection;
import io.yaml.online.beans.OnlineSession;
@RegisterForReflection
@ApplicationScoped
public class OnlineSessionDB {

  public OnlineSession findById(Long id) {
    return OnlineSession.findById(id);
  }

  @Transactional
  public void saveItems(Long sessionId, String items) {
    OnlineSession onlineSession = OnlineSession.findById(sessionId);
    onlineSession.getProcesar().setItems(items);
    onlineSession.persist();
    onlineSession.flush();
  }
}
