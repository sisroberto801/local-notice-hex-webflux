package com.hexagonal.notice.application.usecases.profile;

import com.hexagonal.notice.domain.ports.in.profile.DeleteProfileUseCase;
import com.hexagonal.notice.domain.ports.out.ProfileRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Component("deleteProfileBean")
@Transactional
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
