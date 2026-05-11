package com.hexagonal.notice.application.usecase.profile;

import com.hexagonal.notice.domain.port.in.profile.DeleteProfileUseCase;
import com.hexagonal.notice.domain.port.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("deleteProfileBean")
public class DeleteProfileUseCaseImpl implements DeleteProfileUseCase {
    private final ProfileRepositoryPort profileRepository;

    public DeleteProfileUseCaseImpl(ProfileRepositoryPort profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Mono<Void> deleteProfileById(Long id) {
        return profileRepository.deleteById(id);
    }
}
