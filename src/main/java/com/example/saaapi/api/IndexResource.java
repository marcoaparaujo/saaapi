package com.example.saaapi.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexResource {

    @GetMapping()
    public String get() {
        return "SAA API";
    }
}
