package com.example.elotech.services.googleBooks;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleBooksServiceImpl implements GoogleBooksService {
    private final String GOOGLE_BOOKS_API_BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public Object searchBooks(String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = GOOGLE_BOOKS_API_BASE_URL + "intitle:" + query;
        try {
            return restTemplate.getForObject(url, Object.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
