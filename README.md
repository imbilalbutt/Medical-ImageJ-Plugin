# Medical ImageJ Plugin 🩺🔬
An ImageJ plugin designed for advanced medical image processing, built as part of the MT2 (Medizintechnik II) course.

## 🎯 Project Overview
Inspired by the Medizintechnik II curriculum, this plugin facilitates hands-on experience with core image processing techniques—such as thresholding, segmentation, edge detection, and more—within the ImageJ ecosystem. This project aims to implement fundamental image-processing-techniques which have a lot of application in different fields based on imaging. This project builts a set of ImageJ-Plugins that are capable of performing operations like thresholding / image-segmentation, evaluation of segmentation using specifity and sensitivity, and edge-detection.


## 🧩 Features
Image thresholding & segmentation: Enables interactive and automated region of interest (ROI) extraction.

Segmentation Evaluation – Computes specificity and sensitivity for performance assessment.

Edge detection: Includes popular methods like Canny and Otsu for edge and object boundary identification.

Modular design: Easily extendable to support future processing steps like denoising, registration, or feature extraction.

ImageJ integration: Seamlessly operates within the standard ImageJ/Fiji interface, following plugin best practices.

## ⚙️ Tech Stack
Java (compatible with ImageJ 1.x)

ImageJ Plugin API

Built from a template aligned with MT2 course structure.

## 📚 Course Alignment
This project was developed as part of the Medical Imaging Systems module in the Medizintechnik II program at FAU Erlangen–Nürnberg, simulating common workflows in medical image analysis.


## 🔧 Getting Started

Prerequisites

- Download ImageJ

- Use an IDE such as IntelliJ IDEA

- Java SDK (11 or higher)

- (Optional) Maven



## Setup instructions

1. Clone the repository:
git clone https://github.com/imbilalbutt/Medical-ImageJ-Plugin.git
cd Medical-ImageJ-Plugin

2. Add ImageJ JAR to your project:

IntelliJ → Settings → Project Structure → Modules → Dependencies → Add ij.jar

3. Build the plugin:
mvn clean package

4. Copy the generated .jar into your ImageJ plugins/ directory.

5. Restart ImageJ or Fiji and access the plugin via the Plugins menu.




## 🗂 Project Structure

Medical-ImageJ-Plugin/
├── ij.jar                      # ImageJ library

├── src/                        # Plugin source code

├── plugin/MT2-project-ImageProcessing/

├── img/                        # Sample images

├── macros/                     # ImageJ macros

└── pom.xml                     # Maven configuration


## 🧪 Testing

> TODO: Implement unit tests for image processing algorithms.

CI/CD using Jenkins or Github actions

> TODO: Implement CI/CD pipeline to automatically run test cases.

Ensure full test coverage by running:
mvn test

## 📄 MT2 Course Context

This plugin is part of a series of practical final project in Medizintechnik II:

✅ Task 1: Image Thresholding

✅ Task 2: Segmentation Evaluation (Specificity, Sensitivity)

✅ Task 3: Otsu Thresholding

✅ Task 4: Image Filtering

✅ Task 5: Canny Edge Detection


Some tasks builds upon the previous, culminating in a comprehensive imaging toolkit.

## 📌 Future Enhancements

🔍 Add interactive GUI for visualization within ImageJ

📈 Integrate advanced segmentation techniques (e.g., watershed, active contours)

🧪 Improve testing using synthetic and real-world datasets

🛠 Automate workflows using ImageJ macros

## 📜 License
This project is licensed under the MIT License. See LICENSE for details.


## 🙏 Acknowledgments

Based on the Medizintechnik II – Medical Imaging Systems curriculum at
Friedrich-Alexander-Universität Erlangen–Nürnberg
