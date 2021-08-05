package io.confluent.samples.httpsink;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Importance;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.source.SourceConnector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")
public class HttpSinkConnector extends SourceConnector {

  public static final String HTTP_URL = "http.url";
  public static final String HTTP_URL_DOC = "REST API url";

  private static final ConfigDef CONFIG_DEF = new ConfigDef()
          .define(HTTP_URL, Type.STRING, null, Importance.HIGH, HTTP_URL_DOC);

  private String http_url;

  @Override
  public void start(Map<String, String> props) {
    AbstractConfig parsedConfig = new AbstractConfig(CONFIG_DEF, props);
    http_url = parsedConfig.getString(HTTP_URL);
  }

  @Override
  public Class<? extends Task> taskClass() {
    return HttpSinkTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int i) {
    ArrayList<Map<String, String>> configs = new ArrayList<>();
    Map<String, String> config = new HashMap<>();
    config.put(HTTP_URL, http_url);
    configs.add(config);
    return configs;
  }

  @Override
  public void stop() {
  }

  @Override
  public ConfigDef config() {
    return CONFIG_DEF;
  }

  @Override
  public String version() {
    return "1.0";
  }
}
