package io.yaml.online.decoders;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import io.yaml.online.Message;

public class MessageDecoder implements Decoder.Text<Message> {

  private static Jsonb jsonb = JsonbBuilder.create();

  @Override
  public void init(EndpointConfig config) { }

  @Override
  public void destroy() { }

  @Override
  public Message decode(String message) throws DecodeException {
    return jsonb.fromJson(message, Message.class);
  }

  @Override
  public boolean willDecode(String s) {
    return true;
  }
  
}
