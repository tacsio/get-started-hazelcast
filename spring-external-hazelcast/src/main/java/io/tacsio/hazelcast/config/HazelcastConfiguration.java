package io.tacsio.hazelcast.config;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.tacsio.hazelcast.model.ChessPlayer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelcastConfiguration {

    @Value("${hazelcast.ip}")
    private String HAZELCAST_IP;

    @Value("${hazelcast.port}")
    private String HAZELCAST_PORT;


    @Bean
    public ClientConfig clientConfig(ClientNetworkConfig networkConfig) {
        return new ClientConfig()
                .setClusterName("dev")
                .setNetworkConfig(networkConfig);
    }

    @Bean
    public ClientNetworkConfig networkConfig() {
        String address = String.format("%s:%s", HAZELCAST_IP, HAZELCAST_PORT);
        return new ClientNetworkConfig().addAddress(address);
    }

    @Bean
    public HazelcastInstance hazelcastInstance(ClientConfig clientConfig) {
        return HazelcastClient.newHazelcastClient(clientConfig);
    }

    @Bean
    public IMap<String, String> nameMap(HazelcastInstance instance) {
        return instance.getMap("nameMap");
    }

    @Bean
    public IMap<String, ChessPlayer> hazelcastMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("players");
    }
}
