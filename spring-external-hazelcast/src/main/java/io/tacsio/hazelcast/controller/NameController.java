package io.tacsio.hazelcast.controller;

import com.hazelcast.map.IMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@RestController
public class NameController {

    private IMap<String, String> nameMap;

    public NameController(IMap<String, String> nameMap) {
        this.nameMap = nameMap;
    }

    @PutMapping("/names")
    public ResponseEntity putName(@RequestBody NameRequest request) {
        nameMap.put(request.getKey(), request.getValue());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/names/{key}")
    public ResponseEntity getName(@PathVariable("key") String key) {
        String value = nameMap.get(key);
        return ResponseEntity.ok(value);
    }

}

class NameRequest {
    @NotBlank
    private String key;
    @NotBlank
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}