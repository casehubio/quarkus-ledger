# Quarkus Ledger — Session Handover
**Date:** 2026-04-24

## Current State

Repo transferred to `casehubio/quarkus-ledger`. Version `0.2-SNAPSHOT`. 196 tests passing. All 45 GitHub issues closed.

## What Landed This Session

**Configurable datasource — `quarkus.ledger.datasource` (Closes #46)**
- `@LedgerPersistenceUnit` CDI qualifier + `LedgerEntityManagerProducer`
- Producer reads config, selects `@Default` or named PU via `AnnotationLiteral`
- All 8 runtime `EntityManager` injection points updated
- Fixes "Unsatisfied dependency for EntityManager [@Default]" in named-only datasource deployments (e.g. Claudony with `quarkus.ledger.datasource=qhorus`)
- 4 unit tests + 192 regression tests passing

**Org transfer + build infrastructure**
- Repo moved to `casehubio/quarkus-ledger`; local remote updated
- Version bumped `1.0.0-SNAPSHOT` → `0.2-SNAPSHOT` (aligns with casehub-engine)
- `casehubio/casehub-parent` BOM created at `~/casehub-parent` — manages quarkus-ledger, casehub-ledger, quarkus-bom versions
- GitHub Packages publishing wired (CI publishes on every push to main)
- Claudony config: `quarkus.ledger.datasource=qhorus` added
- quarkus-work notified via issue #134

## Immediate Next Steps

No open issues. Only pending item:
- **Submission target decision** — Quarkiverse vs SmallRye. Parked in `IDEAS.md` 2026-04-23. Needs conversation with maintainers.

## References

| What | Path |
|---|---|
| Design doc | `docs/DESIGN.md` |
| IDEAS.md | `IDEAS.md` (submission target parked 2026-04-23) |
| casehub-parent | `~/casehub-parent/` |
| Latest blog | `blog/2026-04-24-mdp01-fix-that-breaks-everything.md` |
| GitHub repo | casehubio/quarkus-ledger (45 issues, all closed) |
