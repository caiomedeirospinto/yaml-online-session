package io.yaml.online.beans;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@RegisterForReflection
@EqualsAndHashCode
@Entity
@Table(name = "progress_fields")
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "field",
  "firstState",
  "secondState"
})
public class ProgressField {
  
  @Column(name = "field")
  @JsonProperty("field")
  @Getter @Setter
  private String field;
  
  @Column(name = "firstState")
  @JsonProperty("firstState")
  @Getter @Setter
  private String firstState;
  
  @Column(name = "secondState")
  @JsonProperty("secondState")
  @Getter @Setter
  private String secondState;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="procesarId", nullable = false)
  @JsonbTransient
  @Getter @Setter
  private Procesar procesar;
}
