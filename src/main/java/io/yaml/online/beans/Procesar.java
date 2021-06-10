package io.yaml.online.beans;

import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
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
@Table(name = "procesars")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "id",
  "items",
  "idField",
  "nameField",
  "customFields",
  "progressField"
})
public class Procesar extends PanacheEntity {

  @Lob
  @Column(name = "items", columnDefinition = "TEXT")
  @JsonProperty("items")
  @Getter @Setter
  private String items;

  @Column(name = "id_field")
  @JsonProperty("idField")
  @Getter @Setter
  private String idField;

  @Column(name = "name_field")
  @JsonProperty("nameField")
  @Getter @Setter
  private String nameField;

  @OneToMany(mappedBy = "procesar", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonProperty("customFields")
  @Getter @Setter
  private List<CustomField> customFields;

  @OneToOne(mappedBy = "procesar", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonProperty("progressField")
  @Getter @Setter
  private ProgressField progressField;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="online_session_id", nullable = false)
  @JsonbTransient
  @Getter @Setter
  private OnlineSession onlineSession;
}
