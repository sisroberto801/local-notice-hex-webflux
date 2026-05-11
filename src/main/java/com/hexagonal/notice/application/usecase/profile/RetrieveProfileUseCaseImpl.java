package com.hexagonal.notice.application.usecase.profile;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.in.profile.RetrieveProfileUseCase;
import com.hexagonal.notice.domain.port.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("retrieveProfileBean")
public class RetrieveProfileUseCaseImpl implements RetrieveProfileUseCase {
    private final ProfileRepositoryPort profileRepository;

    public RetrieveProfileUseCaseImpl(ProfileRepositoryPort profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Mono<Profile> getProfileById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public Mono<Profile> getProfileByUserId(Long userId) {
        return profileRepository.findByUserId(userId);
    }

    @Override
    public Flux<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }
}
