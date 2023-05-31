# C23-PR589 - Machine Learning

## Table of Contents
- [Introduction](#introduction)
- [Data](#data)
- [Model](#model)
- [API](#api)
- [How to Use](#how-to-use)

## Introduction
This repository is created to store the Machine Learning (ML) codes for Bangkit Capstone Project C23-PR589. The goal of this project is to help people to locate a tourism location based on photos. The ML model will be trained using photos of various tourism category (Mountain, Beach, etc.) and will be deployed as an API. The API will be used by the Android team to help users to find a tourism location based on photos uploaded by the user. The ML model will be trained using Tensorflow and Keras. The API will be deployed using Flask. This brach was originally pushed on [this](https://github.com/Schypozhoa/TourismClassifier) repository.

## Data
The data used in this project can be seen in **Data** folder, which contains:

1. Photos of various tourism category (Mountain, Beach, etc.) from Google Search (Scraped using ImageScrapper.py).
2. Dataset of tourist attractions that our team searched ourself (Currently only tourist attractions that are closest to where we live).

## Model
We are using Transfer Learning in this project. We are using ResNet50V2 as the base model and add some layers on top of it. We have 2 models, one for classifying the artificial tourism category (Museum, Zoo, etc) and one for classifying the natural tourism category (Mountain, Beach, etc). The model can be seen in **Model** folder.

## API
TBD

## How to Use
TBD