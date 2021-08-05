package io.confluent.samples.httpsink;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;


@Slf4j
public class HttpSinkTask extends SinkTask {

  Properties props = new Properties();
  private boolean waitPoll = false;

  public HttpSinkTask() {
    RestClient restClient = new RestClient();
  }

  @Override
  public String version() {
    return "1.0";
  }

  @Override
  public void start(Map<String, String> map) {
    props.putAll(map);
  }

  @Override
  public void put(Collection<SinkRecord> collection) {
    for (SinkRecord record : collection) {
      try {
        URL url = new URL("http://www.example.com/customers");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/xml");

        OutputStream os = connection.getOutputStream();
        os.write("foo".getBytes(StandardCharsets.UTF_8));
        os.flush();

        connection.getResponseCode();
        connection.disconnect();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      }
    } @Override
  public void stop() {
  }

}
