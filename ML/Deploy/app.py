from flask import Flask, request, abort, jsonify
from functools import wraps
from model import *
import io

# V1 - DO NOT CHANGE THIS VARIABLE
LABEL_ALAM = {0: 'Air Terjun', 1: 'Campsite', 2: 'Danau', 3: 'Gunung', 4: 'Hutan', 5: 'Kebun', 6: 'Pantai', 7: 'Sungai'}
LABEL_BUATAN = {0: 'Bendungan', 1: 'Kebun Binatang', 2: 'Kolam Renang', 3: 'Museum', 4: 'Peternakan', 5: 'Religi', 6: 'Situs Purbakala', 7: 'Taman'}
# END OF V1

# V2 - CHANGE THIS INSTEAD
PATH_MODEL_BUATAN = "C:/Users/Administrator/Desktop/C23-PR589/ML/Model/ResNet50V2_20_A_Batch16_RMS_10-3_Train76_Val80.h5"
PATH_MODEL_ALAM = "C:/Users/Administrator/Desktop/C23-PR589/ML/Model/ResNet50V2_20_N_Batch16_RMS_10-3_Train78_Val72.h5"
PORT = 5000
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
    # priceFilter should be in numerical format, ex: 0=gratis, 1=under 5k, etc.
    priceFilter = request.form.get('price')
    # locationFilter should be in string format, ex: "Kota Surabaya", "Kab. Malang", etc.
    locationFilter = request.form.get('location')

    # Fetch image from request
    buffer = io.BytesIO()
    request.files['image'].save(buffer)
    image = convert(buffer)

    # Preprocess image
    image = preprocess(image)

    # Predict image
    predictions, pred_label = predict(MODEL_ALAM, image, LABEL_ALAM)

    # Get recommended places
    if predictions:
        listPlace = recommendPlaces(pred_label, priceFilter, locationFilter)
    else:
        return jsonify({'status': 'OK', 'message': 'No prediction found'})
    
    return jsonify({'status': 'OK', 'message': 'Success', 'predictions': predictions, 'recommendedPlaces': listPlace})

# Route for Artificial Tourism Prediction
@app.route('/buatan', methods=['POST'])
@checkAPIKey
def predictBuatan():
    # priceFilter should be in numerical format, ex: 0=gratis, 1=under 5k, etc.
    priceFilter = request.form.get('price')
    # locationFilter should be in string format, ex: "Kota Surabaya", "Kab. Malang", etc.
    locationFilter = request.form.get('location')

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
        listPlace = recommendPlaces(pred_label, priceFilter, locationFilter)
    else:
        return jsonify({'status': 'OK', 'message': 'No prediction found'})
    
    return jsonify({'status': 'OK', 'message': 'Success', 'predictions': predictions, 'recommendedPlaces': listPlace})

def recommendPlaces(label, priceFilter, locationFilter):
    print(label, priceFilter, locationFilter)
    # TODO : Get recommended places based on prediction label, priceFilter and locationFilter.
    #        For now, just use SQL query to get all places from prediction label, and filtered 
    #        by priceFilter and locationFilter. Then, return the recommended places in JSON format
    return ["Place 1", "Place 2", "Place 3"]

def init():
    global MODEL_ALAM, MODEL_BUATAN
    MODEL_ALAM, MODEL_BUATAN = load_model(PATH_MODEL_ALAM, PATH_MODEL_BUATAN)

if __name__ == '__main__':
    init()
    app.run(debug=True, port=PORT)