package com.hexagonal.notice.domain.port.in.profile;

import com.hexagonal.notice.domain.model.Profile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RetrieveProfileUseCase {
    Mono<Profile> getProfileById(Long id);

    Mono<Profile> getProfileByUserId(Long userId);

    Flux<Profile> getAllProfiles();
}
