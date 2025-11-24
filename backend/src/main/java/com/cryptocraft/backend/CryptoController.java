package com.cryptocraft.backend;

import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CryptoController {
    private final EncryptionService service;

    public CryptoController(EncryptionService service) {
        this.service = service;
    }

    @PostMapping("/encrypt")
    public Map<String, String> encryptData(@RequestBody EncryptionRequest request){
        String algo = request.getAlgorithm();
        String text = request.getPlaintext();
        String key = request.getKey();
        String result = "";

        if(algo== null || text == null || key == null ){
            return Collections.singletonMap("error", "Missing algorithm,plaintext or key");
        }

        try{
            switch(algo.toLowerCase()){
                case "caesar":
                    try{
                        int shift = Integer.parseInt(key);
                        result = service.encryptCaesar(text,shift);
                    } catch(NumberFormatException e){
                        return Collections.singletonMap("error", "Caesar key must be a number");
                    }break;
                
                    case "substitution":
                        result = service.encryptSubstitution(text, key);
                        break;
                    
                    case "playfair":
                        result = service.playfairEncrypt(text, key);
                        break;
                    
                    case "des":
                        result = service.encryptDES(text, key);
                        break;

                    default:
                        return Collections.singletonMap("error", "unknown algorithm: "+algo);
            }
        } catch (Exception e){
            return Collections.singletonMap("error", "Encryption Failed: " + e.getMessage());
        }


        return Collections.singletonMap("result", result);
    }
}

