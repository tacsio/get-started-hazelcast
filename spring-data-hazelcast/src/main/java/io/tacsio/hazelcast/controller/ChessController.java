package io.tacsio.hazelcast.controller;

import com.hazelcast.map.IMap;
import com.hazelcast.query.Predicates;
import io.tacsio.hazelcast.config.PlayerParser;
import io.tacsio.hazelcast.model.ChessPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
public class ChessController {
    @Autowired
    private IMap<String, ChessPlayer> playersMap;

    private static Comparator<Map.Entry<String, ChessPlayer>> blitzRating() {
        return Comparator.comparing(e -> e.getValue().getBlitzRating());
    }

    @GetMapping("/load")
    public String loadData() throws IOException, URISyntaxException {
        Path path = Path.of(getClass().getClassLoader().getResource("players.txt").toURI());
        Map<String, ChessPlayer> players = Files.lines(path)
                .map(PlayerParser::parse)
                .collect(Collectors.toMap(ChessPlayer::getId, Function.identity()));

        playersMap.putAll(players);

        return "data loaded";
    }

    @GetMapping("/count")
    public String count() {
        return String.format("Current: %s players", playersMap.keySet().size());
    }

    @GetMapping("/young")
    public ResponseEntity young() {
        Collection<ChessPlayer> values = playersMap.values(Predicates.sql("age < 21"));

        return ResponseEntity.ok(values);
    }

    @GetMapping("/name")
    public ResponseEntity queryName(@RequestParam("like") String name) {
        Collection<ChessPlayer> players = playersMap.values(
                Predicates.ilike("name", "%" + name + "%"));

        return ResponseEntity.ok(players);

    }

    @GetMapping("/classic")
    public ResponseEntity topClassic() {
        Collection<ChessPlayer> values = playersMap.values(
                Predicates.pagingPredicate(
//                        Predicates.equal("ratings[any].title", "Classic"),
                        10));

        return ResponseEntity.ok(values);
    }

    @GetMapping("/blitz")
    public ResponseEntity topBlitz() {
        Collection<ChessPlayer> values = playersMap.values(
                Predicates.pagingPredicate(blitzRating().reversed(), 10));

        return ResponseEntity.ok(values);
    }
}
