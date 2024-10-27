package ru.wildberries.analytics.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.wildberries.analytics.services.ParseService;

@RestController
@CrossOrigin
@RequestMapping("/parse")
public class ParseController {

    private final ParseService parseService;

    @Autowired
    public ParseController(ParseService parseService) {
        this.parseService = parseService;
    }

    @PostMapping("")
    public ResponseEntity<HttpStatus> parse(
            @RequestBody String jsonUrl
    ) {
        double start = System.currentTimeMillis();
        parseService.parse(jsonUrl);
        double end = System.currentTimeMillis();
        System.out.println((end - start) / 1000); // 20-30 sec for 100 products - slow
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
