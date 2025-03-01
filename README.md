<h1 align="center" style="font-weight: bold;">Leds DevOps Challenge 💻</h1>

<p align="center">
 <a href="#tech">Technologies</a> • 
 <a href="#started">Getting Started</a> • 
  <a href="#routes">API Endpoints</a> •
 <a href="#colab">Collaborators</a> •
 <a href="#contribute">Contribute</a>
</p>

<p>
    This project was developed as part of a challenge for a job opportunity at LEDS. Its goal is to match candidates with government-offered job opportunity exams.
    <br/>
    The API was built using Spring Boot, following best practices such as Clean Code principles, comprehensive testing, and maintainable architecture.
    <br/>
    It features a complete CI/CD pipeline, automating the following steps using GitHub Actions:
    <br/>
    <ul>
    <li><b>Build & Test:</b> Compiles and validates the API using Maven</li>
    <li><b>Containerization:</b> Packages the application into a Docker container</li>
    <li><b>Code Quality Analysis:</b> Runs static code analysis using SonarQube</li>
    <li><b>Image Deployment:</b> Pushes the Docker image to Docker Hub</li>
    <li><b>Server Deployment:</b> Pulls and deploys the containerized application on an AWS EC2 instance</li>
    </ul>
    Additionally, the application relies on an AWS RDS database for persistent storage, ensuring scalability and reliability.
</p>

<h2 id="technologies">💻 Technologies</h2>

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Apache Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=Apache%20Maven&logoColor=white)
![jUnit](https://img.shields.io/badge/-jUnit-%2325A162?style=for-the-badge&logo=junit5&logoColor=white)
![Mockito](https://img.shields.io/badge/-Mockito-%2372A928?style=for-the-badge&logo=mocha&logoColor=%23080808)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-black?style=for-the-badge&logo=sonarqube&logoColor=4E9BCD)
![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)

<h2 id="started">🚀 Getting started</h2>

<h3>📌 Prerequisites</h3>

Ensure you have the following installed:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- Maven: for [Ubuntu](https://maven.apache.org/download.cgi) or [MacOS](https://formulae.brew.sh/formula/maven)
- [Docker](https://docs.docker.com/get-started/get-docker/)

<h3>📥 Cloning</h3>

Run the following command to clone the project:

```bash
git clone https://github.com/jhonatademuner/leds-devops.git
cd leds-devops
```

<h3>🛠️ Database Setup</h3>

Before configuring the application, you need to set up a PostgreSQL 16 database using Docker.

<b>1️⃣ Create a Volume for Data Persistence</b>

Run the following command to create a Docker volume:

```bash
docker volume create postgres_data
```

<b>2️⃣ Start a PostgreSQL 16 Container with a Persistent Volume</b>

```bash
docker run --name postgres-db \
  -e POSTGRES_USER=admin \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=leds_devops \
  -p 5432:5432 \
  -v postgres_data:/var/lib/postgresql/data \
  -d postgres:16
```

Explanation of the parameters:
- `--name postgres-db` → Assigns a name to the container
- `-e POSTGRES_USER=admin `→ Sets the database username to `admin`
- `-e POSTGRES_PASSWORD=admin` → Sets the database password to `admin`
- `-e POSTGRES_DB=leds_devops` → Creates a database named `leds_devops`
- `-p 5432:5432` → Maps the local port 5432 to the container’s PostgreSQL port
- `-v postgres_data:/var/lib/postgresql/data` → Persists database data in a Docker volume
- `-d postgres:16` → Runs the container in the background using PostgreSQL 16

<b>3️⃣ Verify if the Container is Running</b>

```bash
docker ps
```

<h3>⚙️ Configuring the Application</h2>

After setting up the database, update the `application.properties` file with your database credentials:

```properties
sspring.datasource.url=jdbc:postgresql://localhost:5432/leds_devops
spring.datasource.username=admin
spring.datasource.password=admin
```

<h3>🚀 Running the Application</h3>

From the project root directory, execute:

```bash
mvn clean install    # Builds the project
mvn spring-boot:run  # Starts the application
```

<h2 id="routes">📍 API Endpoints</h2>
​
The following endpoints are the core of the application, responsible for matching candidates with exams:

| Route               | Description                                          
|----------------------|-----------------------------------------------------
| <kbd>GET /match/candidate/{citizenId}</kbd>     | Returns the exams that match the candidate roles. [SEE RESPONSE EXAMPLE](#match-candidate-detail)
| <kbd>GET /match/exam/{examCode}</kbd>     | Returns the candidates that match the exam roles. [SEE RESPONSE EXAMPLE](#match-exam-detail)

<h3 id="match-candidate-detail">🔎 GET /match/candidate/{citizenId}</h3>

**📌 Response Example:**
```json
[
  {
    "agency": "SETADES",
    "notice": "8/2016",
    "code": "01403106938"
  },
  {
    "agency": "SESA",
    "notice": "9/2016",
    "code": "22480949466"
  },
  ...
]
```

<h3 id="match-exam-detail">🔎 GET /match/exam/{examCode}</h3>

**📌 Response Example:**
```json
[
   {
    "name": "Nicole Spears",
    "dateOfBirth": "1955-05-07T03:00:00.000+00:00",
    "citizenId": "25144278168"
  },
  {
    "name": "Tyrone Gaines",
    "dateOfBirth": "1976-04-25T03:00:00.000+00:00",
    "citizenId": "26401259887"
  },
  ...
]
```

<h2>📜 Full API Documentation</h2>

The API includes additional endpoints for managing candidates, exams, and  roles. The complete documentation is available through Swagger UI.

**🔹 Access the Swagger Page:**

```plaintext
http://localhost:8080/swagger-ui/index.html
```

This allows you to explore all available endpoints, test requests, and check request/response formats. 🚀

<h2 id="colab">🤝 Collaborators</h2>

<table>
  <tr>
    <td align="center">
      <a href="#">
        <img src="https://avatars.githubusercontent.com/u/103711264?v=4" width="100px;" alt="Jhonata Demuner Profile Picture"/><br>
        <sub>
          <b>Jhonata Demuner</b>
        </sub>
      </a>
    </td>
  </tr>
</table>

<h2 id="contribute">📫 Contribute</h2>

Want to contribute? Follow these steps:

1. Fork the repository
2. Clone your fork:
```bash
git clone https://github.com/your-username/leds-devops.git
cd leds-devops
```

3. Create a branch following the [naming convention](https://dev.to/couchcamote/git-branching-name-convention-cch):
```bash
git checkout -b feature/ISSUE-BEING-SOLVED
```

4. Follow ["Conventional Commits"](https://www.conventionalcommits.org/en/v1.0.0/) for commit messages
5. Open a Pull Request (PR) with a clear description of your changes

<h3>Documentations that might help</h3>

[📝 How to create a Pull Request](https://www.atlassian.com/br/git/tutorials/making-a-pull-request)
