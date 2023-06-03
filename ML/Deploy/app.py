from flask import Flask, request, abort, jsonify, render_template
from functools import wraps
from model import *
import io

# DO NOT CHANGE THIS VARIABLE
LABEL_ALAM = {0: 'Air Terjun', 1: 'Campsite', 2: 'Danau', 3: 'Gunung', 4: 'Hutan', 5: 'Kebun', 6: 'Pantai', 7: 'Sungai'}
LABEL_BUATAN = {0: 'Bendungan', 1: 'Kebun Binatang', 2: 'Kolam Renang', 3: 'Museum', 4: 'Peternakan', 5: 'Religi', 6: 'Situs Purbakala', 7: 'Taman'}

# CHANGE THIS INSTEAD
PATH_MODEL_ALAM = "C:/Users/Administrator/Desktop/C23-PR589/ML/Model/ResNet50V2_20_A_Batch16_RMS_10-3_Train76_Val80.h5"
PATH_MODEL_BUATAN = "C:/Users/Administrator/Desktop/C23-PR589/ML/Model/ResNet50V2_20_N_Batch16_RMS_10-3_Train78_Val72.h5"
PORT = 5000
SECRET_KEY = "secret"

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
    buffer = io.BytesIO()
    request.files['image'].save(buffer)
    image = convert(buffer)
    image = preprocess(image)
    # image, pre_image = preprocess(image)
    predictions = predict(MODEL_ALAM, image, LABEL_ALAM)
    return jsonify(predictions)
    # return jsonify(predictions, pre_image)

# Route for Artificial Tourism Prediction
@app.route('/buatan')
@checkAPIKey
def predictBuatan():
    return "This is buatan"

def init():
    global MODEL_ALAM, MODEL_BUATAN
    MODEL_ALAM, MODEL_BUATAN = load_model(PATH_MODEL_ALAM, PATH_MODEL_BUATAN)

if __name__ == '__main__':
    init()
    app.run(debug=True, port=PORT)