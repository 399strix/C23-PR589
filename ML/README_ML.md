# **C23-PR589 - Machine Learning**

## **Table of Contents**
- [Introduction](#introduction)
- [Data](#data)
- [Model](#model)
- [API](#api)
- [How to Use](#how-to-use)

## **Introduction**
This repository is created to store the Machine Learning (ML) codes for Bangkit Capstone Project C23-PR589. The goal of this project is to help people to locate a tourism location based on photos. The ML model will be trained using photos of various tourism category (Mountain, Beach, etc.) and will be deployed as an API. The API will be used by the Android team to help users to find a tourism location based on photos uploaded by the user. The ML model will be trained using Tensorflow and Keras. The API will be deployed using Flask. This brach was originally pushed on [this](https://github.com/Schypozhoa/TourismClassifier) repository.

## **Data**
The data used in this project can be seen in **Data** folder, which contains:

1. Photos of various tourism category (Mountain, Beach, etc.) from Google Search (Scraped using ImageScrapper.py).
2. Dataset of tourist attractions that our team searched ourself (Currently only tourist attractions that are closest to where we live).

## **Model**
We are using Transfer Learning in this project. We are using ResNet50V2 as the base model and add some layers on top of it. We have 2 models, one for classifying the artificial tourism category (Museum, Zoo, etc) and one for classifying the natural tourism category (Mountain, Beach, etc). The model can be seen in **Model** folder.

## **API**
We have 3 endpoints in our API:
1. `/alam` - `POST`\
    The endpoint to predict the category of a natural type tourism photo. It will return a JSON containing the category of the photo with its confidence, a list of cities that have tourism location according to category and a list of price range of all tourism location according to category.

    **Request**: `Multipart/form-data`
    ```
    Body{
      key=image, 
      value= *your-image*.jpg, 
      content type = multipart/form-data
    }
    ```

    **Response**: `JSON`
    ```
    {
      "city": [
          "Kab. Kediri",
          "Kota Kediri",
          "Kota Surabaya",
          "Kab. Jember"
      ],
      "message": "Success",
      "predictions_confidence": {
        "Hutan": 0.8754536876678467,
        "Gunung": 1.0
      },
      "predictions_label": [
          1,
          12
      ],
      "price": [
          "0",
          "1-15.000",
          "30.001-50.000"
      ],
      "status": "B-OK"
    }
    ```

2. `/buatan` - `POST`\
    This endpoint is the same as previous endpoint, but this is for artificial type tourism photo. It will return the same as previous endpoint.

    **Request**: `Multipart/form-data`
    ```
    Body{
      key=image, 
      value= *your-image*.jpg, 
      content type = multipart/form-data
    }
    ```
    **Response**: `JSON`
    ```
    {
      "city": [
          "Kab. Kediri",
          "Kota Kediri",
          "Kota Surabaya",
          "Kab. Jember"
      ],
      "message": "Success",
      "predictions_confidence": {
        "Peternakan": 0.9744536876678467,
        "Taman": 1.0
      },
      "predictions_label": [
          6,
          13
      ],
      "price": [
          "0",
          "1-15.000",
          "30.001-50.000"
      ],
      "status": "B-OK"
    }
    ```

3. `/filter` - `POST`\
    The endpoint to filter tourism location based on category, city and price selected. It will return a JSON containing the list of tourism location that match the filter. If the prediction is null or no matched filter, it will randomly select one tourism location from the detected category and search for recommendation based on the random selected tourism location.
    
    **Request**: `JSON`
    ```
    {
      "prediction": [1, 13], 
      "price": 1,
      "location": "Kota Surabaya"
    }
    ```
    **Response**: `JSON`
    ```
    {
      "message": "Success",
      "recommendations": [
          "Kebun Bibit Wonorejo",
          "Kebun Binatang Surabaya",
          "Taman Bungkul",
          {
              "id": 15,
              "name": "Bukit Nirwana"
          },
          {
              "id": 7,
              "name": "Meru Betiri"
          }
      ],
      "status": "F-OK"
    }
    ```

Note:
If @checkAPIKey is used, all endpoints will require an additional API Key param as `apiKey` to be accessed. The API Key can be set in SECRET_KEY variable at `app.py` file.

## **How to Use**
These instructions will create a clone of this project and run it on the local machine for development and testing purposes. These instructions are made using Python version 3.10, for other versions it may be different.

### **Prerequisites**
Before starting, we need to create a new [virtual environment](https://docs.python.org/3/library/venv.html) (optional, if without virtual environment [next step](#skip)), and install all the required modules. The required modules can be seen in **requirements.txt** file.

* Create a new Virtual Environment

```
$ mkdir sata-venv
$ python -m venv /sata-venv
```

* Activate Virtual Environment

```
$ cd /sata-venv
$ Scripts/activate
```

* Install required module <a name = "skip"></a>

```
(sata-venv) $ pip install -r /path/to/requirements.txt
```

### **Local Installation**

1. Clone this repository, and extract it in book-venv folder. Or just use git clone in book-venv folder.

2. Run app.py script

```
(sata-venv) $ python app.py
```

3. The log below indicates that the installation was successful, the API can be accessed at the link listed in **Running on**.

```
 * Serving Flask app 'app'
 * Debug mode: on
 * Running on http://127.0.0.1:5000
Press CTRL+C to quit
 * Restarting with watchdog (windowsapi)
```