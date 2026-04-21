# Quarkus Ledger — Session Handover
**Date:** 2026-04-21

## Current State

- **Tests:** 159 passing (runtime module), BUILD SUCCESS across all modules
- **Open issues:** None — #28 (Bayesian Beta trust) and #29 (privacy pseudonymisation) both closed
- **quarkus-ledger installed to `~/.m2`** — Qhorus can consume immediately
- **Version:** 1.0.0-SNAPSHOT

## What Landed This Session

**Bayesian Beta trust scoring (issue #28 — closed):**
- `TrustScoreComputer` — Beta accumulation: α/β per attestation with recency weighting, score=α/(α+β), prior Beta(1,1). `ForgivenessParams` and two-arg constructor removed entirely. See ADR 0003.
- `ActorScore` record — gained `alpha`/`beta` fields, dropped `appealCount`
- `ActorTrustScore` entity + V1001 schema — `alpha_value`/`beta_value` columns replace `appeal_count`
- `TrustScoreJob`, `LedgerConfig` — ForgivenessConfig removed, single-arg constructor
- 18 unit tests + 5 integration tests replacing forgiveness tests

**Privacy / pseudonymisation (issue #29 — closed) — Axiom 7 addressed:**
- `ActorIdentityProvider` SPI — `tokenise()`/`tokeniseForQuery()`/`resolve()`/`erase()`
- `DecisionContextSanitiser` SPI — sanitise `decisionContext` JSON before persist
- `InternalActorIdentityProvider` — built-in UUID token impl (plain Java, not CDI bean)
- `LedgerPrivacyProducer` — `@Produces @DefaultBean @ApplicationScoped` pattern; custom bean silently replaces
- `LedgerErasureService` — GDPR Art.17: finds token, counts affected entries, severs mapping, returns `ErasureResult`
- `ActorIdentity` JPA entity + V1004 migration (`actor_identity` table)
- `LedgerConfig.IdentityConfig` — `quarkus.ledger.identity.tokenisation.enabled` (default false)
- Write path wired: `actorId` and `attestorId` tokenised on write; `decisionContext` sanitised; `findByActorId` translates via `tokeniseForQuery`
- **Bug found + fixed:** `em.clear()` required after bulk JPQL `executeUpdate()` — Hibernate L1 cache returns stale entity otherwise
- 31 new tests: unit, IT, end-to-end

**Housekeeping:**
- Closed stale issues #19, #20 (entity conversion — already landed)
- RESEARCH.md — marked items 1–5 done (were still showing active); added items 9–11 (privacy, LLM mesh, EigenTrust transitivity)
- Health check fix — `TrustScoreIT` datasource isolation (`trustscoretestdb`)
- AUDITABILITY.md — Axiom 7 ✅. All 8 axioms now addressed.

## Immediate Next Steps

1. **Consumer guidance** — Axiom 7 addressed at extension level. Consumers must never store raw PII in `decisionContext` (or provide a `DecisionContextSanitiser` bean); confirm whether their `subjectId` is personal data.
2. **LLM agent mesh (epic #22)** — parked. Start with #23 (agent identity model). Key insight: `actorId` should map to behavioral identity (CLAUDE.md + memory), not session ID.
3. **EigenTrust transitivity (issue #26)** — research task before any code. Current model only computes direct attestation scores; true EigenTrust propagates trust through the mesh via eigenvector computation.

## References

| What | Path |
|---|---|
| Design doc | `docs/DESIGN.md` |
| Axiom gap analysis | `docs/AUDITABILITY.md` (all 8 ✅) |
| RESEARCH.md | `docs/RESEARCH.md` (items 1–8 done, 9–11 active) |
| ADRs | `adr/0001` (superseded), `adr/0002`, `adr/0003` (Bayesian Beta) |
| Privacy spec | `docs/superpowers/specs/2026-04-21-privacy-pseudonymisation-design.md` |
| Latest blog | `blog/2026-04-21-mdp08-forgiveness-was-a-patch.md` |
| LLM agent mesh epic | GitHub #22 (child issues #23–#27) |
