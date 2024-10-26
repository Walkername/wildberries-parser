package ru.wildberries.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wildberries.analytics.services.ParseService;

@RestController
@CrossOrigin
@RequestMapping("/parser")
public class ParseController {

    private final ParseService parseService;

    @Autowired
    public ParseController(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping("/url")
    public ResponseEntity<HttpStatus> parse(
            @RequestBody String jsonUrl
    ) {
        parseService.parse(jsonUrl);
        return ResponseEntity.ok(HttpStatus.OK);
    }


}
