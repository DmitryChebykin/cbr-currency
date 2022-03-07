package com.example.cbrcurrency.controller;

import com.example.cbrcurrency.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegularController {
    private final ModelService modelService;

    @GetMapping("/main")
    public String getMainPage(Model model){
        model = modelService.getModelForMainGetRequest(model);
        return "main";
    }

    @PostMapping("/main")
    public String getResultPage(Model model){
        model = modelService.getModelForConversionResult(model);
        return "redirect:result";
    }
}
