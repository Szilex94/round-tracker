package com.github.szilex94.edu.round_tracker.service.support.caliber;

import com.github.szilex94.edu.round_tracker.repository.support.caliber.CaliberDefinitionRepositoryAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.UnaryOperator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CaliberDefinitionServiceTest {

    private static final String CODE_FIXTURE = "9mm";

    private static final String DISPLAY_NAME_FIXTURE = "9 mm";

    private static final String DESCRIPTION_FIXTURE = "Typical nine millimeter round.";

    private static final CaliberTypeDefinition CALIBER_TYPE_DEFINITION_FIXTURE = CaliberTypeDefinition.builder()
            .setCode(CODE_FIXTURE)
            .setDisplayName(DISPLAY_NAME_FIXTURE)
            .setDescription(DESCRIPTION_FIXTURE)
            .build();

    @Mock
    private CaliberDefinitionRepositoryAdapter mockedAdapter;

    @Mock
    private UnaryOperator<CaliberTypeDefinition> mockedUnaryOperator;

    @InjectMocks
    private CaliberDefinitionServiceImpl subject;

    @Test
    public void test_retrieveAll() {
        when(mockedAdapter.retrieveAll()).thenReturn(Flux.just(CALIBER_TYPE_DEFINITION_FIXTURE));

        var flux = subject.retrieveCaliberDefinitions();

        StepVerifier.create(flux)
                .expectNext(CALIBER_TYPE_DEFINITION_FIXTURE)
                .verifyComplete();
    }

    @Test
    public void test_createNew_nullInput() {
        assertThrows(IllegalArgumentException.class, () -> {
            this.subject.createNewCaliberDefinition(null);
        });
    }

    @Test
    public void test_createNew_happyFlow() {
        when(mockedAdapter.findByCode(CODE_FIXTURE))
                .thenReturn(Mono.empty());

        when(mockedAdapter.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(CALIBER_TYPE_DEFINITION_FIXTURE.toBuilder().build()));

        var result = this.subject.createNewCaliberDefinition(CALIBER_TYPE_DEFINITION_FIXTURE);

        StepVerifier.create(result)
                .assertNext(next -> {
                    assertNotSame(CALIBER_TYPE_DEFINITION_FIXTURE, next);
                    assertEquals(CODE_FIXTURE, next.getCode());
                    assertEquals(DISPLAY_NAME_FIXTURE, next.getDisplayName());
                    assertEquals(DESCRIPTION_FIXTURE, next.getDescription());
                })
                .verifyComplete();

        verify(mockedAdapter, times(1))
                .findByCode(CODE_FIXTURE);

        verify(mockedAdapter, times(1))
                .save(CALIBER_TYPE_DEFINITION_FIXTURE);
    }

    @Test
    public void test_createNew_duplicateEntry() {
        when(mockedAdapter.findByCode(CODE_FIXTURE))
                .thenReturn(Mono.just(CALIBER_TYPE_DEFINITION_FIXTURE));

        when(mockedAdapter.save(ArgumentMatchers.any()))
                .thenReturn(Mono.just(CALIBER_TYPE_DEFINITION_FIXTURE.toBuilder().build()));

        var result = this.subject.createNewCaliberDefinition(CALIBER_TYPE_DEFINITION_FIXTURE);

        StepVerifier.create(result)
                .expectError(DuplicateCaliberCodeException.class)
                .verify();

        verify(mockedAdapter, times(1))
                .findByCode(CODE_FIXTURE);

        verify(mockedAdapter, times(1))
                .save(CALIBER_TYPE_DEFINITION_FIXTURE);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    public void test_createNewCaliberDefinition_invalidCode(String code) {
        assertThrows(IllegalArgumentException.class,
                () -> this.subject.updateExisting(code, UnaryOperator.identity())
        );
    }

    @Test
    public void test_createNewCaliberDefinition_invalidUnaryOperator() {
        assertThrows(IllegalArgumentException.class,
                () -> this.subject.updateExisting("randomCode", null)
        );
    }

    @Test
    public void test_updateExisting_noEntryInDB() {
        when(mockedAdapter.findByCode(CODE_FIXTURE)).thenReturn(Mono.empty());

        var result = this.subject.updateExisting(CODE_FIXTURE, mockedUnaryOperator);

        StepVerifier.create(result)
                .verifyComplete();

        verify(mockedAdapter, never())
                .save(any());

        verify(mockedUnaryOperator, never())
                .apply(any());
    }

    @Test
    public void test_updateExisting_happyFlow() {
        when(mockedAdapter.findByCode(CODE_FIXTURE))
                .thenReturn(Mono.just(CALIBER_TYPE_DEFINITION_FIXTURE));

        var updated = CALIBER_TYPE_DEFINITION_FIXTURE.toBuilder()
                .setDisplayName("updatedDisplayName")
                .setDescription("updatedDescription")
                .build();

        when(mockedUnaryOperator.apply(CALIBER_TYPE_DEFINITION_FIXTURE))
                .thenReturn(updated);

        when(mockedAdapter.save(updated)).thenReturn(Mono.just(updated));


        var result = this.subject.updateExisting(CODE_FIXTURE, mockedUnaryOperator);

        StepVerifier.create(result)
                .expectNext(updated)
                .verifyComplete();

        verify(mockedAdapter, times(1))
                .save(any());

        verify(mockedUnaryOperator, times(1))
                .apply(any());
    }


    @Test
    public void test_updateExisting_alteredEntityCode() {
        when(mockedAdapter.findByCode(CODE_FIXTURE))
                .thenReturn(Mono.just(CALIBER_TYPE_DEFINITION_FIXTURE));

        final var updated = CALIBER_TYPE_DEFINITION_FIXTURE.toBuilder()
                .setCode("newCode :)")
                .setDisplayName("updatedDisplayName")
                .setDescription("updatedDescription")
                .build();

        when(mockedUnaryOperator.apply(CALIBER_TYPE_DEFINITION_FIXTURE))
                .thenReturn(updated);

        var result = this.subject.updateExisting(CODE_FIXTURE, mockedUnaryOperator);

        StepVerifier.create(result)
                .expectError(IllegalStateException.class)
                .verify();

        verify(mockedUnaryOperator, times(1))
                .apply(any());


        verify(mockedAdapter, never())
                .save(any());

    }
}
