# Package Mapper

Package Mapper is an open-source tool that provides a visual representation of the classes and their dependencies within a GitHub repository. It supports any JavaScript and Java based projects and aims to expand to other languages in the future.

## Installation

To install Package Mapper, follow these steps:

1. Clone this repository to your local machine:
```shell
git clone https://github.com/MarzukhAsjad/package-mapper.git
```

2. Navigate to the `package-mapper` directory:
```shell
cd package-mapper
```

3. Create a `.env` file in the root directory with the following variable(s):
```Dotenv
REPOSITORY_DIRECTORY=<absolute path where all repositories will be downloaded>
ANALYSIS_DIRECTORY=<absolute path where all the analysis logs will be stored>
LOGS_DIRECTORY=<absolute path where all general logs will be stored>
```

Ensure that these folders with the paths exist.

4. Run the application using Maven:
```shell
mvnw spring-boot:run
```

**Note:** Package Mapper requires Java (JDK 21) to be installed on your machine.

## Usage

To use Package Mapper, you can send `POST` requests to the following endpoints:

1. `/repository/download`: Downloads the code from the specified GitHub repository.

The following JSON can be used as the request body (example):
```json
{
    "url": "https://github.com/DrKLO/Telegram.git"
}
```

2. `/repository/delete`: Deletes the downloaded code from the local storage.

No request body is required. If you have downloaded a repository and have not closed the application or deleted the repository yet, this endpoint will automatically delete that specific repository. Otherwise, it will delete all repositories by default.

3. `/analyse/custom`: Analyses the downloaded code and generates the dependency graph.

Pass in the absolute path of the local repository you just downloaded in the form of a JSON. The following JSON can be used as the request body (example):
```json
{
    "repositoryPath": "C:/Users/User/PackageMapper/repositories/Telegram"
}
```

4. `/analyse/all`: Analyses all repositories downloaded and stored in the repositories folder

No request body is required. 

## Overview

The principal idea behind Package Mapper is to aid in understanding the relationships and dependencies between classes and modules within a project. It does this by:

1. **Analyzing the Repository**: The tool analyzes the provided GitHub repository and identifies the classes, modules, and their import statements.
2. **Generating Dependencies**: By tracing the import statements, Package Mapper generates a dependency graph that shows how the classes and modules interact with each other.
3. **Visualizing the Dependencies** (coming soon): The generated dependency graph is visualized in a 2D space, allowing users to pan and explore the relationships between classes and modules.

## Features (coming soon)

- **Web-based Interface**: Package Mapper provides a user-friendly web interface where users can enter the GitHub repository path.
- **Dependency Visualization**: The tool displays the visualized graph in a 2D space, with the main class/module in the center and branches pointing out to other classes/modules.
- **Visual Representation**: Classes/modules are represented as circles, and dependencies/imports are depicted as arrows, making it easy to understand the relationships.
- **Panning and Zooming**: Users can pan and zoom the visualized graph to navigate and explore the dependencies.

## System Design

### Graphical User Interface (coming soon)

- Web-based interface for users to interact with the repository visualizer.
- Input field to enter the GitHub repository path.
- Visualized graph display in a 2D space with panning and zooming functionality.

### Backend Logic

- Extract code from the provided GitHub repository path.
- Parse the code to identify class and module definitions.
- Analyze import statements to determine dependencies between classes/modules.
- Build a graph data structure to represent relationships and dependencies.
- Store the graph data structure for visualization.

### Visualization (coming soon)

- Utilize a graph visualization library (e.g., D3.js) to render the graph.
- Map the graph data to visual elements: circles for classes/modules and arrows for dependencies/imports.
- Position the main class/module at the center of the graph.
- Apply layout algorithms for aesthetic arrangement of classes/modules and connections.
- Enable panning and zooming functionality for navigation.

### Integration with GitHub

- Utilize GitHub APIs to access and download public repositories and retrieve the code.
- Authenticate the user and obtain necessary permissions to access and download their private repositories.

### Deployment (coming soon)

- Deploy the system as an online service accessible through a web browser.
- Host the backend logic and visualization on a server or cloud platform.
- Ensure scalability and performance to handle multiple user requests simultaneously.

### Future Enhancements

- Downloading and analysing private repositories.
- Extend support for more languages other than Java and JavaScript.
- Enhance the visualization with a 3D representation.
- Integrate a feature to visualize git commit history, animating code evolution.

Package Mapper aims to provide a user-friendly and insightful way to visualize and understand the dependencies within a project, facilitating better code comprehension and maintenance.
