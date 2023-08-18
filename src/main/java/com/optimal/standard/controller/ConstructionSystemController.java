package com.optimal.standard.controller;

import com.optimal.standard.dto.ConstructionSystemDTO;
import com.optimal.standard.dto.ResponseConstructionSystemDTO;
import com.optimal.standard.service.ConstructionSystemService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ConstructionSystemController {

    private final ConstructionSystemService constructionSystemService;

    @GetMapping("/user/construction_system")
    public ResponseEntity<List<ConstructionSystemDTO>> findAll() {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemService.findAll());
    }

    @GetMapping("/admin/construction_system/{id}")
    public ResponseEntity<ResponseConstructionSystemDTO> findById(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(this.constructionSystemService.findById(id));
    }

    @PostMapping("/admin/construction_system")
    public void create(@RequestBody @Valid ConstructionSystemDTO request) {
        this.constructionSystemService.saveConstructionSystem(request);
    }

    @PutMapping("/admin/construction_system/{id}")
    public void update(@PathVariable Long id, @RequestBody @Valid ConstructionSystemDTO request) {
        this.constructionSystemService.updateConstructionSystem(id, request);
    }

}
