from flask import Flask, request, abort, jsonify
from functools import wraps
from model import *
from recommender import *
import io

# V1 - DO NOT CHANGE THIS VARIABLE
LABEL_ALAM = {0: 'Air Terjun', 1: 'Campsite', 2: 'Danau', 3: 'Gunung', 4: 'Hutan', 5: 'Kebun', 6: 'Pantai', 7: 'Sungai'}
LABEL_BUATAN = {0: 'Bendungan', 1: 'Kebun Binatang', 2: 'Kolam Renang', 3: 'Museum', 4: 'Peternakan', 5: 'Religi', 6: 'Situs Purbakala', 7: 'Taman'}
# END OF V1

# V2 - CHANGE THIS INSTEAD
PATH_MODEL_BUATAN = "ML/Model/ResNet50V2_20_A_Batch16_RMS_10-3_Train76_Val80.h5"
PATH_MODEL_ALAM = "ML/Model/ResNet50V2_20_N_Batch16_RMS_10-3_Train78_Val72.h5"
PORT = 8080
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
@checkAPIKey
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
    
    return jsonify({'status': 'A-OK', 'message': 'Success', 'predictions': predictions, 'city': listCity, 'price': listPrice})

# Route for Artificial Tourism Prediction
@app.route('/buatan', methods=['POST'])
@checkAPIKey
def predictBuatan():
    # Fetch image from request
    buffer = io.BytesIO()
    request.files['image'].save(buffer)
    image = convert(buffer)

    # Preprocess image
    image = preprocess(image)

    # Predict image
    predictions, pred_label = predict(MODEL_BUATAN, image, LABEL_BUATAN)

    # Get recommended places
    if predictions:
        listCity, listPrice = getFilterBasedOnLabel(pred_label)
    else:
        return jsonify({'status': 'B-NF', 'message': 'No prediction found'})
    
    return jsonify({'status': 'A-OK', 'message': 'Success', 'predictions': predictions, 'city': listCity, 'price': listPrice})

# Route for Filtering
@app.route('/filter', methods=['POST'])
@checkAPIKey
def filterOutput():
    # priceFilter should be in numerical format, ex: 0=gratis, 1=under 5k, etc.
    priceFilter = request.form.get('price')
    # locationFilter should be in string format, ex: "Kota Surabaya", "Kab. Malang", etc.
    locationFilter = request.form.get('location')
    # labelFilter should be in string format, ex: "Air Terjun", "Campsite", etc.
    labelFilter = request.form.get('prediction')

    # TODO : Get all available places based on priceFilter, locationFilter, and labelFilter
    # allPlaces = fetch all places by label from db
    # filteredPlaces = filter allPlaces by priceFilter and locationFilter
    # if len(filteredPlaces) == 0:
    # Do recomendation from one random name in allPlaces
    recommendations = contentBasedFiltering('Kawah Ijen', locationFilter, DATA_TOURISM, SIMILARITY)

    return jsonify({'status': 'F-OK', 'message': 'Success', 'recommendations': recommendations})

def getFilterBasedOnLabel(label):
    print(label)
    # TODO : Get all distict available places based on prediction label
    #        Get all price based on prediction label
    return ['Kota Surabaya', 'Kab. Malang', 'Kab. Sidoarjo', 'Kab. Probolinggo'], [10000, 20000, 30000, 40000]

import pandas as pd
def init():
    global MODEL_ALAM, MODEL_BUATAN, DATA_TOURISM, SIMILARITY
    MODEL_ALAM, MODEL_BUATAN = load_model(PATH_MODEL_ALAM, PATH_MODEL_BUATAN)
    # Data Tourism fetch from db and ensure is in Pandas DataFrame format, 
    # Column : 'No', 'Wisata', 'Label (int)', 'Label (str)', 'Alamat', 'Harga', 'Image URL', 'Kab/Kota', 'Provinsi', 'Deskripsi'
    # Used : 'Wisata', 'Label (str)', 'Kab/Kota', 'Provinsi', 'Deskripsi'
    DATA_TOURISM = pd.read_csv('ML/Data/dataset.csv')
    DATA_TOURISM, SIMILARITY = initRecommender(DATA_TOURISM)

if __name__ == '__main__':
    init()
    app.run( debug=True, host='0.0.0.0', port=int(os.environ.get('PORT', 8080)) )
