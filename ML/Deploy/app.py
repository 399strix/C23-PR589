from flask import Flask, request, abort, jsonify
from functools import wraps
from model import *
from recommender import *
import io
import requests as req
import pandas as pd
import os

# V1 - DO NOT CHANGE THIS VARIABLE
LABEL_ALAM = {0: 'Air Terjun', 1: 'Campsite', 2: 'Danau', 3: 'Gunung', 4: 'Hutan', 5: 'Kebun', 6: 'Pantai', 7: 'Sungai'}
LABEL_BUATAN = {0: 'Bendungan', 1: 'Kebun Binatang', 2: 'Kolam Renang', 3: 'Museum', 4: 'Peternakan', 5: 'Religi', 6: 'Situs Purbakala', 7: 'Taman'}
# END OF V1

# V2 - CHANGE THIS INSTEAD
PATH_MODEL_BUATAN = "ML/Model/ResNet50V2_20_A_Batch16_RMS_10-3_Train76_Val80.h5"
PATH_MODEL_ALAM = "ML/Model/ResNet50V2_20_N_Batch16_RMS_10-3_Train78_Val72.h5"
PORT = 8000
DB_URL = "https://c23-pr589-ru5cfkck3a-uc.a.run.app/"
# USED FOR API KEY, ENSURE THIS SAME WITH IN ANDROID APP
SECRET_KEY = "secret"
# END OF V2

app = Flask(__name__)

# Decorator for checking API Key
def checkAPIKey(view):
    @wraps(view)
    def isAPIKeyValid(*args, **kwargs):
        if request.form.get('apiKey') and request.form.get('apiKey') == SECRET_KEY:
            return view(*args, **kwargs)
        else:
            abort(401)
    return isAPIKeyValid

# Route for Natural Tourism Prediction
@app.route('/alam', methods=['POST'])
def predictAlam():
    # Fetch image from request
    buffer = io.BytesIO()
    request.files['image'].save(buffer)
    image = convert(buffer)

    # Preprocess image
    image = preprocess(image)

    # Predict image
    predictions, pred_label = predict(MODEL_ALAM, image, LABEL_ALAM)

    # Get all available places based on prediction label
    if predictions:
        listCity, listPrice = getFilterBasedOnLabel(pred_label)
    else:
        return jsonify({'code': 'A-NF', 'message': 'No prediction found'})
    
    return jsonify({'status': 'A-OK', 'message': 'Success', 'predictions': pred_label, 'city': listCity, 'price': listPrice})

# Route for Artificial Tourism Prediction
@app.route('/buatan', methods=['POST'])
def predictBuatan():
    # Fetch image from request
    buffer = io.BytesIO()
    request.files['image'].save(buffer)
    image = convert(buffer)

    # Preprocess image
    image = preprocess(image)

    # Predict image
    predictions, pred_label = predict(MODEL_BUATAN, image, LABEL_BUATAN)
    pred_label = [key for sublist in pred_label for key, value in LABEL_ALL.items() if value in sublist]

    # Get recommended places
    if predictions:
        listCity, listPrice = getFilterBasedOnLabel(pred_label)
    else:
        return jsonify({'status': 'B-NF', 'message': 'No prediction found'})
    
    return jsonify({'status': 'B-OK', 'message': 'Success', 'predictions': predictions, 'city': listCity, 'price': listPrice})

# Route for Filtering
@app.route('/filter', methods=['POST'])
def filterOutput():
    priceFilter = request.json['price']
    locationFilter = request.json['location']
    labelFilter = request.json['prediction']

    upperLimit = 0
    lowerLimit = 0
    if priceFilter == 0:
        lowerLimit = 0
        upperLimit = 0
    elif priceFilter == 1:
        lowerLimit = 1
        upperLimit = 15000
    elif priceFilter == 2:
        lowerLimit = 15001
        upperLimit = 30000
    elif priceFilter == 3:
        lowerLimit = 30001
        upperLimit = 50000
    elif priceFilter == 4:
        lowerLimit = 50001
        upperLimit = 999999

    labelList = [key for sublist in labelFilter for key, value in LABEL_ALL.items() if value in sublist]

    allPlaces = TOURISM_ALL
    labelFiltered = allPlaces[allPlaces['label'].isin(labelList)]
    priceFiltered = labelFiltered[(labelFiltered['price'] >= lowerLimit) & (labelFiltered['price'] <= upperLimit)]
    locationFiltered = priceFiltered[priceFiltered['location']==locationFilter]
    if locationFiltered.empty:
        randomPlace = labelFiltered.sample(n=1)
        recommendations = contentBasedFiltering(randomPlace, locationFilter, DATA_TOURISM, SIMILARITY)
        if len(recommendations) < 5:
            minus = 5 - len(recommendations)
            if minus < len(labelFiltered):
                listRandomPlace = labelFiltered.sample(n=minus)
                for i in listRandomPlace.name:
                    recommendations.append(i)
    else:
        recommendations = []
        for i in locationFiltered.name:
            recommendations.append(i)
        if len(recommendations) < 5:
            minus = 5 - len(recommendations)
            if minus < len(labelFiltered):
                listRandomPlace = labelFiltered.sample(n=minus)
                for i in listRandomPlace.name:
                    recommendations.append(i)

    allPlaces = None
    return jsonify({'status': 'F-OK', 'message': 'Success', 'recommendations': recommendations})

def getFilterBasedOnLabel(label):
    locations = []
    prices = []

    for i in label:
        price = req.get(DB_URL + "products/" + str(i)).json()
        location = req.get(DB_URL + "products/" + str(i) + "/location").json()

        if isinstance(price, dict) and 'msg' in price:
            for price in price['msg']:
                if price not in prices:
                    prices.append(price)

            for location_dict in location:
                if 'location' in location_dict:
                    location = location_dict['location']
                    if location not in locations:
                        locations.append(location)

    return locations, sorted(prices)

def init():
    global MODEL_ALAM, MODEL_BUATAN, DATA_TOURISM, SIMILARITY, LABEL_ALL, TOURISM_ALL
    MODEL_ALAM, MODEL_BUATAN = load_model(PATH_MODEL_ALAM, PATH_MODEL_BUATAN)
    DATA_JSON = req.get(DB_URL + "products").json()
    TOURISM_ALL = pd.DataFrame(DATA_JSON)
    DATA_TOURISM = pd.DataFrame(DATA_JSON)
    DATA_TOURISM = DATA_TOURISM.rename(columns={'name': 'Wisata', 'label': 'Label (int)', 'location': 'Kab/Kota', 'description': 'Deskripsi'})
    LABEL_ALL = {
        1 :"Gunung", 2 :"Pantai", 3 :"Danau", 4 :"Museum", 5 :"Campsite", 6 :"Taman", 7 :"Kebun Binatang", 8 :"Kolam Renang",
        9 :"Air Terjun", 10 :"Sungai", 11 :"Kebun", 12 :"Hutan", 13 :"Peternakan", 14 :"Religi", 15 :"Bendungan", 16 :"Situs Purbakala",
    }
    DATA_TOURISM['Label (str)'] = DATA_TOURISM['Label (int)'].map(LABEL_ALL)
    DATA_TOURISM, SIMILARITY = initRecommender(DATA_TOURISM)

init()
if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=8000)
