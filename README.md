# Medical ImageJ Plugin 🩺🔬  
**An ImageJ plugin for medical image processing developed for MT2 (Medizintechnik II) at FAU Erlangen-Nürnberg**

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
![Java](https://img.shields.io/badge/Java-11%2B-orange)
[![Java CI](https://github.com/imbilalbutt/Medical-ImageJ-Plugin/actions/workflows/maven.yml/badge.svg)](https://github.com/imbilalbutt/Medical-ImageJ-Plugin/actions/workflows/maven.yml)


## 🎯 Project Overview and Goal
Inspired by the Medizintechnik II curriculum, this plugin facilitates hands-on experience with core image processing techniques—such as thresholding, segmentation, edge detection, and more—within the ImageJ ecosystem. This project aims to implement fundamental image-processing-techniques which have a lot of application in different fields based on imaging. This project builts a set of ImageJ-Plugins that are capable of performing operations like thresholding / image-segmentation, evaluation of segmentation using specifity and sensitivity, and edge-detection.


## ✨ Key Features
### Implemented Course Project Tasks
✅ **Task 1**: Image Thresholding  
✅ **Task 2**: Segmentation Evaluation (Specificity & Sensitivity)  
✅ **Task 3**: Otsu Thresholding  
✅ **Task 4**: Primitive Edge-Detection Filters  (Sobel, Scharr, Prewitt)
✅ **Task 5**: Canny Edge Detection



### Core Functionalities
- **Segmentation**: Threshold-based ROI extraction and Otsu method
- **Performance Metrics**: Specificity & sensitivity calculation
- **Edge Detection**: Sobel, Scharr, Prewitt, and Canny
- **Modular Architecture**: Easy to extend with new features

## 🛠️ Installation
### Prerequisites
- ImageJ or Fiji
- Java SDK ≥11
- (Recommended) IntelliJ IDEA

### Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/imbilalbutt/Medical-ImageJ-Plugin.git
   cd Medical-ImageJ-Plugin
   ```

2. Add ImageJ dependency:
   - IntelliJ: `File → Project Structure → Modules → Dependencies → Add ij.jar`

3. Build the plugin:
   ```bash
   mvn clean package
   ```

4. Install:
   - Copy generated `.jar` to ImageJ's `plugins/` folder
   - Restart ImageJ/Fiji

## 🏗️ Project Structure
```
Medical-ImageJ-Plugin/
├── src/                        # Java source code
│   ├── Task_1_Threshold.java
│   ├── Task_2_EvaluateSegmentation
│   ├── Task_3_Otsu
│   ├── Task_4_Filters
│   ├── Task_5_CannyEdgeDetection
│   ├── EvaluationResult
│   └── test/java/              # Unit tests (TODO)
├── img/                        # Sample images
├── macros/                     # ImageJ macros
├── pom.xml                     # Maven configuration
└── ij.jar                      # ImageJ library
```

## 🧪 Testing & Validation
- **Unit Tests**: (To be implemented)
  ```bash
  mvn test
  ```
- **CI/CD**: GitHub Actions configured for automated testing

## 📚 Educational Context
Developed for the **Medical Imaging Systems** module in Medizintechnik II at FAU Erlangen-Nürnberg. The project follows:
- Standard medical image processing workflows
- ImageJ plugin development conventions
- MT2 course requirements for practical implementation

## 🔮 Future Work
- [ ] Interactive parameter tuning GUI
- [ ] Advanced segmentation (Watershed, Active Contours)
- [ ] Comprehensive test suite
- [ ] Documentation with usage examples

## 📜 License
MIT License - See [LICENSE](LICENSE) for details.

## 🙏 Acknowledgments
- Medizintechnik II teaching team at FAU Erlangen-Nürnberg
- ImageJ/Fiji developer community
```

The project aligns with MT2 course expectations while maintaining professional open-source project standards.
