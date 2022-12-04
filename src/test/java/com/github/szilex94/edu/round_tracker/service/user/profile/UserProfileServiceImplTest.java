package com.github.szilex94.edu.round_tracker.service.user.profile;

import com.github.szilex94.edu.round_tracker.repository.user.profile.UserProfileRepositoryAdapter;
import com.github.szilex94.edu.round_tracker.service.user.profile.support.UserProfileUpdateMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserProfileServiceImplTest {

    private static final String TEST_UID = UUID.randomUUID().toString();

    @Mock
    private UserProfileRepositoryAdapter mockedRepositoryAdapter;

    @Spy
    private UserProfileUpdateMapperImpl spyUpdateMapper;

    @Captor
    ArgumentCaptor<UserProfile> profileArgumentCaptor;

    @InjectMocks
    private UserProfileServiceImpl subject;

    @Test
    @DisplayName("createNewUser happyFlow")
    public void test_createNewUser_happyFlow() {
        var input = createValidUserProfile()
                .setId(null);
        var dbUserProfile = createValidUserProfile();
        when(mockedRepositoryAdapter.createOrUpdate(input)).thenReturn(Mono.just(dbUserProfile));

        var result = subject.createNewUser(input);

        StepVerifier.create(result)
                .expectNext(dbUserProfile)
                .verifyComplete();

        verify(mockedRepositoryAdapter, times(1)).createOrUpdate(input);
    }

    @Test
    @DisplayName("retrieveById happyFlow")
    public void test_retrieveById_happyFlow() {
        var dbUserProfile = createValidUserProfile();
        when(mockedRepositoryAdapter.findById(TEST_UID)).thenReturn(Mono.just(dbUserProfile));

        var result = subject.retrieveById(TEST_UID);

        StepVerifier.create(result)
                .expectNext(dbUserProfile)
                .verifyComplete();

        verify(mockedRepositoryAdapter, times(1)).findById(TEST_UID);
    }


    @Test
    @DisplayName("updateUserProfile null input")
    public void test_updateUserProfile_null_input() {
        assertThrows(IllegalArgumentException.class, () -> subject.updateUserProfile(null));
    }

    @Test
    @DisplayName("updateUserProfile null userId")
    public void test_updateUserProfile_null_userId() {
        var profileFixture = createValidUserProfile()
                .setId(null);
        assertThrows(IllegalArgumentException.class, () -> subject.updateUserProfile(profileFixture));
    }

    @Test
    @DisplayName("updateUserProfile empty userId")
    public void test_updateUserProfile_empty_userId() {
        var profileFixture = createValidUserProfile()
                .setId("");
        assertThrows(IllegalArgumentException.class, () -> subject.updateUserProfile(profileFixture));
    }

    @Test
    @DisplayName("updateUserProfile happyFlow")
    public void test_updateUserProfile_happyFlow() {
        var desiredUserProfile = createValidUserProfile();

        var dbUserProfileFixture = createValidUserProfile()
                .setAlias("dbAlias")
                .setFirstName("dbFirstName")
                .setLastName("dbLastName");

        when(mockedRepositoryAdapter.findById(TEST_UID))
                .thenReturn(Mono.just(dbUserProfileFixture));
        when(mockedRepositoryAdapter.createOrUpdate(any(UserProfile.class)))
                .thenAnswer((Answer<Mono<UserProfile>>) invocation -> Mono.just(invocation.getArgument(0)));

        var result = subject.updateUserProfile(desiredUserProfile);

        StepVerifier.create(result)
                .consumeNextWith(afterDBSaveValue -> {
                    assertEquals(desiredUserProfile.getId(), afterDBSaveValue.getId());
                    assertEquals(desiredUserProfile.getAlias(), afterDBSaveValue.getAlias());
                    assertEquals(desiredUserProfile.getFirstName(), afterDBSaveValue.getFirstName());
                    assertEquals(desiredUserProfile.getLastName(), afterDBSaveValue.getLastName());
                })
                .verifyComplete();

        verify(mockedRepositoryAdapter, times(1)).findById(TEST_UID);

        verify(spyUpdateMapper, times(1)).updateNonNull(any(), any());

        verify(mockedRepositoryAdapter, times(1)).createOrUpdate(profileArgumentCaptor.capture());

        var beforeDBSaveValue = profileArgumentCaptor.getValue();
        assertEquals(desiredUserProfile.getId(), beforeDBSaveValue.getId());
        assertEquals(desiredUserProfile.getAlias(), beforeDBSaveValue.getAlias());
        assertEquals(desiredUserProfile.getFirstName(), beforeDBSaveValue.getFirstName());
        assertEquals(desiredUserProfile.getLastName(), beforeDBSaveValue.getLastName());
    }

    private UserProfile createValidUserProfile() {
        return new UserProfile()
                .setId(TEST_UID)
                .setFirstName("FirstName")
                .setLastName("LastName")
                .setAlias("Alias");
    }
}
