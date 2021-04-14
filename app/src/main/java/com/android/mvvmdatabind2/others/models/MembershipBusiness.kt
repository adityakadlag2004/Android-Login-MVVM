package com.android.mvvmdatabind2.others.models


data class MembershipBusiness(
    var title: String,
    var category: String? = null,
    var desc: String,
    var capacity: String,
    var st_time: String,
    var duration: String,
    var ed_time: String,
    var fees: String,
) {
}