# Quarkus Ledger — Session Handover
**Date:** 2026-04-29

## Current State

`casehubio/quarkus-ledger`, version `0.2-SNAPSHOT`. Clean working tree (`.claude/settings.local.json` modified locally — not a concern). SNAPSHOT installed and pushed to origin.

## What Landed This Session

**Confidence & trust scoring fixes:**
- `confidence` weighted in Bayesian Beta: `weight = recencyWeight × clamp(confidence, 0,1)`; `DEFAULT 1.0` on `ledger_attestation.confidence`; `overturnedCount` gated on `weight > 0` (Closes #69)

**Prerequisite refactors:**
- #67: `LedgerEntryEnricher` SPI + `TraceIdEnricher` + `LedgerTraceListener` pipeline runner (Closes #67)
- #68: `ActorTrustScore` discriminator model — UUID PK, `score_type` GLOBAL|CAPABILITY|DIMENSION, `scope_key` nullable, `UNIQUE NULLS NOT DISTINCT` (Closes #68)

**Group A — all shipped:**
- #55: `DecayFunction` SPI + `ExponentialDecayFunction` with valence multiplier (FLAGGED decays 0.5× slower, configurable); `TrustScoreComputer` delegates decay (Closes #55)
- #54: `TrustGateService` CDI bean — `meetsThreshold(actorId, minTrust)`, capability overload Phase 1 falls back to global (Closes #54)
- #53: `ActorTypeResolver.resolve()` in quarkus-work, quarkus-qhorus, casehub-engine, claudony — all local `deriveActorType()` removed (Closes #53)

**Process fix:** Important review findings now must reach the user or be fixed — never auto-dismissed by the controller (saved to memory).

## Open Issues from Cross-Repo Audit (#72)

claudony and quarkus-work have real bugs found during Step 6 sweep — tracked in #72. Those repos have active in-progress work; fix in a dedicated session when clear:

- **claudony `ClaudonyLedgerEventCapture`**: silent exception swallowing + `nextSequenceNumber()` race condition
- **quarkus-work**: JSON built with `String.format()` (no escaping), missing null guard on `eventSuffix()`, 8 pre-existing `TrustScoreComputerTest` failures (wrong expectations)

## Immediate Next Steps

1. **Group B starts with #60** (add `capabilityTag` to `LedgerAttestation`) — then #61 (capability-scoped trust scores, requires #68 + #60) and #62 (multi-dimensional, requires #68)
2. **Fix #72 findings** in claudony and quarkus-work when those sessions complete
3. **Remaining Group A**: #56 (health checks), #57 (multi-attestation aggregation), #58 (compliance report — read consolidation check #6 first), #59 (ProvenanceSupplement enricher — requires #67 ✅)

## References

| What | Path |
|---|---|
| Plan (Group A) | `docs/superpowers/plans/2026-04-28-group-a-55-54-53.md` |
| Plan (Prerequisites) | `docs/superpowers/plans/2026-04-28-prerequisite-refactors-67-68.md` |
| Latest ADRs | `adr/0005` – `adr/0007` |
| Latest blog | `blog/2026-04-29-mdp01-what-the-reviews-missed.md` |
| Cross-repo bugs | `https://github.com/casehubio/quarkus-ledger/issues/72` |
| casehub-parent | `~/casehub-parent/` |
