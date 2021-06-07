package io.yaml.online.repositories;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.runtime.annotations.RegisterForReflection;
import io.yaml.online.beans.OnlineSession;

@RegisterForReflection
@ApplicationScoped
public class OnlineSessionRepository implements PanacheRepository<OnlineSession> { }
