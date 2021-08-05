package io.confluent.samples.httpsink;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.sink.ErrantRecordReporter;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class HttpSinkTask extends SinkTask {

    Properties props = new Properties();
    private ErrantRecordReporter reporter;

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void start(Map<String, String> map) {
        props.putAll(map);
        try {
            reporter = context.errantRecordReporter();
        } catch (NoClassDefFoundError e) {
            reporter = null;
        }
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        log.info("Received a collection of records of size {}", collection.size());
        for (SinkRecord record : collection) {
            log.info("Processing records {}", record.toString());
            callRestService(record);
        }
    }

    private void callRestService(SinkRecord record) {
        try {
            URL url = new URL(props.getProperty("http.url") + "/" + record.key());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");

            OutputStream os = connection.getOutputStream();
            os.write(((String) record.value()).getBytes(StandardCharsets.UTF_8));
            os.flush();

            log.info("Response code: {}", connection.getResponseCode());
            if (connection.getResponseCode() != 200) {
                if (reporter != null) {
                    log.info("reporting error.");
                    this.context.errantRecordReporter().report(
                            record,
                            new RuntimeException("Received unexpected response code: " + connection.getResponseCode())
                    ).get();
                } else {
                    throw new ConnectException("failed on record with result code " + connection.getResponseCode());
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            log.info(e.toString());
            log.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
    }

}
