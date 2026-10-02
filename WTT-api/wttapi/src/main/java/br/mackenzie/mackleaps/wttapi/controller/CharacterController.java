package br.mackenzie.mackleaps.wttapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.mackenzie.mackleaps.wttapi.model.CharacterDTO;
import br.mackenzie.mackleaps.wttapi.service.CharacterService;

@RestController
@RequestMapping("/api")
public class CharacterController {

    CharacterService characterService;

    public CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }
    
    @GetMapping("/character/{name}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable String name) {
        CharacterDTO character = characterService.getCharacter(name);

        if (character == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(character);
    }

    @GetMapping("/allCharacters")
    public ResponseEntity<List<CharacterDTO>> getAllCharacters() {
        return ResponseEntity.ok(characterService.getAllCharacters());
    } 
}
