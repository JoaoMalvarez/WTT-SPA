package br.mackenzie.mackleaps.wttapi.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import br.mackenzie.mackleaps.wttapi.model.CharacterDTO;
import jakarta.annotation.PostConstruct;

@Service
public class CharacterService {

    List<CharacterDTO> allCharacterDTOs;
    
    @Value("${api.baseUrl}")
    private String baseUrl;

    @PostConstruct
    private void createCharacters() {

        // CRIANDO YODA
        Map<String, String> yodaStats = Map.of(
                    "intelligence", "90",
                    "strength", "25",
                    "speed", "80",
                    "durability", "75",
                    "power", "85",
                    "combat", "100"
        );
        CharacterDTO yodaDTO = new CharacterDTO("Yoda", baseUrl + "/images/yodaImage.jpg", "#16a34a",yodaStats);
        
        // CRIANDO O DARTH VADER

        Map<String, String> vaderStats = Map.of(
                    "intelligence", "85",
                    "strength", "50",
                    "speed", "85",
                    "durability", "70",
                    "power", "90",
                    "combat", "100"
        );
        CharacterDTO vaderDTO = new CharacterDTO("Darth Vader", baseUrl + "/images/vaderImage.webp", "#dc2626",vaderStats);

        // CRIANDO O XEWBACCA

        Map<String, String> chewStats = Map.of(
                    "intelligence", "65",
                    "strength", "26",
                    "speed", "27",
                    "durability", "50",
                    "power", "47",
                    "combat", "100"
        );
        CharacterDTO chewDTO = new CharacterDTO("Chewbacca", baseUrl + "/images/chewImage.webp", "#92400e",chewStats);
        
        // Criando Bobba

        Map<String, String> bobaStats = Map.of(
                        "intelligence", "70",
                        "strength", "25",
                        "speed", "20",
                        "durability", "25",
                        "power", "30",
                        "combat", "50"
            );
        CharacterDTO bobaDTO = new CharacterDTO("Boba Fett", baseUrl + "/images/bobaImage.webp", "#059669",bobaStats);
        
        // Criando R2D2
        Map<String, String> r2d2Stats = Map.of(
                        "intelligence", "75",
                        "strength", "1",
                        "speed", "1",
                        "durability", "20",
                        "power", "7",
                        "combat", "10"
            );
        CharacterDTO r2d2 = new CharacterDTO("R2-D2", baseUrl + "/images/r2d2.jpg", "#2563eb",r2d2Stats);

        // Criando Grievous
        Map<String, String> grievousStats = Map.of(
                        "intelligence", "65",
                        "strength", "25",
                        "speed", "80",
                        "durability", "50",
                        "power", "45",
                        "combat", "100"
            );
        CharacterDTO grievous = new CharacterDTO("Grievous", baseUrl + "/images/grievous.jpg", "#0d9488",grievousStats);


        // Criando kylo
        Map<String, String> kyloStats = Map.of(
                        "intelligence", "70",
                        "strength", "25",
                        "speed", "25",
                        "durability", "55",
                        "power", "90",
                        "combat", "90"
            );
        CharacterDTO kylo = new CharacterDTO("Kylo Ren", baseUrl + "/images/kyloImage.webp", "#b91c1c",kyloStats);


        // Criando
        Map<String, String> trooperStats = Map.of(
                        "intelligence", "45",
                        "strength", "1",
                        "speed", "1",
                        "durability", "10",
                        "power", "15",
                        "combat", "30"
            );
        CharacterDTO trooper = new CharacterDTO("Stormtrooper", baseUrl + "/images/trooperImage.jpg", "#1f2937",trooperStats);

        Map<String, String> maulStats = Map.of(
                        "intelligence", "70",
                        "strength", "25",
                        "speed", "80",
                        "durability", "55",
                        "power", "70",
                        "combat", "100"
            );
        CharacterDTO maul = new CharacterDTO("Darth Maul", baseUrl + "/images/maul.jpg", "#7f1d1d",maulStats);

        allCharacterDTOs = List.of(yodaDTO, vaderDTO, chewDTO, bobaDTO, r2d2, grievous, kylo, trooper, maul);
       
        
    }

    public CharacterDTO getCharacter(String name) {
        return allCharacterDTOs.stream()
                .filter(character -> character.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null); // Retorna null se não achar o herói
    }

    public List<CharacterDTO> getAllCharacters() {
       return allCharacterDTOs;
    }
    
}
