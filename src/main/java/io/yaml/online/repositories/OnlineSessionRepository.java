package io.yaml.online.repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.yaml.online.beans.OnlineSession;

@ApplicationScoped
public class OnlineSessionRepository implements PanacheRepository<OnlineSession> { }
