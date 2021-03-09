![Android CI](https://github.com/lukaspanni/OpenSourceStats/workflows/Android%20CI/badge.svg)
![Build-LaTeX](https://github.com/lukaspanni/OpenSourceStats/workflows/Build-LaTeX/badge.svg)

# OpenSourceStats (_Your Open-Source Contribution-Stats on GitHub_ to be exact)

Android-App for simple GitHub Contribution statistics.

Dokumentation Programmentwurf für die Vorlesung Advanced Software Engineering an der DHBW Karlsruhe [hier](./dhbw_doc/Dokumentation.pdf)

## Build instructions

### Prerequisites

- Android-Studio
- GitHub-Account

### Build steps

- First create a new GitHub-OAuth-App to access the GitHub-API using your own account: follow the steps [here](https://docs.github.com/en/developers/apps/creating-an-oauth-app)
  - Copy Client-Secret and Client-ID and create a auth.xml file following [this](https://github.com/lukaspanni/OpenSourceStats/blob/main/app/src/main/res/values/.sample_auth.xml) template 

- Install all necessary dependencies using gradle and build the project in android studio

### Running Tests

- Unit-Tests can be found [here](https://github.com/lukaspanni/OpenSourceStats/tree/main/app/src/test/java/de/lukaspanni/opensourcestats) and run using the Android-Studio Test-Runner
