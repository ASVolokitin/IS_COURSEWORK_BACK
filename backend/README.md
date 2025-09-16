##  How to Run the Application
##### PrerequisitesPrerequisites
1. Docker and Docker Compose installed
2. Maven (for building the Spring application)

### Step-by-Step Guide

##### Build Spring Application JAR:

```bash
mvn clean package -DskipTests
```
This command will create a JAR artifact in the target/ directory.

##### Start Docker Containers:
```bash
docker compose up --build
```
The --build flag ensures that Docker images are rebuilt before starting containers.

##### Run in Detached Mode (Recommended):
```bash
docker compose up --build -d
```
Use the -d flag to run containers in the background.

##### 🛑 Stopping the Application
Stop and Remove Containers
```bash
docker compose down
```
