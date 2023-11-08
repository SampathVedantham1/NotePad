package com.example.notepad.note_feature.domain.util

/**
 * Sealed class representing the filter criteria for ordering notes.
 *
 * @property orderTypeFilter The order type filter, either ascending or descending.
 */
sealed class NoteOrderByFilter(val orderTypeFilter: OrderTypeFilter) {
    /**
     * Subclass for ordering by note title.
     *
     * @param orderType The order type filter for titles.
     */
    class Title(orderType: OrderTypeFilter) : NoteOrderByFilter(orderTypeFilter = orderType)

    /**
     * Subclass for ordering by note date.
     *
     * @param orderType The order type filter for dates.
     */
    class Date(orderType: OrderTypeFilter) : NoteOrderByFilter(orderTypeFilter = orderType)

    /**
     * Subclass for ordering by note color.
     *
     * @param orderType The order type filter for colors.
     */
    class Color(orderType: OrderTypeFilter) : NoteOrderByFilter(orderTypeFilter = orderType)

    /**
     * Changes the order type for the current filter criteria.
     *
     * @param orderType The new order type to apply.
     * @return A new instance of NoteOrderByFilter with the updated order type.
     */
    fun changeOrderType(orderType: OrderTypeFilter): NoteOrderByFilter {
        return when (this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
