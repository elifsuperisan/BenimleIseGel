package com.project.benimleisegel.service.ai;

import com.project.benimleisegel.response.RideSalarySuggestionResponse;
import com.project.benimleisegel.utils.Prompts;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class RideAIService {

    private ChatClient.Builder clientBuilder;

    public RideAIService(ChatClient.Builder clientBuilder) {
        this.clientBuilder = clientBuilder;
    }

    public RideSalarySuggestionResponse getSalarySuggestion(
            double distanceInMeters,
            double durationInSeconds,
            String carBrand,
            String carModel
    ) {
        return clientBuilder
                .defaultSystem(Prompts.GET_SALARY_SUGGESTION_PROMPT)
                .build()
                .prompt()
                .user("yolculuğun metre cinsinden uzunluğu : " + distanceInMeters
                        + "yolculuğun saniye cinsinden uzunluğu : "  + durationInSeconds
                        + "yolculukta kullanılacak aracın markası : " + carBrand
                        + "yolculukta kullanılacak aracın modeli  : " + carModel
                )
                .call()
                .entity(new ParameterizedTypeReference<RideSalarySuggestionResponse>() {});
    }
}
