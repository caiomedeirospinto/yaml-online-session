package io.yaml.online;

import java.beans.Encoder;
import java.util.Date;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@RegisterForReflection
@AllArgsConstructor
public class Message extends Encoder {
  
  @Getter @Setter
  private String key;

  @Getter @Setter
  private String value;

  @Getter @Setter
  private Date date;
}
