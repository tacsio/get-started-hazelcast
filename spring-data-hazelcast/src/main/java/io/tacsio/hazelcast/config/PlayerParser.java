package io.tacsio.hazelcast.config;

import io.tacsio.hazelcast.model.ChessPlayer;
import io.tacsio.hazelcast.model.Rating;

import java.util.Set;

public class PlayerParser {

    public static ChessPlayer parse(String line) {
        var split = line.split(";");

        var name = split[1];
        var classic = Integer.parseInt(split[2]);
        var rapid = Integer.parseInt(split[3]);
        var blitz = Integer.parseInt(split[4]);
        var age = Integer.parseInt(split[5]);


        var player = new ChessPlayer(name, age);
        var ratingClassic = new Rating(player.getId(), "Classic", classic);
        var ratingRapid = new Rating(player.getId(), "Rapid", rapid);
        var ratingBlitz = new Rating(player.getId(), "Blitz", blitz);

        player.setRatings(Set.of(ratingClassic, ratingRapid, ratingBlitz));

        return player;
    }
}
