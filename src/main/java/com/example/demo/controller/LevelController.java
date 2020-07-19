package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.entity.Level;
import com.example.demo.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class LevelController {

    @Autowired
    LevelRepository levelRepository;

    @GetMapping("/levels")
    public ResponseEntity<List <Level>> getAllLevel()
            throws ResourceNotFoundException {
        List<Level> levels = levelRepository.findAll();
        if (levels.isEmpty()) {
            new ResourceNotFoundException("Don't have level");
        }
        return new ResponseEntity<>(levels, HttpStatus.OK);
    }

    @GetMapping("/levels/{id}")
    public ResponseEntity<Level> getLevelById(@PathVariable(value = "id") Long levelID)
            throws ResourceNotFoundException {

        Level level = levelRepository.findById(levelID)
                .orElseThrow(() -> new ResourceNotFoundException("Không tồn tại level này: " + levelID));

        return ResponseEntity.ok().body(level);
    }

    @PostMapping("/levels")
    public Level createLevel(@Valid @RequestBody Level level) {
        return levelRepository.save(level);
    }

    @PutMapping("/levels/{id}")
    public ResponseEntity<Level> updateLevel(@PathVariable(value = "id") Long levelID,
                                                   @Valid @RequestBody Level levelDetails) throws ResourceNotFoundException {
        Level level = levelRepository.findById(levelID)
                .orElseThrow(() -> new ResourceNotFoundException("Level này không tồn tại: " + levelID));
        level.setName(levelDetails.getName());
        level.setImage(levelDetails.getImage());

        final Level updatedLevel = levelRepository.save(level);

        return ResponseEntity.ok(updatedLevel);
    }

    @DeleteMapping("/levels/{id}")
    public Map<String, Boolean> deleteLevel(@PathVariable(value = "id") Long levelId)
            throws ResourceNotFoundException {

        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new ResourceNotFoundException("Nhân viên này không tồn tại: " + levelId));

        levelRepository.delete(level);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Delete successfully!", Boolean.TRUE);

        return response;
    }
}
