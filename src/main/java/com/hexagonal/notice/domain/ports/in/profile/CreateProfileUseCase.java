package com.hexagonal.notice.domain.ports.in.profile;

import com.hexagonal.notice.domain.model.Profile;
import reactor.core.publisher.Mono;

public interface CreateProfileUseCase {
    Mono<Profile> createProfile(Profile profile);
}
