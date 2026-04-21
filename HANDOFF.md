# Quarkus Ledger — Session Handover
**Date:** 2026-04-21

## Current State

*Unchanged — `git show HEAD:HANDOFF.md`*

Consumer guidance (previous item 1) is now done — `docs/PRIVACY.md` added this session.

## What Landed This Session

**Capability guide and doc audit:**
- `docs/CAPABILITIES.md` — nine capabilities rated ★ to ★★★★★, with regulatory drivers, "enable when / skip when", and consumer type matrix
- `docs/PRIVACY.md` — consumer privacy obligations: four decisions (actorId, subjectId, decisionContext, custom provider), erasure flow, responsibility boundary
- Systematic doc audit across 8 files — fixed compile-breaking stale references: `previousHash` (removed from README fields table — field doesn't exist), `findById` → `findEntryById` in integration guide + examples (would fail to compile), `ObservabilitySupplement` removed from 5 locations (deleted; fields are core), EigenTrust → Bayesian Beta naming throughout, forgiveness config keys removed, migration V1003 → V1004 in examples, AUDITABILITY date and hash formula updated, DESIGN tracker + RESEARCH item #9 marked done

**All staged, not yet committed** — commit lands with this handover.

## Immediate Next Steps

1. **EigenTrust transitivity (issue #26)** — research-first task before any code. Current model scores direct attestations only; true EigenTrust propagates through the mesh via eigenvector computation. Meaningful when agent mesh is sparse. See RESEARCH.md item #11 and GitHub issue #26.
2. **LLM agent mesh (epic #22)** — start with #23 (agent identity model). Key insight: `actorId` maps to behavioral identity (CLAUDE.md + memory hash), not session ID.

## References

| What | Path |
|---|---|
| Capability applicability guide | `docs/CAPABILITIES.md` (new) |
| Consumer privacy obligations | `docs/PRIVACY.md` (new) |
| Design doc | `docs/DESIGN.md` |
| Axiom gap analysis | `docs/AUDITABILITY.md` (all 8 ✅) |
| RESEARCH.md | `docs/RESEARCH.md` (items 1–9 done, 10–11 active) |
| ADRs | `adr/0002` (MMR), `adr/0003` (Bayesian Beta) |
| Latest blog | `blog/2026-04-21-mdp09-documentation-that-lies.md` |
| LLM agent mesh epic | GitHub #22 (child issues #23–#27) |
| EigenTrust issue | GitHub #26 |
