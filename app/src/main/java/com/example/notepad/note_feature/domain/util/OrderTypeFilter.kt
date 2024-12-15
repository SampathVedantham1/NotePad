package com.example.notepad.note_feature.domain.util

/**
 * Sealed class representing the order type filter for sorting items.
 */
sealed class OrderTypeFilter{
    /**
     * Subclass representing an ascending order type filter.
     */
    object Ascending: OrderTypeFilter()

    /**
     * Subclass representing a descending order type filter.
     */
    object Descending: OrderTypeFilter()
}
