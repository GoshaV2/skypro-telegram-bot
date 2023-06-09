package com.skypro.skyprotelegrambot.dto.response;

import com.skypro.skyprotelegrambot.entity.Shelter;
import lombok.Builder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
public class ShelterResponse {
    private Long id;
    private String name;

    public ShelterResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShelterResponse() {
    }

    public static ShelterResponse from(Shelter shelter) {
        return ShelterResponse.builder()
                .id(shelter.getId())
                .name(shelter.getName()).build();
    }

    public static List<ShelterResponse> from(Collection<Shelter> sheltersList) {
        return sheltersList.stream().map(ShelterResponse::from).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}