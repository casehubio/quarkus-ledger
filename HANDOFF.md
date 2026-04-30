# CaseHub Ledger — Session Handover
**Date:** 2026-04-30

## Current State

`casehub-ledger` v0.2-SNAPSHOT. Clean working tree. All stale Quarkiverse/quarkus-ledger references purged across 60+ files — Java source, docs, blog, examples, SQL migrations, GitHub issues. IRI in serialized JSON-LD output corrected. Tier-4 health check passed with no High or Critical findings.

## What Landed This Session

**Naming cleanup (full sweep):**
- IRI `http://quarkiverse.io/ledger#` → `https://casehubio.github.io/ledger#` in `LedgerProvSerializer.java`, its test, and `ProvDmExportIT.java` — this was live serialized output, not cosmetic
- Stale names in Javadoc, docs, blog frontmatter, example pom `<name>` fields, SQL migration comments, historical plan docs, GitHub issues #48/#49/#72
- Rule applied: change our old package/artifact names; leave actual Quarkiverse library deps untouched

**CLAUDE.md updated:** `LedgerRetentionJob` and `RetentionEligibilityChecker` added to project structure table (were missing despite having integration tests)

**Tier-4 health check:** architecture clean, BOM-managed throughout, DESIGN.md 100% accurate against code, 32 test classes covering all major services. DESIGN.md approaching single-file capacity — worth splitting before Group B lands (not urgent).

## Open Cross-Repo Issues (#72)

*Unchanged from previous handover — retrieve with: `git show HEAD~2:HANDOFF.md`*

## Immediate Next Steps

1. **Group B starts with #60** — add `capabilityTag` to `LedgerAttestation`, then #61 (capability-scoped trust) and #62 (multi-dimensional)
2. **Remaining Group A:** #56 (health checks), #57 (multi-attestation aggregation), #58 (compliance report), #59 (ProvenanceSupplement enricher)
3. **DESIGN.md split** — before Group B adds more, consider `DESIGN-core.md` (entity model, SPI contracts) and `DESIGN-capabilities.md` (trust, Merkle, privacy, compliance)

## References

| What | Path |
|---|---|
| Latest ADRs | `adr/0005` – `adr/0007` |
| Latest blog | `blog/2026-04-29-mdp01-what-the-reviews-missed.md` |
| Cross-repo bugs | `https://github.com/casehubio/ledger/issues/72` |
| Previous handover | `git show HEAD~1:HANDOFF.md` |
