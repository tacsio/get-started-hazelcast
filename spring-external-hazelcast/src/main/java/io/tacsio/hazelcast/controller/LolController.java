package io.tacsio.hazelcast.controller;

import com.github.javafaker.Faker;
import com.hazelcast.collection.IList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("lol")
public class LolController {

    private IList<String> champions;
    private Faker faker;

    public LolController(IList<String> champions) {
        this.champions = champions;
        this.faker = new Faker();
    }

    @GetMapping
    public ResponseEntity getNames() {
        List<String> names = this.champions.stream().sorted().collect(Collectors.toList());
        return ResponseEntity.ok(names);
    }

    @GetMapping("/new")
    public ResponseEntity generate() {
        String champion = faker.leagueOfLegends().champion();
        champions.add(champion);

        return ResponseEntity.ok(champion);
    }

}