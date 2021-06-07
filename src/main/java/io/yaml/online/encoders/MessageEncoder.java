package io.yaml.online.encoders;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import io.yaml.online.Message;

public class MessageEncoder implements Encoder.Text<Message> {

  private static Jsonb jsonb = JsonbBuilder.create();

  @Override
  public void init(EndpointConfig config) { }

  @Override
  public void destroy() { }

  @Override
  public String encode(Message message) throws EncodeException {
    return jsonb.toJson(message);
  }
}
