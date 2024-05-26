# Distance Project

This project is a Spring Boot application that retrieves route information between two locations using the Google Maps Distance Matrix API. The application exposes a RESTful endpoint to fetch the route details, including distance and duration.

## Prerequisites

- Java 17
- Maven or Gradle
- Google Maps API Key

## Dependencies

The project uses the following dependencies:
- Spring Boot 3.3.0
- Spring Web
- Lombok
- RestTemplate

## Getting Started

### Clone the repository

```bash
git clone https://github.com/rohit-nunam/distance-project.git
cd distance-project
```
## Configuration

```bash
google.maps.api.key=YOUR_GOOGLE_MAPS_API_KEY
```

## Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

## Usage

```bash
req:
GET /route?from={origin}&to={destination}

res:
{
  "from": "New York, NY",
  "to": "Los Angeles, CA",
  "distance": "2,789 miles",
  "duration": "1 day 17 hours"
}

```