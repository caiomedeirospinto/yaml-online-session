package io.yaml.online.beans;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

@RegisterForReflection
@Entity
@Table(name = "online_sessions")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "procesar"
})
public class OnlineSession extends PanacheEntity{

  private static Jsonb jsonb = JsonbBuilder.create();

  @OneToOne(mappedBy = "onlineSession", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonProperty("procesar")
  @Getter @Setter
  private Procesar procesar;

  public String toJson() {
    return jsonb.toJson(this);
  }
}
