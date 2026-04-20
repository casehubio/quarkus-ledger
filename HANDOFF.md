# Quarkus Ledger — Session Handover
**Date:** 2026-04-20

## Current State

- **Tests:** 129 passing (runtime module), BUILD SUCCESS across all modules
- **Open issues:** None — #11–#17 all closed
- **quarkus-ledger installed to `~/.m2`** — Qhorus can consume immediately
- **Version:** 1.0.0-SNAPSHOT

## What Landed This Session

**PROV-DM export (issues #13/#14 — closed):**
- `LedgerProvSerializer.toProvJsonLd(UUID, List<LedgerEntry>)` — pure static, 13 unit tests
- `LedgerProvExportService.exportSubject(UUID)` — CDI bean, 4 IT tests
- `docs/prov-dm-mapping.md` — field-by-field reference for all supplements
- `examples/prov-dm-export/` — 2 end-to-end tests covering all supplement types

**Documentation review (issue #17 — closed):**
- `integration-guide.md` + `examples.md` — removed deleted `LedgerHashChain` API (compile-breaking drift)
- `DESIGN.md` — ObservabilitySupplement removed, PROV-DM section added, tracker updated
- `CLAUDE.md` + `RESEARCH.md` — Merkle Mountain Range description, items #7/#8 marked done
- Blog entries mdp03–mdp06 written and committed

**Reactive migration (issues #15/#16 — closed) — UNBLOCKS QHORUS:**
- `LedgerEntry` is now a plain `@Entity` — no `PanacheEntityBase`. Qhorus can subclass reactively.
- `JpaLedgerEntryRepository` — rewritten with `EntityManager` JPQL (Panache bytecode enhancement failed with plain entity)
- `ReactiveLedgerEntryRepository` — SPI interface with `Uni<T>` return types. Qhorus implements it.
- **Breaking API change:** `findById(UUID)` → `findEntryById(UUID)` in `LedgerEntryRepository` (return-type conflict with PanacheRepositoryBase)

**Key note for Qhorus:** `findById` is now `findEntryById`. Consumers need to update call sites.

## Immediate Next Steps

1. **LedgerMerkleFrontier decision** — still extends `PanacheEntityBase`. If it becomes a plain `@Entity` too, the reactive `save()` path could update the frontier properly (currently can't — frontier update uses blocking Panache statics). Discuss before Bayesian work.

2. **Bayesian trust weighting** — Research priority #6. Per-interaction recency weighting before EigenTrust eigen computation in `TrustScoreComputer`. Targeted algorithm change, no schema changes. Next after frontier decision.

3. **Privacy/pseudonymisation (Axiom 7)** — hardest gap. Needs design before code.

## References

| What | Path |
|---|---|
| Design doc | `docs/DESIGN.md` |
| Axiom gap analysis | `docs/AUDITABILITY.md` (6 of 8 ✅) |
| PROV-DM mapping | `docs/prov-dm-mapping.md` |
| ADRs | `adr/0001`, `adr/0002` |
| Latest blog | `blog/2026-04-20-mdp06-a-clean-entity.md` |
| PROV-DM spec | `docs/superpowers/specs/2026-04-18-prov-dm-export-design.md` |
