# Quarkus Ledger — Session Handover
**Date:** 2026-04-28

## Current State

`casehubio/quarkus-ledger`, version `0.2-SNAPSHOT`. Uncommitted changes in migration files and test resources (casehub-claude's work — verify before touching).

## What Landed Since Last Handover

**casehub-claude additions (commits post-2026-04-24):**
- `ActorTypeResolver` — unified utility for deriving `ActorType` from actor identity strings (versioned persona names → AGENT, null → SYSTEM, otherwise HUMAN)
- CLAUDE.md: ecosystem conventions added (Quarkus version, GitHub Packages, SNAPSHOT deps); platform context section; stale refs fixed (quarkus-tarkus→quarkus-work, AgentMessageLedgerEntry→MessageLedgerEntry)
- Migration files V1000–V1002 modified (uncommitted) — **do not overwrite without reading first**
- `runtime/src/test/resources/application.properties` modified (uncommitted)

**This session (ledger-claude):**
- casehubio org established: `casehub-parent` BOM at `~/casehub-parent`, published to GitHub Packages
- All ecosystem repos transferred/aligned: quarkus-work, claudony, quarkus-langchain4j → casehubio
- `casehub-parent` BOM includes: quarkus-ledger, quarkus-work (api/core/runtime/deployment/ledger), quarkus-qhorus (runtime/deployment/testing)
- `build-all.sh` / `replay.sh` / `aggregator.xml` in casehub-parent — SHA-log incremental build (BUILD/TEST/SKIP)
- `quarkus-langchain4j` fork: CI publishes `999-SNAPSHOT` via `-DaltDeploymentRepository`, pom unchanged ✅

## Uncommitted Changes

```
M runtime/src/main/resources/db/migration/V1000__ledger_base_schema.sql
M runtime/src/main/resources/db/migration/V1001__actor_trust_score.sql
M runtime/src/main/resources/db/migration/V1002__ledger_supplement.sql
M runtime/src/test/resources/application.properties
```

These are casehub-claude's changes. Read before acting — the schema changes may be intentional and part of in-progress work.

## Immediate Next Steps

1. Commit/review the uncommitted migration and test resource changes
2. Centralise langchain4j version in casehub-parent BOM (`999-SNAPSHOT`) — casehub-engine still declares it locally
3. casehub-engine pom work ongoing — coordinate with casehub-claude before touching

## References

| What | Path |
|---|---|
| casehub-parent | `~/casehub-parent/` (GitHub: `casehubio/casehub-parent`) |
| Design doc | `docs/DESIGN.md` |
| Latest blog | `blog/2026-04-24-mdp01-fix-that-breaks-everything.md` |
| GitHub repo | `casehubio/quarkus-ledger` (45 issues, all closed) |
