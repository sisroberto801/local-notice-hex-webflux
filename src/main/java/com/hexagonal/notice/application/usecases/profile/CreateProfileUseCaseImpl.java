package com.hexagonal.notice.application.usecases.profile;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.ports.in.profile.CreateProfileUseCase;
import com.hexagonal.notice.domain.ports.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("createProfileBean")
@Transactional
public class CreateProfileUseCaseImpl implements CreateProfileUseCase {
    private final ProfileRepositoryPort profileRepository;

    public CreateProfileUseCaseImpl(ProfileRepositoryPort profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Mono<Profile> createProfile(Profile profile) {
        return profileRepository.save(profile);
    }
}
