package com.example.hospitalapp.models

data class ShowCall(
    val `data`: Data,
    val message: String,
    val status: Int
)

data class CallData(
    val age: String,
    val analysis_id: String,
    val blood_pressure: String,
    val case_status: String,
    val created_at: String,
    val description: String,
    val doc_id: Int,
    val doctor_id: String,
    val fluid_balance: Any,
    val heart_rate: Any,
    val id: Int,
    val image: String,
    val measurement_note: String,
    val medical_record_note: String,
    val nurse_id: String,
    val patient_name: String,
    val phone: String,
    val respiratory_rate: Any,
    val status: String,
    val sugar_analysis: String,
    val tempreture: Any
)