# CaseHub Ledger — Session Handover
**Date:** 2026-05-01

## Current State

`casehub-ledger` v0.2-SNAPSHOT. Clean working tree. 247 tests, BUILD SUCCESS.
Group B has started — #60 (capabilityTag on LedgerAttestation) is shipped and
closed. GitHub CI green.

## What Landed This Session

**DESIGN.md split:**
- `docs/DESIGN.md` — stable structure (entity model, SPI, config, roadmap)
- `docs/DESIGN-capabilities.md` — algorithms (Merkle, PROV-DM, trust capability
  tags, agent identity, agent mesh)

**#60 capabilityTag on LedgerAttestation (Group B, epic #50):**
- `CapabilityTag.GLOBAL = "*"` sentinel (api module) — no NULL semantics
- `capability_tag VARCHAR(255) NOT NULL DEFAULT '*'` in V1000
- 3 new SPI methods (blocking + reactive parity): `findAttestationsByEntryIdAndCapabilityTag`,
  `findAttestationsByEntryIdGlobal`, `findAttestationsByAttestorIdAndCapabilityTag`
- JPA impl with `tokeniseForQuery` on attestorId
- 9 IT + 3 unit tests; pseudonymisation-profile test in `LedgerPrivacyWiringIT`
- Dead `api/repository/` package deleted (zero usages, silently diverging)
- `LedgerTestFixtures.seedDecision` overload with capabilityTag (for B2)
- qhorus#133 opened for deferred `LedgerWriteService` capabilityTag integration

**Platform conventions (casehub/parent):**
- `spi-blocking-reactive-parity.md` added — reflection test pattern for
  blocking/reactive SPI parity enforcement

## Immediate Next Steps

1. **#61 (B2)** — capability-scoped trust scores: `TrustScoreJob` reads
   `findAttestationsByAttestorIdAndCapabilityTag` and writes `CAPABILITY` rows
   to `ActorTrustScore`
2. **#62 (B3)** — multi-dimensional trust infrastructure
3. **Group A remaining:** #56 (health checks), #57 (multi-attestation
   aggregation), #58 (compliance report), #59 (ProvenanceSupplement enricher)

## References

| What | Path |
|---|---|
| Latest ADRs | `adr/0005` – `adr/0007` |
| Latest blog | `blog/2026-05-01-mdp01-the-sentinel-this-time.md` |
| Cross-repo bugs | `https://github.com/casehubio/ledger/issues/72` |
| Previous handover | `git show HEAD~1:HANDOFF.md` |
