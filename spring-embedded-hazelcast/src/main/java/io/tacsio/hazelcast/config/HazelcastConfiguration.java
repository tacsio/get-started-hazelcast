package io.tacsio.hazelcast.config;

import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import io.tacsio.hazelcast.model.ChessPlayer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@Configuration
public class HazelcastConfiguration {

    @Bean
    public HazelcastInstance hazelcastInstance(Config hazelcastConfig) {
        return Hazelcast.newHazelcastInstance(hazelcastConfig);
    }

    @Bean
    public Config hazelcastConfig(NetworkConfig networkConfig,
                                  ManagementCenterConfig managementCenterConfig,
                                  Map<String, CacheSimpleConfig> cacheConfigs) {

        return new Config()
                .setClusterName("dev")
                .setNetworkConfig(networkConfig)
                .setManagementCenterConfig(managementCenterConfig)
                .setCacheConfigs(cacheConfigs)
                ;
    }

    @Bean
    public Map<String, CacheSimpleConfig> cacheConfigs() {
        var configs = new HashMap<String, CacheSimpleConfig>();
        configs.put("default", new CacheSimpleConfig().setBackupCount(1));

        return configs;
    }

    @Bean
    public NetworkConfig networkConfig() {
        return new NetworkConfig()
                .setInterfaces(new InterfacesConfig().addInterface("0.0.0.0"))
                .setRestApiConfig(new RestApiConfig().setEnabled(true));
    }

    @Bean
    public ManagementCenterConfig managementCenterConfig() {
        return new ManagementCenterConfig()
                .setTrustedInterfaces(
                        Set.of("*", "152.168.0.100")
                );
    }

    @Bean
    public IMap<String, ChessPlayer> hazelcastMap(HazelcastInstance hazelcastInstance) {
        return hazelcastInstance.getMap("players");
    }
}
