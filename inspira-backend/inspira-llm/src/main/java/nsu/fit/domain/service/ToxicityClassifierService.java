package nsu.fit.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nsu.fit.exception.ErrorType;
import nsu.fit.exception.ServiceException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class ToxicityClassifierService {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${huggingface.api.url}")
    private String apiUrl;

    @Value("${huggingface.api.token}")
    private String apiToken;

    public boolean isCleanLanguage(String wish) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiToken);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String body = String.format("{\"inputs\": \"%s\"}", wish.replace("\"", "\\\""));
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            try {
                JSONArray jsonArray = new JSONArray(response.getBody()).getJSONArray(0);
                double neutralScore = 0.0;

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    if ("neutral".equals(obj.getString("label"))) {
                        neutralScore = obj.getDouble("score");
                        break;
                    }
                }

                return neutralScore > 0.7;

            } catch (Exception e) {
                log.error("Некорректный ответ модели: {}", response.getBody());
                throw new ServiceException("Некорректный ответ модели", ErrorType.UNEXPECTED_MODEL_ANSWER);
            }
        }

        log.error("Запрос окончился неудачей: {}", response.getBody());
        throw new ServiceException("Запрос был завершен с кодом " + response.getStatusCode(), ErrorType.UNEXPECTED_MODEL_ANSWER);
    }
}
