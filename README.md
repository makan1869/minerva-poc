# Minerva Proof of Concept

A Spring Boot 3 application with AI-MC-Server integration.

## Prerequisites

- Java 17+
- Maven 3.6+
- Docker (for containerization)

## Local Development

### Building the Application

```bash
# Clone the repository
git clone https://github.com/makan1869/minerva-poc.git
cd minerva-poc

# Build with Maven
mvn clean package
```

The build process will create a self-contained JAR file in the `target` directory.

### Running Locally

```bash
java -jar target/minerva-poc-0.0.1-SNAPSHOT.jar
```

The application will start and be accessible at http://localhost:8080

## Docker Build and Deployment

### Build Docker Image

```bash
docker build -t minerva-poc:latest .
```

### Run Docker Container

```bash
docker run -p 8080:8080 minerva-poc:latest
```

## Deployment to Secure Container Instances

### Manual Deployment

1. Build the Docker image
2. Push to your container registry:
   ```bash
   docker tag minerva-poc:latest your-registry/minerva-poc:latest
   docker push your-registry/minerva-poc:latest
   ```
3. Deploy to your secure container platform using the appropriate commands for your environment.

### CI/CD Deployment

To set up automated deployment:

1. Create a `.github/workflows` directory
2. Add a workflow YAML file with the following content:

```yaml
name: Build and Deploy

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  build:
    runs-on: ubuntu-latest

    steps:
    - uses: actions/checkout@v3
    
    - name: Set up JDK 17
      uses: actions/setup-java@v3
      with:
        java-version: '17'
        distribution: 'temurin'
        cache: maven
    
    - name: Build with Maven
      run: mvn -B package --file pom.xml
    
    - name: Upload JAR
      uses: actions/upload-artifact@v3
      with:
        name: minerva-poc-jar
        path: target/*.jar

  deploy:
    needs: build
    if: github.ref == 'refs/heads/main' && github.event_name == 'push'
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v3
    
    - name: Download JAR
      uses: actions/download-artifact@v3
      with:
        name: minerva-poc-jar
        path: target
    
    - name: Set up Docker Buildx
      uses: docker/setup-buildx-action@v2
    
    - name: Login to DockerHub
      uses: docker/login-action@v2
      with:
        username: ${{ secrets.DOCKER_USERNAME }}
        password: ${{ secrets.DOCKER_PASSWORD }}
    
    - name: Build and push Docker image
      uses: docker/build-push-action@v4
      with:
        context: .
        push: true
        tags: ${{ secrets.DOCKER_USERNAME }}/minerva-poc:latest
    
    - name: Deploy to Secure Container Instances
      run: |
        # This step would contain the commands to deploy to your secure container instances
        # Example: kubectl apply -f k8s/deployment.yml
        echo "Deploying to secure container instances"
```

3. Set up the required secrets in your GitHub repository.

## API Endpoints

- Status: `GET /api/status`
- AI-MC-Server: `GET /ai-mc`

## Configuration

Application configuration can be modified in `src/main/resources/application.properties`.

For secure container deployment, consider using environment variables instead of hardcoded configuration values.
