package io.yaml.online.beans;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "custom_fields")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "displayName",
  "key"
})
public class CustomField extends PanacheEntity {

  @Column(name = "display_name")
  @JsonProperty("displayName")
  @Getter @Setter
  private String displayName;

  @Column(name = "key_field")
  @JsonProperty("key")
  @Getter @Setter
  private String key;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "procesar_id", nullable = false)
  @JsonbTransient
  @Getter @Setter
  private Procesar procesar;
}
