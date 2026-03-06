# Room Reservation Service API Documentation

Complete API documentation for the Hotel Management System - Room Reservation Service.

**Base URL:** `http://localhost:8080/api/room-reservation`

---

## Endpoints Summary

### 1. Room Categories - 5 endpoints
- ✅ Create, Update, Get All, Get by ID, Delete

### 2. Room Features - 5 endpoints
- ✅ Create, Update, Get All, Get by ID, Delete

### 3. Room Facilities - 5 endpoints
- ✅ Create, Update, Get All, Get by ID, Delete

### 4. Rooms - 5 endpoints
- ✅ Create, Update, Get All, Get by ID, Delete

### 5. Reservations - 12 endpoints
- ✅ Create, Update, Get All, Get by ID, Get by Number, Get by Email
- ✅ Cancel, Check-in, Check-out

**Total: 32 API endpoints**

---

## Table of Contents
1. [Room Category Management](#room-category-management)
2. [Room Features Management](#room-features-management)
3. [Room Facilities Management](#room-facilities-management)
4. [Room Management](#room-management)
5. [Reservation Management](#reservation-management)
6. [Swagger/OpenAPI Documentation](#swaggeropenapi-documentation)
7. [Complete Usage Scenario](#complete-usage-scenario)

---

## Room Category Management

Manage room categories with associated features.

### Create Room Category
**POST** `/categories`

**Request Body:**
```json
{
  "name": "Deluxe Suite",
  "featureIds": [1, 2, 3, 4, 5]
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "Deluxe Suite",
  "features": [
    {"id": 1, "name": "WIFI"},
    {"id": 2, "name": "AC"},
    {"id": 3, "name": "TV"},
    {"id": 4, "name": "MINI_BAR"},
    {"id": 5, "name": "BALCONY"}
  ]
}
```

### Update Room Category
**PUT** `/categories/{id}`

**Request Body:**
```json
{
  "name": "Premium Deluxe Suite",
  "featureIds": [1, 2, 3, 4, 5]
}
```

**Response:** `200 OK`

### Get All Categories
**GET** `/categories`

**Response:** `200 OK`
```json
[
  {
    "id": 1,
    "name": "STANDARD",
    "features": [...]
  }
]
```

### Get Category by ID
**GET** `/categories/{id}`

**Response:** `200 OK`

### Delete Room Category
**DELETE** `/categories/{id}`

**Response:** `204 NO CONTENT`

---

## Room Features Management

Manage room features (WiFi, AC, TV, etc.).

### Create Room Feature
**POST** `/features`

**Request Body:**
```json
{
  "name": "SEA_VIEW"
}
```

**Response:** `200 OK`
```json
{
  "id": 6,
  "name": "SEA_VIEW"
}
```

### Update Room Feature
**PUT** `/features/{id}`

**Request Body:**
```json
{
  "name": "OCEAN_VIEW"
}
```

**Response:** `200 OK`
```json
{
  "id": 6,
  "name": "OCEAN_VIEW"
}
```

### Get All Features
**GET** `/features`

**Response:** `200 OK`
```json
[
  {"id": 1, "name": "WIFI"},
  {"id": 2, "name": "AC"},
  {"id": 3, "name": "TV"},
  {"id": 4, "name": "MINI_BAR"},
  {"id": 5, "name": "BALCONY"}
]
```

### Get Feature by ID
**GET** `/features/{id}`

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "WIFI"
}
```

### Delete Room Feature
**DELETE** `/features/{id}`

**Response:** `204 NO CONTENT`

---

## Room Facilities Management

Manage hotel facilities (Pool, Gym, Spa, etc.).

### Create Room Facility
**POST** `/facilities`

**Request Body:**
```json
{
  "name": "SWIMMING_POOL"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "SWIMMING_POOL"
}
```

### Update Room Facility
**PUT** `/facilities/{id}`

**Request Body:**
```json
{
  "name": "INFINITY_POOL"
}
```

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "INFINITY_POOL"
}
```

### Get All Facilities
**GET** `/facilities`

**Response:** `200 OK`
```json
[
  {"id": 1, "name": "SWIMMING_POOL"},
  {"id": 2, "name": "GYM"},
  {"id": 3, "name": "SPA"}
]
```

### Get Facility by ID
**GET** `/facilities/{id}`

**Response:** `200 OK`
```json
{
  "id": 1,
  "name": "SWIMMING_POOL"
}
```

### Delete Room Facility
**DELETE** `/facilities/{id}`

**Response:** `204 NO CONTENT`

---

## Room Management

Manage hotel rooms with categories and facilities.

### Create Room
**POST** `/rooms`

**Request Body:**
```json
{
  "pricePerNight": 250.00,
  "status": "AVAILABLE",
  "categoryId": 1,
  "facilityIds": [1, 2, 3]
}
```

**Note:** Room number is auto-generated (R001, R002, R003, etc.)

**Response:** `201 CREATED`
```json
{
  "id": 1,
  "roomNumber": "R001",
  "pricePerNight": 250.00,
  "status": "AVAILABLE",
  "category": {
    "id": 1,
    "name": "DELUXE"
  },
  "facilities": [
    {"id": 1, "name": "SWIMMING_POOL"},
    {"id": 2, "name": "GYM"},
    {"id": 3, "name": "SPA"}
  ]
}
```

### Update Room
**PUT** `/rooms/{id}`

**Request Body:**
```json
{
  "pricePerNight": 300.00,
  "status": "OCCUPIED",
  "categoryId": 2,
  "facilityIds": [1, 2, 3, 4]
}
```

**Response:** `200 OK`

### Get All Rooms
**GET** `/rooms`

**Response:** `200 OK`

### Get Room by ID
**GET** `/rooms/{id}`

**Response:** `200 OK`

### Delete Room
**DELETE** `/rooms/{id}`

**Response:** `204 NO CONTENT`

---

## Reservation Management

Manage room reservations with full booking lifecycle and additional facilities.

### Create Reservation
**POST** `/reservations`

**Request Body:**
```json
{
  "roomId": 1,
  "customerName": "John Doe",
  "customerEmail": "john.doe@example.com",
  "customerPhone": "+1234567890",
  "checkInDate": "2026-03-15",
  "checkOutDate": "2026-03-20",
  "numberOfGuests": 2,
  "facilityIds": [3, 4]
}
```

**New Feature: Additional Facilities** ⭐
- You can add extra facilities to a reservation (optional)
- These facilities MUST NOT already be included in:
  - The room's assigned facilities
  - The room category's features
- Validation prevents duplicates

**Validations:**
- Check-in and check-out dates must be in the future
- Check-out date must be after check-in date
- Room must be available for selected dates
- Email must be valid format
- ✅ **facilityIds cannot include facilities already on the room**
- ✅ **facilityIds cannot include facilities matching category features**

**Response:** `201 CREATED`
```json
{
  "id": 1,
  "reservationNumber": "RES00001",
  "room": {
    "id": 1,
    "roomNumber": "R001",
    "pricePerNight": 250.00,
    "status": "AVAILABLE",
    "category": {
      "id": 1,
      "name": "DELUXE"
    },
    "facilities": [
      {"id": 1, "name": "SWIMMING_POOL"},
      {"id": 2, "name": "GYM"}
    ]
  },
  "customerName": "John Doe",
  "customerEmail": "john.doe@example.com",
  "customerPhone": "+1234567890",
  "checkInDate": "2026-03-15",
  "checkOutDate": "2026-03-20",
  "actualCheckInDate": null,
  "actualCheckOutDate": null,
  "numberOfGuests": 2,
  "additionalFacilities": [
    {"id": 3, "name": "SPA"},
    {"id": 4, "name": "RESTAURANT"}
  ],
  "roomCostPerNight": 250.00,
  "totalPrice": 1250.00,
  "status": "CONFIRMED",
  "createdAt": "2026-02-03T10:30:00"
}
```

**Note:** 
- Reservation number is auto-generated (RES00001, RES00002, etc.)
- Total price is calculated: (nights × price per night)
- Room cost per night is locked at booking time
- Additional facilities are stored with the reservation

### Update Reservation
**PUT** `/reservations/{id}`

**Request Body:**
```json
{
  "roomId": 1,
  "customerName": "John Doe",
  "customerEmail": "john.doe@example.com",
  "customerPhone": "+1234567890",
  "checkInDate": "2026-03-16",
  "checkOutDate": "2026-03-22",
  "numberOfGuests": 3,
  "facilityIds": [3, 4, 5]
}
```

**Restrictions:**
- Cannot update CHECKED_IN or CHECKED_OUT reservations
- Room availability is re-checked
- Additional facilities validated against room and category

**Response:** `200 OK`

### Get All Reservations
**GET** `/reservations`

**Response:** `200 OK`

### Get Reservation by ID
**GET** `/reservations/{id}`

**Response:** `200 OK`

### Get Reservation by Number
**GET** `/reservations/number/{reservationNumber}`

**Example:** `/reservations/number/RES00001`

**Response:** `200 OK`

### Get Reservations by Customer Email
**GET** `/reservations/customer/{email}`

**Example:** `/reservations/customer/john.doe@example.com`

**Response:** `200 OK`

### Cancel Reservation
**PUT** `/reservations/{id}/cancel`

**Response:** `200 OK`
```json
{
  "id": 1,
  "reservationNumber": "RES00001",
  "status": "CANCELLED",
  ...
}
```

**Restrictions:**
- Cannot cancel CHECKED_IN or CHECKED_OUT reservations

### Check-In
**PUT** `/reservations/{id}/check-in`

**Response:** `200 OK`
```json
{
  "id": 1,
  "reservationNumber": "RES00001",
  "status": "CHECKED_IN",
  "actualCheckInDate": "2026-03-15",
  ...
}
```

**Business Logic:**
- Records the actual check-in date
- Can only check-in CONFIRMED reservations
- Check-in date must have arrived or passed

### Check-Out
**PUT** `/reservations/{id}/check-out`

**Response:** `200 OK`
```json
{
  "id": 1,
  "reservationNumber": "RES00001",
  "status": "CHECKED_OUT",
  "actualCheckInDate": "2026-03-15",
  "actualCheckOutDate": "2026-03-21",
  "roomCostPerNight": 250.00,
  "totalPrice": 1500.00
}
```

**Business Logic:**
- Records the actual check-out date
- Recalculates total cost based on actual stay:
  - Actual nights = actualCheckOutDate - actualCheckInDate
  - Minimum 1 night charge
  - Final cost = actual nights × roomCostPerNight
- Can only check-out CHECKED_IN reservations

---

## Swagger/OpenAPI Documentation

Access interactive API documentation:

**Swagger UI:** `http://localhost:8080/swagger-ui/index.html`

**OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

The Swagger UI provides:
- Interactive API testing
- Request/response schemas
- Model definitions
- Try-it-out functionality

---

## Reservation Status Flow

```
CONFIRMED → CHECKED_IN → CHECKED_OUT
    ↓
CANCELLED
```

- **CONFIRMED**: Reservation created successfully
- **CHECKED_IN**: Customer checked in
- **CHECKED_OUT**: Customer checked out, final cost calculated
- **CANCELLED**: Reservation cancelled (cannot cancel after check-in)

---

## Complete Usage Scenario

### Step 1: Setup (One-time)

```bash
# Create Features
POST /api/room-reservation/features
{"name": "WIFI"}

POST /api/room-reservation/features
{"name": "AC"}

POST /api/room-reservation/features
{"name": "TV"}

POST /api/room-reservation/features
{"name": "MINI_BAR"}

POST /api/room-reservation/features
{"name": "BALCONY"}

# Create Facilities
POST /api/room-reservation/facilities
{"name": "SWIMMING_POOL"}

POST /api/room-reservation/facilities
{"name": "GYM"}

POST /api/room-reservation/facilities
{"name": "SPA"}

POST /api/room-reservation/facilities
{"name": "RESTAURANT"}

# Create Categories
POST /api/room-reservation/categories
{
  "name": "STANDARD",
  "featureIds": [1, 3]
}

POST /api/room-reservation/categories
{
  "name": "DELUXE",
  "featureIds": [1, 2, 3, 4]
}

POST /api/room-reservation/categories
{
  "name": "SUITE",
  "featureIds": [1, 2, 3, 4, 5]
}
```

### Step 2: Create Rooms

```bash
POST /api/room-reservation/rooms
{
  "pricePerNight": 100.00,
  "status": "AVAILABLE",
  "categoryId": 1,
  "facilityIds": [1, 4]
}
# Response: Room R001 with SWIMMING_POOL, RESTAURANT

POST /api/room-reservation/rooms
{
  "pricePerNight": 200.00,
  "status": "AVAILABLE",
  "categoryId": 2,
  "facilityIds": [1, 2, 4]
}
# Response: Room R002 with SWIMMING_POOL, GYM, RESTAURANT
```

### Step 3: Book a Room with Additional Facilities

```bash
POST /api/room-reservation/reservations
{
  "roomId": 2,
  "customerName": "Alice Johnson",
  "customerEmail": "alice.johnson@example.com",
  "customerPhone": "+1555123456",
  "checkInDate": "2026-03-20",
  "checkOutDate": "2026-03-25",
  "numberOfGuests": 2,
  "facilityIds": [3]
}
# Response: RES00001, Total: 1000.00 (5 nights × 200.00)
# Room has: SWIMMING_POOL, GYM, RESTAURANT
# Additional: SPA
# Category (DELUXE) features: WIFI, AC, TV, MINI_BAR
```

### Step 4: Validation Examples

**❌ Invalid - Facility already on room:**
```json
{
  "roomId": 2,
  "facilityIds": [1]
}
// Error: "Selected facilities already included in the room."
```

**❌ Invalid - Facility matches category feature:**
```json
{
  "roomId": 2,
  "facilityIds": [1, 2]
}
// If facility "AC" exists and category has feature "AC"
// Error: "Selected facilities already included in the room category."
```

**✅ Valid - New facility:**
```json
{
  "roomId": 2,
  "facilityIds": [3]
}
// SPA is not on room, not in category features - OK!
```

### Step 5: Check-In

```bash
PUT /api/room-reservation/reservations/1/check-in
# On 2026-03-20, status changes to CHECKED_IN
# actualCheckInDate recorded
```

### Step 6: Check-Out (Early/Late)

```bash
PUT /api/room-reservation/reservations/1/check-out
# If checked out on 2026-03-26 (1 extra day)
# Cost recalculated: 6 nights × 200.00 = 1200.00
# actualCheckOutDate recorded
# Status: CHECKED_OUT
```

---

## Postman Examples

### 1. Create Feature
```
POST http://localhost:8080/api/room-reservation/features
Content-Type: application/json

{"name":"WIFI"}
```

### 2. Create Facility
```
POST http://localhost:8080/api/room-reservation/facilities
Content-Type: application/json

{"name":"SWIMMING_POOL"}
```

### 3. Create Category
```
POST http://localhost:8080/api/room-reservation/categories
Content-Type: application/json

{"name":"DELUXE","featureIds":[1,2,3,4]}
```

### 4. Create Room
```
POST http://localhost:8080/api/room-reservation/rooms
Content-Type: application/json

{"pricePerNight":200.0,"status":"AVAILABLE","categoryId":1,"facilityIds":[1,2]}
```

### 5. Create Reservation with Additional Facilities
```
POST http://localhost:8080/api/room-reservation/reservations
Content-Type: application/json

{
  "roomId":1,
  "customerName":"John Doe",
  "customerEmail":"john@example.com",
  "customerPhone":"+1234567890",
  "checkInDate":"2026-03-10",
  "checkOutDate":"2026-03-15",
  "numberOfGuests":2,
  "facilityIds":[3,4]
}
```

---

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Check-out date must be after check-in date!"
}
```

### 400 Validation Error - Duplicate Facility
```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Selected facilities already included in the room."
}
```

### 400 Validation Error - Category Conflict
```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Selected facilities already included in the room category."
}
```

### 404 Not Found
```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Room not found with id: 99"
}
```

### 409 Conflict
```json
{
  "timestamp": "2026-02-03T10:30:00",
  "status": 409,
  "error": "Conflict",
  "message": "Room is not available for the selected dates!"
}
```

---

## Features Summary

✅ **Auto-Generation**
- Room numbers: R001, R002, R003...
- Reservation numbers: RES00001, RES00002...

✅ **Smart Pricing**
- Room cost locked at booking
- Automatic calculation on checkout
- Handles early/late checkout

✅ **Availability Management**
- Prevents double booking
- Date conflict detection

✅ **Additional Facilities** ⭐ NEW
- Add extra facilities to reservations
- Validates against room facilities
- Validates against category features
- Prevents duplicate selections

✅ **Flexible Management**
- Full CRUD for all entities
- Categories with multiple features
- Update reservations before check-in

✅ **Complete Lifecycle**
- Booking → Check-in → Check-out
- Cancellation support
- Customer history tracking

✅ **API Documentation**
- Interactive Swagger UI
- OpenAPI 3.0 specification
- Try-it-out functionality

---

## Quick Reference

### Common Room Statuses
- `AVAILABLE` - Room ready for booking
- `OCCUPIED` - Room currently occupied
- `MAINTENANCE` - Room under maintenance

### Reservation Statuses
- `CONFIRMED` - Booking confirmed
- `CHECKED_IN` - Guest checked in
- `CHECKED_OUT` - Guest checked out
- `CANCELLED` - Booking cancelled

### Date Format
- All dates: `YYYY-MM-DD` (ISO format)
- Timestamps: ISO 8601 format

### Minimum Requirements
- Minimum stay: 1 night
- Dates must be in the future when creating reservations

### Validation Rules
- ❌ Cannot add facilities already on the room
- ❌ Cannot add facilities that match category features (by name)
- ✅ Can only add new, unique facilities

---

## Technology Stack

- **Framework:** Spring Boot 4.0.2
- **Java:** 25
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA
- **Validation:** Jakarta Validation
- **Documentation:** Springdoc OpenAPI 2.7.0
- **Build Tool:** Maven

---

**Version:** 3.0  
**Last Updated:** February 3, 2026  
**Total Endpoints:** 32  
**Latest Feature:** Additional Reservation Facilities with Validation
