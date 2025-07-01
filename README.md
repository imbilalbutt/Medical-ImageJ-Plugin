# Medical ImageJ Plugin 🩺🔬
An ImageJ plugin designed for advanced medical image processing, built as part of the MT2 (Medizintechnik II) course.

🎯 Project Overview
Inspired by the Medizintechnik II curriculum, this plugin facilitates hands-on experience with core image processing techniques—such as thresholding, segmentation, edge detection, and more—within the ImageJ ecosystem. This project aims to implement fundamental image-processing-techniques which have a lot of application in different fields based on imaging. This project builts a set of ImageJ-Plugins that are capable of performing operations like thresholding / image-segmentation, evaluation of segmentation using specifity and sensitivity, and edge-detection.


🧩 Features
Image thresholding & segmentation: Enables interactive and automated region of interest (ROI) extraction.

Edge detection: Includes popular methods like Canny and Otsu for edge and object boundary identification.

Modular design: Easily extendable to support future processing steps like denoising, registration, or feature extraction.

ImageJ integration: Seamlessly operates within the standard ImageJ/Fiji interface, following plugin best practices.

⚙️ Tech Stack
Java (compatible with ImageJ 1.x)

ImageJ Plugin API

Built from a template aligned with MT2 course structure.

📚 Course Alignment
This project is part of the Medizintechnik II (“Medical Engineering II – Medical Imaging Systems”) at FAU Erlangen–Nürnberg, a simulation of real-world medical image analysis workflows.

🔧 Getting Started
Prerequisites
> Use IntelliJ
> Download ImageJ
> Go to IntelliJ setting > Project structure > Click + button > Select Java > Select download jar file of ImageJ

Java 11+ development environment

(Optional) Maven for build automation

Installation
1. Clone the repository:
git clone https://github.com/imbilalbutt/Medical-ImageJ-Plugin.git
cd Medical-ImageJ-Plugin

2. Build the plugin:
mvn clean package

3. Copy the generated .jar into your ImageJ plugins/ directory.

4. Restart ImageJ or Fiji and access the plugin via the Plugins menu.




🗂 Project Structure

├── Medical-ImageJ-Plugin/
├──── ij.jar     # downloaded jar file of ImageJ
├──── src/       # Plugin source code
├──── plugin/MT2-project-ImageProcessing      
├──── img
├──── macros

🧪 Testing
TODO

Ensure full test coverage by running:
mvn test

📄 MT2 Course Context
This plugin is part of a series of practical assignments in Medizintechnik II exercises:

- Task 1: Thresholding
- Task 2: Evaluate Segmentation
- Task 3: Otsu segmentation
- Task 4: Filtering
- Task 5: Canny edge detection

Some tasks builds upon the previous, culminating in a comprehensive imaging toolkit.

📌 Next Steps
Add interactive visualization tools in ImageJ UI.

Integrate advanced methods like statistical segmentation or morphological filtering.

Automate workflows via macro recording.

Extend tests to include real-world medical image datasets.

📜 License
This project is licensed under the MIT License. See LICENSE for details.

🧭 Acknowledgments
Based on the Medizintechnik II – Medical Imaging Systems exercises at FAU Erlangen–Nürnberg
