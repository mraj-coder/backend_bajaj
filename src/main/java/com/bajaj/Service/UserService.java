package com.bajaj.Service;

import com.bajaj.Model.UserRequest;
import com.bajaj.Model.UserResponse;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    public UserResponse processUserRequest(UserRequest userRequest) {
        UserResponse response = new UserResponse();

        response.setUserId("aj3514");
        response.setEmail("aj3514@srmist.edu.in");
        response.setRollNumber("RA2111003011923");

        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();

        for (String element : userRequest.getData()) {
            if (element.matches("\\d+")) {
                numbers.add(element);  // Add numbers
            } else if (element.matches("[A-Za-z]")) {
                alphabets.add(element);
            }
        }

        String highestLowercaseAlphabet = alphabets.stream()
                .filter(c -> c.matches("[a-z]"))
                .max(String::compareTo)
                .orElse(null);

        boolean isFileValid = false;
        String fileMimeType = "N/A";
        String fileSizeKB = "0";

        if (userRequest.getFileB64() != null && !userRequest.getFileB64().isEmpty()) {
            try {
                byte[] decodedFile = Base64.getDecoder().decode(userRequest.getFileB64());
                fileMimeType = detectMimeType(decodedFile);
                fileSizeKB = String.valueOf(decodedFile.length / 1024);
                isFileValid = true;
            } catch (IllegalArgumentException e) {
                isFileValid = false;
            }
        }

        response.setNumbers(numbers);
        response.setAlphabets(alphabets);
        response.setHighestLowercaseAlphabet(highestLowercaseAlphabet != null ? highestLowercaseAlphabet : "");
        response.setFileValid(isFileValid);
        response.setFileMimeType(fileMimeType);
        response.setFileSizeKB(fileSizeKB);
        response.setSuccess(true);

        return response;
    }

    private String detectMimeType(byte[] fileData) {
        String fileHeader = new String(fileData, 0, 4).toUpperCase();

        if (fileHeader.startsWith("FFD8")) {
            return "image/jpeg";
        } else if (fileHeader.startsWith("8950")) {
            return "image/png";
        } else if (fileHeader.startsWith("2550")) {
            return "application/pdf";
        }
        return "unknown";
    }
}