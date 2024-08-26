# Package Mapper

Package Mapper is a web-based tool that provides a visual representation of the classes and their dependencies within a GitHub repository. It supports JavaScript projects and aims to expand to other languages in the future.

## Overview

The principal idea behind Package Mapper is to aid in understanding the relationships and dependencies between classes and modules within a project. It does this by:

1. **Analyzing the Repository**: The tool analyzes the provided GitHub repository and identifies the classes, modules, and their import statements.
2. **Generating Dependencies**: By tracing the import statements, Package Mapper generates a dependency graph that shows how the classes and modules interact with each other.
3. **Visualizing the Dependencies**: The generated dependency graph is visualized in a 2D space, allowing users to pan and explore the relationships between classes and modules.

## Features

- **Web-based Interface**: Package Mapper provides a user-friendly web interface where users can enter the GitHub repository path.
- **Dependency Visualization**: The tool displays the visualized graph in a 2D space, with the main class/module in the center and branches pointing out to other classes/modules.
- **Visual Representation**: Classes/modules are represented as circles, and dependencies/imports are depicted as arrows, making it easy to understand the relationships.
- **Panning and Zooming**: Users can pan and zoom the visualized graph to navigate and explore the dependencies.

## System Design

### User Interface

- Web-based interface for users to interact with the repository visualizer.
- Input field to enter the GitHub repository path.
- Visualized graph display in a 2D space with panning and zooming functionality.

### Backend Logic

- Extract code from the provided GitHub repository path.
- Parse the code to identify class and module definitions.
- Analyze import statements to determine dependencies between classes/modules.
- Build a graph data structure to represent relationships and dependencies.
- Store the graph data structure for visualization.

### Visualization

- Utilize a graph visualization library (e.g., D3.js) to render the graph.
- Map the graph data to visual elements: circles for classes/modules and arrows for dependencies/imports.
- Position the main class/module at the center of the graph.
- Apply layout algorithms for aesthetic arrangement of classes/modules and connections.
- Enable panning and zooming functionality for navigation.

### Integration with GitHub

- Utilize GitHub APIs to access the repository and retrieve the code.
- Authenticate the user and obtain necessary permissions to access the repository.
- Handle rate limits and ensure smooth integration with the GitHub platform.

### Deployment

- Deploy the system as an online service accessible through a web browser.
- Host the backend logic and visualization on a server or cloud platform.
- Ensure scalability and performance to handle multiple user requests simultaneously.

### Future Enhancements

- Develop an offline version for analyzing private codebases.
- Extend support for Java projects and other languages.
- Enhance the visualization with a 3D representation.
- Integrate a feature to visualize git commit history, animating code evolution.

Package Mapper aims to provide a user-friendly and insightful way to visualize and understand the dependencies within a project, facilitating better code comprehension and maintenance.