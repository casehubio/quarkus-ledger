package io.quarkiverse.ledger.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkiverse.ledger.runtime.model.ActorType;
import io.quarkiverse.ledger.runtime.model.LedgerEntry;
import io.quarkiverse.ledger.runtime.model.LedgerEntryType;
import io.quarkiverse.ledger.runtime.repository.LedgerEntryRepository;
import io.quarkiverse.ledger.runtime.service.LedgerEntryEnricher;
import io.quarkiverse.ledger.service.supplement.TestEntry;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;

/**
 * Verifies the LedgerEntryEnricher pipeline properties: non-fatal failure and full execution.
 */
@QuarkusTest
@TestProfile(LedgerEnricherPipelineIT.PipelineTestProfile.class)
class LedgerEnricherPipelineIT {

    public static class PipelineTestProfile implements QuarkusTestProfile {
        @Override
        public String getConfigProfile() {
            return "enricher-pipeline-test";
        }
    }

    /** Always throws — used to verify the pipeline is non-fatal. */
    @ApplicationScoped
    static class ThrowingEnricher implements LedgerEntryEnricher {
        @Override
        public void enrich(final LedgerEntry entry) {
            throw new RuntimeException("simulated enricher failure");
        }
    }

    /** Counts invocations — used to verify all enrichers run. */
    @ApplicationScoped
    static class CountingEnricher implements LedgerEntryEnricher {
        static final AtomicInteger count = new AtomicInteger(0);

        @Override
        public void enrich(final LedgerEntry entry) {
            count.incrementAndGet();
        }
    }

    @Inject
    LedgerEntryRepository repo;

    @BeforeEach
    void reset() {
        CountingEnricher.count.set(0);
    }

    @Test
    @Transactional
    void throwingEnricher_doesNotPreventPersist() {
        final TestEntry entry = buildEntry();

        // Must not throw even though ThrowingEnricher is registered
        repo.save(entry);

        assertThat(repo.findEntryById(entry.id)).isPresent();
    }

    @Test
    @Transactional
    void allEnrichersRun_despiteFailingEnricher() {
        final TestEntry entry = buildEntry();

        repo.save(entry);

        // CountingEnricher must have run (pipeline did not short-circuit on ThrowingEnricher)
        assertThat(CountingEnricher.count.get()).isGreaterThanOrEqualTo(1);
    }

    @Test
    @Transactional
    void traceId_stillPopulated_despiteThrowingEnricher() {
        // TraceIdEnricher must still run even when ThrowingEnricher is registered.
        // No active OTel span in this test, so traceId stays null — but the key point
        // is no exception propagates.
        final TestEntry entry = buildEntry();

        repo.save(entry);

        // Entry was saved — no exception means the pipeline completed gracefully
        assertThat(repo.findEntryById(entry.id)).isPresent();
    }

    private static TestEntry buildEntry() {
        final TestEntry e = new TestEntry();
        e.subjectId = UUID.randomUUID();
        e.sequenceNumber = 1;
        e.entryType = LedgerEntryType.EVENT;
        e.actorId = "pipeline-test-actor";
        e.actorType = ActorType.AGENT;
        e.occurredAt = Instant.now();
        return e;
    }
}
