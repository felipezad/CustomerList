# CustomerList

The following is a demonstration code that utilizes some basic principles in an Android Project. The project structure follows a monolithic approach, with a core package that ideally should drive towards a modular project. However, for simplicity's sake, the project contains packages that emulate the modules.

The project consists of the following modules:
- Core: This module is responsible for shared configuration and models that can be reused by one or more modules.
- Customers: This module represents the main list of the application.
- Detail: This module represents details that are required by a unique customer.

The project's tech stack is provided in the Gradle KTS and includes the following:
- Android MVVM
- StateFlow
- Hilt
- Moshi
- Glide
- Material Design
- Coroutines
- Navigation Component

This project structure and tech stack are designed to provide a scalable, efficient, and user-friendly Android application.

# Won't Address

Please note that currently, a billing account is required to create an API_KEY for the Google Maps API, which falls outside the scope of this task. To accommodate the design, a placeholder JPG has been included in the project.

