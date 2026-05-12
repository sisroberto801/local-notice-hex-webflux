package com.hexagonal.notice.application.service;

import com.hexagonal.notice.domain.model.Profile;
import com.hexagonal.notice.domain.port.in.profile.CreateProfileUseCase;
import com.hexagonal.notice.domain.port.in.profile.DeleteProfileUseCase;
import com.hexagonal.notice.domain.port.in.profile.RetrieveProfileUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit Tests for ProfileService
 * <p>
 * These tests verify the business logic of ProfileService in isolation,
 * mocking all external dependencies to ensure pure unit testing.
 * <p>
 * Test Coverage:
 * - Profile creation
 * - Profile retrieval by ID
 * - Profile retrieval by User ID
 * - Profile retrieval (all profiles)
 * - Profile deletion
 * - Error handling scenarios
 */
@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private CreateProfileUseCase createProfileUseCase;

    @Mock
    private RetrieveProfileUseCase retrieveProfileUseCase;

    @Mock
    private DeleteProfileUseCase deleteProfileUseCase;

    @InjectMocks
    private ProfileService profileService;

    private Profile testProfile;
    private static final Long PROFILE_ID = 1L;
    private static final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        testProfile = Profile.builder()
                .id(PROFILE_ID)
                .userId(USER_ID)
                .email("test@example.com")
                .fullName("Test User")
                .bio("Test bio")
                .avatarUrl("https://example.com/avatar.jpg")
                .phoneNumber("+1234567890")
                .address("Test Address")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should create profile successfully when valid profile is provided")
    void createProfile_shouldReturnCreatedProfile_whenValidProfileProvided() {
        Profile newProfile = Profile.builder()
                .userId(USER_ID)
                .email("new@example.com")
                .fullName("New User")
                .build();

        when(createProfileUseCase.createProfile(any(Profile.class))).thenReturn(Mono.just(testProfile));
        StepVerifier.create(profileService.createProfile(newProfile))
                .expectNext(testProfile)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when profile creation fails")
    void createProfile_shouldReturnError_whenCreationFails() {
        Profile newProfile = Profile.builder()
                .userId(USER_ID)
                .email("new@example.com")
                .build();

        when(createProfileUseCase.createProfile(any(Profile.class)))
                .thenReturn(Mono.error(new RuntimeException("Profile creation failed")));
        StepVerifier.create(profileService.createProfile(newProfile))
                .expectError(RuntimeException.class)
                .verify();
    }

    @Test
    @DisplayName("Should return profile when valid ID is provided")
    void getProfileById_shouldReturnProfile_whenValidIdProvided() {
        when(retrieveProfileUseCase.getProfileById(PROFILE_ID)).thenReturn(Mono.just(testProfile));
        StepVerifier.create(profileService.getProfileById(PROFILE_ID))
                .expectNext(testProfile)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when profile ID does not exist")
    void getProfileById_shouldReturnEmpty_whenProfileDoesNotExist() {
        when(retrieveProfileUseCase.getProfileById(PROFILE_ID)).thenReturn(Mono.empty());
        StepVerifier.create(profileService.getProfileById(PROFILE_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return profile when valid User ID is provided")
    void getProfileByUserId_shouldReturnProfile_whenValidUserIdProvided() {
        when(retrieveProfileUseCase.getProfileByUserId(USER_ID)).thenReturn(Mono.just(testProfile));
        StepVerifier.create(profileService.getProfileByUserId(USER_ID))
                .expectNext(testProfile)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty when User ID does not have profile")
    void getProfileByUserId_shouldReturnEmpty_whenUserDoesNotHaveProfile() {
        when(retrieveProfileUseCase.getProfileByUserId(USER_ID)).thenReturn(Mono.empty());
        StepVerifier.create(profileService.getProfileByUserId(USER_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return all profiles when requested")
    void getAllProfiles_shouldReturnAllProfiles() {
        Profile profile2 = Profile.builder()
                .id(2L)
                .userId(2L)
                .email("user2@example.com")
                .fullName("User Two")
                .build();

        when(retrieveProfileUseCase.getAllProfiles()).thenReturn(Flux.just(testProfile, profile2));
        StepVerifier.create(profileService.getAllProfiles())
                .expectNext(testProfile)
                .expectNext(profile2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should return empty flux when no profiles exist")
    void getAllProfiles_shouldReturnEmpty_whenNoProfilesExist() {
        when(retrieveProfileUseCase.getAllProfiles()).thenReturn(Flux.empty());
        StepVerifier.create(profileService.getAllProfiles())
                .verifyComplete();
    }

    @Test
    @DisplayName("Should delete profile successfully when valid ID is provided")
    void deleteProfileById_shouldComplete_whenValidIdProvided() {
        when(deleteProfileUseCase.deleteProfileById(PROFILE_ID)).thenReturn(Mono.empty());
        StepVerifier.create(profileService.deleteProfileById(PROFILE_ID))
                .verifyComplete();
    }

    @Test
    @DisplayName("Should handle error when profile deletion fails")
    void deleteProfileById_shouldReturnError_whenDeletionFails() {
        when(deleteProfileUseCase.deleteProfileById(PROFILE_ID))
                .thenReturn(Mono.error(new RuntimeException("Profile not found")));
        StepVerifier.create(profileService.deleteProfileById(PROFILE_ID))
                .expectError(RuntimeException.class)
                .verify();
    }
}
