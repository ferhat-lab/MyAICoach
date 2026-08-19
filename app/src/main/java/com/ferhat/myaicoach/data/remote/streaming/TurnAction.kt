package com.ferhat.myaicoach.data.remote.streaming

/**
 * TurnAction: Server-Side TargetEvaluator ve Pedagoji Motoru tarafından üretilen aksiyon enum'u.
 * TurnMetadata wire contract'ında kullanılır.
 */
enum class TurnAction {
    CONTINUE,
    RETRY,
    GIVE_HINT,
    COMPLETE_SCENARIO,
    SAFE_REDIRECT
}
