package com.sherazsadiq.workwise

import java.io.Serializable
    data class Freelancer(
        var name: String = "",
        var id: String? = null,
        var rating: Int = 5,
        var no_gigs: Int = 0,
        var no_reviews: Int = 0,
        var no_orders: Int = 0,
        var no_cancelled_orders: Int = 0,
        var no_delivered_orders: Int = 0,
        var no_active_orders: Int = 0,
        var total_earnings: Int = 0,
        var total_orders: Int = 0,
        var current_balance: Int = 0,
        var current_month_earnings: Int = 0,
        var total_withdrawn: Int = 0,
        var level: Int=1,
        var response_rate: Int = 1,
        var available_for____withdrawl: Int = 0,
    ): Serializable
